package projetescalade;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Commentaire;
import javabeans.PhotoSecteur;
import javabeans.Secteur;
import javabeans.Site;
import javabeans.Utilisateur;
import javabeans.Voie;


public class ServletSecteur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //
	ConnexionDao maconnexion;
	//
	private SecteurDao accesBddSecteur;
	private PhotoSecteurDao accesBddPhotoSecteur;
	private SiteDao accesBddSite;
	private CommentaireDao accesBddCommentaire;
	private UtilisateurDao accesBddUtilisateur;
	private VoieDao accesBddVoie;
	//
	private int id;
	private Secteur secteurAAfficher;
	private CalculDate aujourdhui;
	//
	public void init() throws ServletException {
		maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
		this.accesBddSecteur=maconnexion.getSecteurDao();
		this.accesBddPhotoSecteur=maconnexion.getPhotoSecteurDao();
		this.accesBddSite=maconnexion.getSiteDao();
		this.accesBddCommentaire=maconnexion.getCommentaireDao();
		this.accesBddUtilisateur=maconnexion.getUtilisateurDao();
		this.accesBddVoie=maconnexion.getVoieDao();
	}
    public ServletSecteur() {
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
			id =Integer.valueOf(request.getParameter("secteur"));
		}catch(Exception e) {
			
		}
		
		if(id!=0) {
				//R�cup�ration des informations Site
				secteurAAfficher=accesBddSecteur.trouverSecteur(id);
				if(secteurAAfficher.getIdSecteur()!=0) {
					//
					//cookie important pour tracer la derni�re page atteinte par l'utilisateur
					String adresse =request.getRequestURI()+"?secteur="+id;
					response.addCookie(new Cookie("last",adresse));
					//
					//R�cup�ration adress img
					PhotoSecteur relationPhotoSecteur= accesBddPhotoSecteur.getPhotoPath(id);
					//String hostURL="http://"+request.getServerName() + ":" + request.getServerPort()+"/escalade/img/";
					String imgPath=null;
					if(relationPhotoSecteur.getPathPhoto()!=null) {
						imgPath="/escalade/img/"+relationPhotoSecteur.getPathPhoto();
					}
					//R�cup�ration du site li� au secteur
					Site siteLi� =accesBddSite.trouverSite(secteurAAfficher.getIdSite());
					//
					secteurAAfficher.setPaysSite(siteLi�.getPaysSite());
					secteurAAfficher.setVilleSite(siteLi�.getVilleSite());
					secteurAAfficher.setLatitudeSite(siteLi�.getLatitudeSite());
					secteurAAfficher.setLongitudeSite(siteLi�.getLongitudeSite());
					secteurAAfficher.setNomSite(siteLi�.getNomSite());
					//
					//R�cup�ration des commentaires du Site et des utilisateurs ayant comment�
					ArrayList<Commentaire> commentaireDuSecteur = accesBddCommentaire.trouverCommentairesParSecteur(id);
					for(Commentaire comm:commentaireDuSecteur) {
						if(comm.getContenuCommentaire()!=null && comm.getContenuCommentaire().equals("")==false) {
							try {
								if(comm.getContenuCommentaire().substring(0, secteurAAfficher.getNomSecteur().length()+11).equals("[Secteur: "+secteurAAfficher.getNomSecteur()+"]")) {
									comm.setContenuCommentaire(comm.getContenuCommentaire().substring(secteurAAfficher.getNomSecteur().length()+12));
								}
							}finally {
								Utilisateur commentateur = accesBddUtilisateur.trouver(comm.getIdUtilisateur());
								comm.setCommentateur(commentateur);
								if(comm.getIdUtilisateur()==0) {
									Utilisateur commentateur2= new Utilisateur(0,"","","","","","","","");
									commentateur2.setPrenom("Anonyme");
									comm.setCommentateur(commentateur2);
								}
							}
						}
					}
					//
					//R�cup�ration des voies
					ArrayList<Voie> voies=accesBddVoie.trouver(secteurAAfficher.getIdSecteur());
					String[] minMax=Quotations.getMinMax(voies);
					//
					//Affectation des variables
					request.setAttribute("minMax", minMax);
					commentaireDuSecteur.sort(null);
					request.setAttribute("imgPath", imgPath);
					request.setAttribute("maintenant", aujourdhui);
					request.setAttribute("voies", voies);
					request.setAttribute("commentaireDuSecteur", commentaireDuSecteur);
					request.setAttribute("secteurfound", secteurAAfficher);
					this.getServletContext().getRequestDispatcher("/WEB-INF/secteur.jsp").forward(request, response);
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
			contenuDuCommentaire="[Secteur: "+ secteurAAfficher.getNomSecteur() +"] "+contenuDuCommentaire;
			//
			aujourdhui=new CalculDate();
			//def info de base du commentaire
			Commentaire newCommentaire = new Commentaire();
			newCommentaire.setContenuCommentaire(contenuDuCommentaire);
			newCommentaire.setDateCommentaire(aujourdhui.getTheDate().toString());
			newCommentaire.setIdSecteur(id);
			newCommentaire.setIdSite(secteurAAfficher.getIdSite());
			//def I.P. du commentaire
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
			accesBddCommentaire.ajouterCommentaire(newCommentaire,newCommentaire.getIdSecteur(),0);
			//
			
			//
		}
		//
		response.sendRedirect(request.getContextPath() + fromWho);
		//
	}

}
