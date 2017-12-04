package projetescalade;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Commentaire;
import javabeans.PhotoSite;
import javabeans.PhotoTopo;
import javabeans.Reservation;
import javabeans.Secteur;
import javabeans.Site;
import javabeans.Topo;
import javabeans.Utilisateur;
import javabeans.Voie;


public class ServletSite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao accesBddUtilisateur;
	private TopoDao accesBddTopo;
	private SiteDao accesBddSite;
	private TopoSitesDao accesBddTopoSite;
	private CommentaireDao accesBddCommentaire;
	private SecteurDao accesBddSecteur;
	private VoieDao accesBddVoie;
	private PhotoSiteDao accesBddPhotoSite;
	//
	private ConnexionDao maconnexion;
	//
	private int id;
	private CalculDate aujourdhui;
	//
	
	public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
        this.accesBddUtilisateur = maconnexion.getUtilisateurDao();
        this.accesBddTopo=maconnexion.getTopoDao();
        this.accesBddSite=maconnexion.getSiteDao();
        this.accesBddTopoSite=maconnexion.getTopoSitesDao();
        this.accesBddCommentaire=maconnexion.getCommentaireDao();
        this.accesBddSecteur=maconnexion.getSecteurDao();
        this.accesBddVoie=maconnexion.getVoieDao();
        this.accesBddPhotoSite=maconnexion.getPhotoSiteDao();
    }

    public ServletSite() {
        super();
        //
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//
		id = 0;
		try {
			id =Integer.valueOf(request.getParameter("site"));
		}catch(Exception e) {
			
		}
		
		if(id!=0) {
			//Récupération des informations Site
			Site siteAAfficher=accesBddSite.trouverSite(id);
			if(siteAAfficher.getIdSite()!=0) {
				//
				//cookie important pour tracer la dernière page atteinte par l'utilisateur
				String adresse =request.getRequestURI()+"?site="+id;
				response.addCookie(new Cookie("last",adresse));
				//
				//Récupération adress img
				PhotoSite relationPhotoSite= accesBddPhotoSite.getPhotoPath(id);
				//String hostURL="http://"+request.getServerName() + ":" + request.getServerPort()+"/escalade/img/";
				String imgPath=null;
				if(relationPhotoSite.getPathPhoto()!=null) {
					imgPath="/escalade/img/"+relationPhotoSite.getPathPhoto();
				}
				//
				//Récupération des topo concernés par les sites et leur propriétaire
				ArrayList<Integer> idDesTopos=accesBddTopoSite.trouverTopoConcerneParSite(id);
				ArrayList<Topo> topoConcernes=accesBddTopo.trouverTopo(idDesTopos);
				ArrayList<Utilisateur> listProprietaires=accesBddUtilisateur.trouverProprietaireTopo(topoConcernes);
				//On assigne les propriétaires aux topos
				for(Topo top:topoConcernes) {
					for(Utilisateur prop:listProprietaires) {
						if(top.getIdProprietaire()==prop.getId()) {
							top.setProprietaire(prop);
						}
					}
				}
				//Récupération des commentaires du Site et des utilisateurs ayant commenté
				ArrayList<Commentaire> commentaireDuSite = accesBddCommentaire.trouverCommentairesParSite(id);
				for(Commentaire comm:commentaireDuSite) {
					if(comm.getContenuCommentaire()!=null && comm.getContenuCommentaire().equals("")==false) {
							Utilisateur commentateur = accesBddUtilisateur.trouver(comm.getIdUtilisateur());
							comm.setCommentateur(commentateur);
						if(comm.getIdUtilisateur()==0) {
							Utilisateur commentateur2= new Utilisateur(0,"","","","","","","","");
							commentateur2.setPrenom("Anonyme");
							comm.setCommentateur(commentateur2);
						}
						
					}
				}
				//
				//Récupération des Secteurs et des voies
				ArrayList<Voie> voies = null;
				ArrayList<Secteur> secteurs=accesBddSecteur.trouver(id);
				for(Secteur sect:secteurs) {
					if(voies==null) {
						voies=accesBddVoie.trouver(sect.getIdSecteur());
					}else {
						voies.addAll(accesBddVoie.trouver(sect.getIdSecteur()));
					}
				}
				String[] minMax=Quotations.getMinMax(voies);
				int nbVoies=0;
				if(voies!=null) {
					nbVoies=voies.size();
				}else {
					nbVoies=0;
				}
				
				//
				request.setAttribute("nbVoies", nbVoies);
				request.setAttribute("minMax", minMax);
				commentaireDuSite.sort(null);
				request.setAttribute("imgPath", imgPath);
				request.setAttribute("maintenant", aujourdhui);
				request.setAttribute("secteurs", secteurs);
				request.setAttribute("voies", voies);
				request.setAttribute("toposconcernes", topoConcernes);
				request.setAttribute("commentaireDuSite", commentaireDuSite);;
				request.setAttribute("sitefound", siteAAfficher);
				this.getServletContext().getRequestDispatcher("/WEB-INF/site.jsp").forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + fromWho);
			}
		}else {
			response.sendRedirect(request.getContextPath() + fromWho);
		}
		//
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession sessionEnCours=request.getSession();
		Utilisateur utilisateurConnecte=(Utilisateur)sessionEnCours.getAttribute("utilisateurencours");
		//
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//
		String contenuDuCommentaire=request.getParameter("commentaire");
		if(contenuDuCommentaire!=null) {
			//
			aujourdhui=new CalculDate();
			//
			Commentaire newCommentaire = new Commentaire();
			newCommentaire.setContenuCommentaire(contenuDuCommentaire);
			newCommentaire.setDateCommentaire(aujourdhui.getTheDate().toString());
			newCommentaire.setIdSite(id);
			//i.p.
			String ip = request.getRemoteAddr();
			if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			    InetAddress inetAddress = InetAddress.getLocalHost();
			    String ipAddress = inetAddress.getHostAddress();
			    ip = ipAddress;
			}
			newCommentaire.setIpCommentaire(ip);
			if(utilisateurConnecte!=null) {
				newCommentaire.setIdUtilisateur(utilisateurConnecte.getId());
			}else {
				newCommentaire.setIdUtilisateur(0);//anonyme
			}
			accesBddCommentaire.ajouterCommentaire(newCommentaire,0,0);
			//
		}
		//
		response.sendRedirect(request.getContextPath() + fromWho);
		//
	}

}
