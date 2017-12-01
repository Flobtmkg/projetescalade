package projetescalade;


import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.LienTopoSite;
import javabeans.Site;
import javabeans.Topo;
import javabeans.Utilisateur;

public class ServletAjoutTopo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//
	private SiteDao accesBddSites;
	private TopoDao accesBddTopo;
	private TopoSitesDao accesBddTopoSites;
	private PhotoTopoDao accesBddPhotoTopo;
	//
	private ConnexionDao maconnexion;
	//
	
	public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance();
        this.accesBddSites = maconnexion.getSiteDao();
        this.accesBddTopo=maconnexion.getTopoDao();
        this.accesBddTopoSites=maconnexion.getTopoSitesDao();
        this.accesBddPhotoTopo=maconnexion.getPhotoTopoDao();
    }
       
    public ServletAjoutTopo() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession sessionEnCours=request.getSession();
		//
		//rechercher les sites pour les proposer à l'ajout topo
		ArrayList<Site> allSites =new ArrayList<Site>();
		ArrayList<Integer> tableau0=new ArrayList<Integer>();
		//besoin d'un ArrayList contenant 0 pour obtenir toute la table
		tableau0.add(0);
		allSites=accesBddSites.trouverListeSite(tableau0);
		//
		sessionEnCours.setAttribute("allSites", allSites);
		response.sendRedirect(request.getContextPath() + "/espaceutilisateur#ModalAjoutTopo");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession sessionEnCours=request.getSession();
		//
		Utilisateur utilisateurencours=(Utilisateur)sessionEnCours.getAttribute("utilisateurencours");
		ArrayList<Site> allSites =(ArrayList<Site>)sessionEnCours.getAttribute("allSites");
		//
		request.setCharacterEncoding("UTF-8");
		//
		//On def le new topo et on l'ajoute
		Topo newTopo =new Topo();
		newTopo.setNomTopo(request.getParameter("titre"));
		newTopo.setDescriptionTopo(request.getParameter("descriptionTopo"));
		newTopo.setIdProprietaire(utilisateurencours.getId());
		String erreur=accesBddTopo.ajoutTopo(newTopo);
		if(erreur.equals("")==false) {
			response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
		}
		//on récupère le topo précédent dans une requète pour obtenir idTopo correspondant
		Topo topoRecupere=accesBddTopo.trouverTopoSansIdTopo(newTopo.getIdProprietaire(),newTopo.getNomTopo(),newTopo.getDescriptionTopo());
		//On def les liens topoSites
		ArrayList<LienTopoSite> sitesAssocies =new ArrayList<LienTopoSite>();
		//On récupère le dernier indice idSite dans allSites
		int idmax=0;
		for(Site chaqueSite:allSites) {
			if(chaqueSite.getIdSite()>idmax) {
				idmax=chaqueSite.getIdSite();
			}
		}
		//
		int i=1;
		while(i<=idmax) {
			String valeurCheck=request.getParameter("spot"+String.valueOf(i));	
			if(valeurCheck!=null) {
				LienTopoSite relationTopoSite =new LienTopoSite();
				relationTopoSite.setIdTopo(topoRecupere.getIdTopo());
				relationTopoSite.setIdSite(i);
				sitesAssocies.add(relationTopoSite);
			}
			i++;
		}
		//On écrit les nouvelles relations topoSites
		String erreur2=accesBddTopoSites.ajoutTopoSites(sitesAssocies);
		//
		if(erreur2.equals("")==false) {
			response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
		}
		//
		//fichier
		//
		String nomFichier=Photo.ajout(request.getPart("fichier"),getServletContext().getInitParameter("filePath"),topoRecupere.getIdTopo());
		if(nomFichier.equals("Erreur")==false) {
			accesBddPhotoTopo.ajoutPhoto(topoRecupere.getIdTopo(), nomFichier); 
		}
		//
	    //mettre à j variable session
	    ArrayList<Topo> listeDesTopos=(ArrayList<Topo>)sessionEnCours.getAttribute("topoDelUtilisateur");
	    listeDesTopos.add(topoRecupere);
	    sessionEnCours.setAttribute("topoDelUtilisateur", listeDesTopos);
	    //
		response.sendRedirect(request.getContextPath() + "/espaceutilisateur#ModalValidationAjoutTopo");
        //
	}
}
