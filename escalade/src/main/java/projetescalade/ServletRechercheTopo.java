package projetescalade;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabeans.Site;
import javabeans.Topo;


public class ServletRechercheTopo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Topo> toposAAfficher;
	private String rech;
	//
	private String dispo;
	private String rechsite;
	private String rechsearch;
	private String nbPages;
	// 
	private ConnexionDao maconnexion;
	private SiteDao accesBddSite;
	private TopoDao accesBddTopo;
    
    public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance();
        this.accesBddSite=maconnexion.getSiteDao();
        this.accesBddTopo=maconnexion.getTopoDao();
    }
        

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		rech = "";
		try {
			rech =request.getParameter("rech");
		}catch(Exception e) {
			
		}
		//récupération de la liste des sites
		ArrayList<Integer> listeVide = new ArrayList<Integer>();
		listeVide.add(0);//le passage d'une liste contenant 0 à [0] permet de renvoyer la liste de tout les sites
		ArrayList<Site> listeDesSites = accesBddSite.trouverListeSite(listeVide);
		//
		//cookie important pour tracer la dernière page atteinte par l'utilisateur
		String adresse;
		if(rech==null) {
			toposAAfficher=null;
			adresse =request.getRequestURI();
		}else {
			adresse =request.getRequestURI()+"?rech="+rech;
		}
		response.addCookie(new Cookie("last",adresse));
		//
		//
		//recherche et affectation des cookies requete
		request.setAttribute("rechsearch",rechsearch);
		request.setAttribute("rechsite",rechsite);
		request.setAttribute("dispo",dispo);
		request.setAttribute("nbPages",nbPages);
		request.setAttribute("rech", rech);
		//
		request.setAttribute("listeDesSites", listeDesSites);
		request.setAttribute("toposAAfficher", toposAAfficher);
		//
		this.getServletContext().getRequestDispatcher("/WEB-INF/topos.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		request.setCharacterEncoding("UTF-8");
		//
		String keyWord=request.getParameter("search");
		String site=request.getParameter("site");
		String dispoOuPas=request.getParameter("dispoTopo");
		//gestion des problèmes de saisie
		if(dispoOuPas.equals("Disponible ou indisponible")) {
			dispoOuPas="";
		}
		if(site.equals("Tout les sites")==true) {
			site="";
		}
		//
		//rech des keyword dans Topo avec filtre du site
		ArrayList<Topo> topoRecherches =accesBddTopo.trouverParCritere(site, keyWord, dispoOuPas);
		//On regroupe les résultat par idtopo en faisant la liste des objets site auquels ils sont associés (pas faisable par GROUP BY dans la requete)
		toposAAfficher=regrouperLesTopos(topoRecherches);
		//
		//def des variables de recherche
		rechsearch=keyWord;
		rechsite=site;
		dispo=dispoOuPas;
		//
		int nbpages=(int)(toposAAfficher.size()/10)+1;
		nbPages=String.valueOf(nbpages);
		//
		response.sendRedirect(request.getContextPath() + "/topos?rech=1");
		//
	}
	//
	//
	//
	private ArrayList<Topo> regrouperLesTopos(ArrayList<Topo> topoRecherchesInput){
		ArrayList<Topo> topoARenvoyer = new ArrayList<Topo>();
		ArrayList <Integer> idDejaTraites= new ArrayList <Integer>();
		for(Topo top:topoRecherchesInput) {
			int refId=top.getIdTopo();
			if(idDejaTraites.contains(refId)==false) {
				idDejaTraites.add(refId);
				ArrayList<Site> lesSitesDuTopo=new ArrayList<Site>();
				for(Topo top2:topoRecherchesInput) {
					if(top2.getIdTopo()==refId) {
						//tout les topos dupliqués dans topoRecherchés à cause de la multiplicité des sites, sont regroupés en aggrégant les sites en ArrayList de site
						lesSitesDuTopo.add(top2.getSiteAssocie());
					}
				}
				top.setSitesAssocies(lesSitesDuTopo);
				topoARenvoyer.add(top);
			}
		}
		return topoARenvoyer;
	}
}
