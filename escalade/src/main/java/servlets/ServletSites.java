package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classestravail.CookieGetter;
import classestravail.Quotations;
import factorydao.ConnexionDao;
import interfacesdao.SiteDao;
import interfacesdao.VoieDao;
import javabeans.Site;
import javabeans.Voie;

public class ServletSites extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Voie> voiesAAfficher;
	private String rech;
	private ConnexionDao maconnexion;
	private SiteDao accesBddSite;
	private VoieDao accesBddVoie;
    
    public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
        this.accesBddSite=maconnexion.getSiteDao();
        this.accesBddVoie=maconnexion.getVoieDao();
    }
        
    public ServletSites() {
        super();
        //
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		rech = "";
		try {
			rech =request.getParameter("rech");
		}catch(Exception e) {
			
		}
		//récupération de la liste des villes
		ArrayList<Site> paysVilles = accesBddSite.trouverVille();
		//
		//cookie important pour tracer la dernière page atteinte par l'utilisateur
		String adresse;
		if(rech==null) {
			voiesAAfficher=null;
			adresse =request.getRequestURI();
		}else {
			adresse =request.getRequestURI()+"?rech="+rech;
		}
		response.addCookie(new Cookie("last",adresse));
		//
		//
		//recherche et affectation des cookies requete
		request.setAttribute("rechsearch",CookieGetter.getValeurCookie(request, "rechsearch"));
		request.setAttribute("rechville",CookieGetter.getValeurCookie(request, "rechville"));
		request.setAttribute("rechcotMin",CookieGetter.getValeurCookie(request, "rechcotMin"));
		request.setAttribute("rechcotMax",CookieGetter.getValeurCookie(request, "rechcotMax"));
		request.setAttribute("nbPages",CookieGetter.getValeurCookie(request, "nbPages"));
		request.setAttribute("rech", rech);
		//
		request.setAttribute("paysVilles", paysVilles);
		request.setAttribute("voiesAAfficher", voiesAAfficher);
		//
		this.getServletContext().getRequestDispatcher("/WEB-INF/sites.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		String keyWord=request.getParameter("search");
		String ville=request.getParameter("ville");
		String cotMin=request.getParameter("cotMin");
		String cotMax=request.getParameter("cotMax");
		//gestion des problèmes de saisie
		if(ville.equals("Toutes villes")==true) {
			ville="";
		}else {
			ville=ville.substring(0, ville.lastIndexOf(","));
		}
		if(cotMin.equals("Toutes cotations")) {
			cotMin="1a";
		}
		if(cotMax.equals("Toutes cotations")) {
			cotMax="9c+";
		}
		//
		//rech des keyword dans Site, dans Secteur, dans Voie avec filtre de la ville
		ArrayList<Voie> voiesRecherchées =accesBddVoie.trouverParCritere(ville, keyWord);
		//
		//tri des résultats en fonction des cotations
		//
		cotMin=cotMin.toLowerCase(new Locale("FRENCH"));
		cotMax=cotMax.toLowerCase(new Locale("FRENCH"));
		voiesAAfficher=Quotations.getVoiesCorrespondantesAuxCotations(voiesRecherchées, cotMin, cotMax);
		//
		//
		//inscription de la requete dans les cookies
		response.addCookie(new Cookie("rechsearch",keyWord));
		response.addCookie(new Cookie("rechville",ville));
		response.addCookie(new Cookie("rechcotMin",cotMin));
		response.addCookie(new Cookie("rechcotMax",cotMax));
		int nbpages=(int)(voiesAAfficher.size()/10)+1;
		response.addCookie(new Cookie("nbPages",String.valueOf(nbpages)));
		//
		response.sendRedirect(request.getContextPath() + "/sites?rech=1");
		//
	}

}
