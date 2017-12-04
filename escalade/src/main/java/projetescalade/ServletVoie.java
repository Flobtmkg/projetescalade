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
import javabeans.PhotoVoie;
import javabeans.Secteur;
import javabeans.Site;
import javabeans.Utilisateur;
import javabeans.Voie;

public class ServletVoie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 //
		ConnexionDao maconnexion;
		//
		private SecteurDao accesBddSecteur;
		private SiteDao accesBddSite;
		private CommentaireDao accesBddCommentaire;
		private UtilisateurDao accesBddUtilisateur;
		private VoieDao accesBddVoie;
		private PhotoVoieDao accesBddVoieSecteur;
		//
		private int id;
		private Voie voieAAfficher;
		private CalculDate aujourdhui;
		//
		public void init() throws ServletException {
			maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
			this.accesBddSecteur=maconnexion.getSecteurDao();
			this.accesBddSite=maconnexion.getSiteDao();
			this.accesBddCommentaire=maconnexion.getCommentaireDao();
			this.accesBddUtilisateur=maconnexion.getUtilisateurDao();
			this.accesBddVoie=maconnexion.getVoieDao();
			this.accesBddVoieSecteur=maconnexion.getPhotoVoieDao();
		}
	    public ServletVoie() {
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
				id =Integer.valueOf(request.getParameter("voie"));
			}catch(Exception e) {
				
			}
			
			if(id!=0) {
					//Récupération des informations Site
					voieAAfficher=accesBddVoie.trouverParVoieid(id);
					if(voieAAfficher.getIdVoie()!=0) {
						//
						//cookie important pour tracer la dernière page atteinte par l'utilisateur
						String adresse =request.getRequestURI()+"?voie="+id;
						response.addCookie(new Cookie("last",adresse));
						//
						//Récupération adress img
						PhotoVoie relationPhotoVoie= accesBddVoieSecteur.getPhotoPath(id);
						//String hostURL="http://"+request.getServerName() + ":" + request.getServerPort()+"/escalade/img/";
						String imgPath=null;
						if(relationPhotoVoie.getPathPhoto()!=null) {
							imgPath="/escalade/img/"+relationPhotoVoie.getPathPhoto();
						}
						//Récupération du secteur lié à la voie
						Secteur secteurLié =accesBddSecteur.trouverSecteur(voieAAfficher.getIdSecteur());
						Site siteLié =accesBddSite.trouverSite(secteurLié.getIdSite());
						//
						voieAAfficher.setPaysSite(siteLié.getPaysSite());
						voieAAfficher.setVilleSite(siteLié.getVilleSite());
						voieAAfficher.setLatitudeSite(siteLié.getLatitudeSite());
						voieAAfficher.setLongitudeSite(siteLié.getLongitudeSite());
						voieAAfficher.setNomSite(siteLié.getNomSite());
						voieAAfficher.setIdSecteur(secteurLié.getIdSecteur());
						voieAAfficher.setNomSecteur(secteurLié.getNomSecteur());
						voieAAfficher.setIdSite(siteLié.getIdSite());
						//
						//Récupération des commentaires du Site et des utilisateurs ayant commenté
						ArrayList<Commentaire> commentaireVoie = accesBddCommentaire.trouverCommentairesParVoie(id);
						for(Commentaire comm:commentaireVoie) {
							if(comm.getContenuCommentaire()!=null && comm.getContenuCommentaire().equals("")==false) {
									//
									try {
										if(comm.getContenuCommentaire().substring(0, voieAAfficher.getNomVoie().length()+8).equals("[Voie: "+voieAAfficher.getNomVoie()+"]")) {
											comm.setContenuCommentaire(comm.getContenuCommentaire().substring(voieAAfficher.getNomVoie().length()+9));
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
						//Récupération des infos voies
						ArrayList<Voie> listedUneVoie =new ArrayList<Voie>();
						listedUneVoie.add(voieAAfficher);
						String[] minMax=Quotations.getMinMax(listedUneVoie);
						ArrayList<String> listeDesCotations=Quotations.getListCotation(voieAAfficher);
						String derniereCotation=listeDesCotations.get(listeDesCotations.size()-1);
						//
						//Affectation des variables
						request.setAttribute("minMax", minMax);
						request.setAttribute("listeDesCotations", listeDesCotations);
						request.setAttribute("derniereCotation", derniereCotation);
						commentaireVoie.sort(null);
						request.setAttribute("imgPath", imgPath);
						request.setAttribute("maintenant", aujourdhui);
						request.setAttribute("commentaireVoie", commentaireVoie);
						request.setAttribute("voiefound", voieAAfficher);
						this.getServletContext().getRequestDispatcher("/WEB-INF/voie.jsp").forward(request, response);
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
				contenuDuCommentaire="[Voie: "+ voieAAfficher.getNomVoie() +"] "+contenuDuCommentaire;
				//
				aujourdhui=new CalculDate();
				//def info de base du commentaire
				Commentaire newCommentaire = new Commentaire();
				newCommentaire.setContenuCommentaire(contenuDuCommentaire);
				newCommentaire.setDateCommentaire(aujourdhui.getTheDate().toString());
				newCommentaire.setIdVoie(id);
				newCommentaire.setIdSecteur(voieAAfficher.getIdSecteur());
				newCommentaire.setIdSite(voieAAfficher.getIdSite());
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
				accesBddCommentaire.ajouterCommentaire(newCommentaire,newCommentaire.getIdSecteur(),newCommentaire.getIdVoie());
				//
				
				//
			}
			//
			response.sendRedirect(request.getContextPath() + fromWho);
			//
		}

	}
