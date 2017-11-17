package projetescalade;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import javabeans.Notification;
import javabeans.PhotoTopo;
import javabeans.PhotoUtilisateur;
import javabeans.Reservation;
import javabeans.Topo;
import javabeans.Utilisateur;

public class ServletEspaceUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao accesBddUtilisateur;
	private NotificationDao accesBddNotification;
	private ReservationDao accesBddReservation;
	private TopoDao accesBddTopo;
	private PhotoUtilisateurDao accesBddPhotoUtilisateur;
	//
	private ConnexionDao maconnexion;
	//
	private Notification notificationClick;//La notification sur laquelle on a cliqué
	private Utilisateur UtilisateurConnecte;//L'utilisateur connecté
	//

	public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance();
        this.accesBddUtilisateur = maconnexion.getUtilisateurDao();
        this.accesBddNotification=maconnexion.getNotificationDao();
        this.accesBddReservation=maconnexion.getReservationDao();
        this.accesBddTopo=maconnexion.getTopoDao();
        this.accesBddPhotoUtilisateur=maconnexion.getPhotoUtilisateurDao();
    }
	
    public ServletEspaceUtilisateur() {
        super();
        //
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession sessionEnCours=request.getSession();
		UtilisateurConnecte=(Utilisateur) sessionEnCours.getAttribute("utilisateurencours");
		//
		//Pas de connexion utilisateur -> pas d'accès possible
		if(UtilisateurConnecte!=null) {
				//
				//récupération du parametre pour l'ouverture des messages si un messages est cliqué
				String mess =request.getParameter("mess");
				int nbrNew=Integer.parseInt(String.valueOf(sessionEnCours.getAttribute("nouvellesNotifications")));
				notificationClick=new Notification();
				ArrayList<Notification> lesmessages= (ArrayList<Notification>)sessionEnCours.getAttribute("notificationDelUtilisateur");
				boolean trouveOuPas=false;
				if(mess!=null) {
					for(Notification idNot:lesmessages) {
						if(mess.equals(String.valueOf(idNot.getIdNotification()))) {
							notificationClick=idNot;
							if(notificationClick.getTypeNotification().equals("Demande de réservation")==false && notificationClick.getTypeNotification().equals("Bilan de réservation")==false) {
								//Le message à bien été lu
								accesBddNotification.definirTraitement(notificationClick.getIdNotification(), true);
								idNot.setTraitementNotification("true");
								if(nbrNew>0) {
									nbrNew=nbrNew-1;
								}
							}
							notificationClick.setTopoNotification(accesBddTopo.trouverTopo(notificationClick.getIdTopo()));
							trouveOuPas=true;
						}
					}
				}else {
					trouveOuPas=true;
				}
				if(trouveOuPas==false) {
					response.sendRedirect(request.getContextPath() + "/accueil");
				}else {
					//
					//cookie important pour tracer la dernière page atteinte par l'utilisateur
					String adresse =request.getRequestURI();
					response.addCookie(new Cookie("last",adresse));
					//
					//
					//Récupération adress img
					PhotoUtilisateur relationPhotoUtilisateur= accesBddPhotoUtilisateur.getPhotoPath(UtilisateurConnecte.getId());
					//String hostURL="http://"+request.getServerName() + ":" + request.getServerPort()+"/escalade/img/";
					String imgPath=null;
					if(relationPhotoUtilisateur.getPathPhoto()!=null) {
						imgPath="/escalade/img/"+relationPhotoUtilisateur.getPathPhoto();
					}
					//
					//Récupération date et l'age de l'utilisateur
					CalculDate aujourdhui = new CalculDate();
					request.setAttribute("maintenant", aujourdhui);
					//
					String annees;
					if(UtilisateurConnecte.getDateNaissance().equals("0001-01-01")==false) {
						annees=String.valueOf(aujourdhui.diff(LocalDate.parse(UtilisateurConnecte.getDateNaissance())));
					}else {
						annees="Indéterminé";
					}
					//
					sessionEnCours.setAttribute("notificationDelUtilisateur",lesmessages);
					sessionEnCours.setAttribute("nouvellesNotifications",nbrNew);
					request.setAttribute("imgPath", imgPath);
					request.setAttribute("notificationClick", notificationClick);
					request.setAttribute("age", annees);
					String dateFr = CalculDate.conversionFormatFr(UtilisateurConnecte.getDateNaissance());
					request.setAttribute("datefr", dateFr);
					this.getServletContext().getRequestDispatcher("/WEB-INF/espaceutilisateur.jsp").forward(request, response);
				}
		}else {
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession sessionEnCours=request.getSession();
		Utilisateur utilisateurConnecte=(Utilisateur)sessionEnCours.getAttribute("utilisateurencours");
		//
		request.setCharacterEncoding("UTF-8");
		//
		String mdpA=(String) request.getParameter("motDePasseA");
		String mdpN=(String) request.getParameter("motDePasseN");
		String mdpN2=(String) request.getParameter("motDePasseN2");//Le test de la non-nullité d'une variable mdpN2 permet de déterminer les differents renvoi en Post
		String testTypeDePost =request.getParameter("prenom");//Le test de la non-nullité d'une variable prenom permet de déterminer les differents renvoi en Post
		String commentaire =request.getParameter("commentaire");//Le test de la non-nullité d'une variable commentaire permet de déterminer les differents renvoi en Post
		String reponseUtilisateur = request.getParameter("choix");//Le test de la non-nullité d'une variable choix (acceptation/refus) permet de déterminer les differents renvoi en Post
		String enregImage = request.getParameter("enregImage");//Le test de la non-nullité d'une variable enregImage permet de déterminer les differents renvoi en Post
		//
		//Quel renvoi en post?
		int indiceDeRenvoi=0;
		if(mdpN2!=null) {
			//modif mot de passe
			indiceDeRenvoi=1;
			//
		}else if(testTypeDePost!=null) {
			//modif données de base
			indiceDeRenvoi=2;
			//
		}else if(reponseUtilisateur!=null) {
			//Notification: Validation/refus de demande
			indiceDeRenvoi=3;
			//
		}else if(commentaire!=null) {
			//Notification: édite commentaire reservation
			indiceDeRenvoi=4;
			//
		}else if(enregImage!=null) {
			//Notification: édite l'image
			indiceDeRenvoi=5;
			//
		}
		//
		//
		//
		if(indiceDeRenvoi==1) {
			//Post de modification de mot de passe
			if(mdpN.equals(mdpN2)) {
				Utilisateur nouvelutilisateur=new Utilisateur(0,"","","","","","","","");
				nouvelutilisateur=accesBddUtilisateur.verifier(utilisateurConnecte.getEmail(), mdpA);
				if(nouvelutilisateur.getId()== utilisateurConnecte.getId()) {
					String erreurs=accesBddUtilisateur.modifierCol("mdputilisateur", mdpN, "idutilisateur", utilisateurConnecte.getId(), false, true);
					if(erreurs.equals("")) {
						nouvelutilisateur.setMotDePasse(mdpN);
						sessionEnCours.setAttribute("utilisateurencours", nouvelutilisateur);
						response.sendRedirect(request.getContextPath() + "/espaceutilisateur#ModalValidation");
					}else {
						response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
					}		
				}else {
					response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
				}
			}else {
				response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
			}
			//
			//
			//
		}else if(indiceDeRenvoi==2) {
			//Post de modification des données de base
			Utilisateur utilisateurAvantReqt= new Utilisateur(utilisateurConnecte.getId(),utilisateurConnecte.getNom(),utilisateurConnecte.getPrenom(),utilisateurConnecte.getEmail(),utilisateurConnecte.getMotDePasse(),utilisateurConnecte.getDateNaissance(),utilisateurConnecte.getDescription(),utilisateurConnecte.getPays(),utilisateurConnecte.getVille());
			utilisateurConnecte.setPrenom(request.getParameter("prenom"));
			utilisateurConnecte.setNom(request.getParameter("nom"));
			utilisateurConnecte.setDescription(request.getParameter("description"));
			utilisateurConnecte.setDateNaissance(request.getParameter("dateNaissance"));
			utilisateurConnecte.setEmail(request.getParameter("email"));
			utilisateurConnecte.setPays(request.getParameter("pays"));
			utilisateurConnecte.setVille(request.getParameter("ville"));		
			String erreurs=accesBddUtilisateur.modifier(utilisateurConnecte);
			if(erreurs.equals("")) {
				sessionEnCours.setAttribute("utilisateurencours", utilisateurConnecte);
				response.sendRedirect(request.getContextPath() + "/espaceutilisateur#ModalValidation");
			}else {
				sessionEnCours.setAttribute("utilisateurencours", utilisateurAvantReqt);
				response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
			}
			//
			//
			//
		}else if(indiceDeRenvoi==3) {
			//Post de d'interaction notification demande de reservation
			if(reponseUtilisateur.equals("Accepter")) {
				String parametres[]=notificationClick.getParametreNotification().split("&");
				DisponibiliteTopo dispoTopo=new DisponibiliteTopo(maconnexion);
				if(dispoTopo.isDisponible(notificationClick.getIdTopo(), parametres[0], parametres[1])) {	//disponibilité sur toute la période de la réservation
					//Nouvelle notification retour avec confirmation
					Notification notificationConfirmation = new Notification();
					notificationConfirmation.setIdUtilisateurDestinataire(notificationClick.getIdUtilisateurExpediteur());
					notificationConfirmation.setIdUtilisateurExpediteur(notificationClick.getIdUtilisateurDestinataire());
					notificationConfirmation.setTypeNotification("Confirmation de réservation");
					notificationConfirmation.setParametreNotification(UtilisateurConnecte.getEmail());
					notificationConfirmation.setIdTopo(notificationClick.getIdTopo());
					accesBddNotification.ajouter(notificationConfirmation);
					//Ajout d'une réservation
					//si topo dispo (si pas de supperposition de dates)
				
					Reservation nouvelleReservation = new Reservation();
					nouvelleReservation.setIdUtilisateur(notificationClick.getIdUtilisateurExpediteur());
					nouvelleReservation.setIdTopo(notificationClick.getIdTopo());
					nouvelleReservation.setDatedebutReservation(parametres[0]);
					nouvelleReservation.setDatefinReservation(parametres[1]);
					nouvelleReservation.setBilanReservation(false);
					accesBddReservation.ajout(nouvelleReservation);
					//
					//
					//notification traitée et actualisée
					int nbrNew=Integer.parseInt(String.valueOf(sessionEnCours.getAttribute("nouvellesNotifications")));
					accesBddNotification.definirTraitement(notificationClick.getIdNotification(), true);
					ArrayList<Notification> listeDesNotifications =(ArrayList<Notification>)sessionEnCours.getAttribute("notificationDelUtilisateur");
					for(Notification notif:listeDesNotifications) {
						if(notif.getIdNotification()==notificationClick.getIdNotification()) {
							notif.setTraitementNotification("true");
								if(nbrNew>0) {
									nbrNew=nbrNew-1;
								}
						}
					}
					sessionEnCours.setAttribute("nouvellesNotifications", nbrNew);
					sessionEnCours.setAttribute("notificationDelUtilisateur", listeDesNotifications);
					response.sendRedirect(request.getContextPath() + "/espaceutilisateur");
				}else {
					//erreur la plage de date n'est pas/plus disponible!! -> annulation forcée
					//Nouvelle notification retour avec Annulation
					Notification notificationAnnulation = new Notification();
					notificationAnnulation.setIdUtilisateurDestinataire(notificationClick.getIdUtilisateurExpediteur());
					notificationAnnulation.setIdUtilisateurExpediteur(notificationClick.getIdUtilisateurDestinataire());
					notificationAnnulation.setTypeNotification("Annulation de réservation");
					notificationAnnulation.setIdTopo(notificationClick.getIdTopo());
					accesBddNotification.ajouter(notificationAnnulation);
					//notification traitée et actualisée
					int nbrNew=Integer.parseInt(String.valueOf(sessionEnCours.getAttribute("nouvellesNotifications")));
					accesBddNotification.definirTraitement(notificationClick.getIdNotification(), false);
					ArrayList<Notification> listeDesNotifications =(ArrayList<Notification>)sessionEnCours.getAttribute("notificationDelUtilisateur");
					for(Notification notif:listeDesNotifications) {
						if(notif.getIdNotification()==notificationClick.getIdNotification()) {
							notif.setTraitementNotification("false");
								if(nbrNew>0) {
									nbrNew=nbrNew-1;
								}
						}
					}
					sessionEnCours.setAttribute("nouvellesNotifications", nbrNew);
					sessionEnCours.setAttribute("notificationDelUtilisateur", listeDesNotifications);
					response.sendRedirect(request.getContextPath() + "/espaceutilisateur#ModalerreurConfirmation");
				}
				
			}else {
				//Nouvelle notification retour avec Annulation
				Notification notificationAnnulation = new Notification();
				notificationAnnulation.setIdUtilisateurDestinataire(notificationClick.getIdUtilisateurExpediteur());
				notificationAnnulation.setIdUtilisateurExpediteur(notificationClick.getIdUtilisateurDestinataire());
				notificationAnnulation.setTypeNotification("Annulation de réservation");
				notificationAnnulation.setIdTopo(notificationClick.getIdTopo());
				accesBddNotification.ajouter(notificationAnnulation);
				//notification traitée
				int nbrNew=Integer.parseInt(String.valueOf(sessionEnCours.getAttribute("nouvellesNotifications")));
				accesBddNotification.definirTraitement(notificationClick.getIdNotification(), false);
				ArrayList<Notification> listeDesNotifications =(ArrayList<Notification>)sessionEnCours.getAttribute("notificationDelUtilisateur");
				for(Notification notif:listeDesNotifications) {
					if(notif.getIdNotification()==notificationClick.getIdNotification()) {
						notif.setTraitementNotification("false");
						if(nbrNew>0) {
							nbrNew=nbrNew-1;
						}
					}
				}
				sessionEnCours.setAttribute("nouvellesNotifications", nbrNew);
				sessionEnCours.setAttribute("notificationDelUtilisateur", listeDesNotifications);
				response.sendRedirect(request.getContextPath() + "/espaceutilisateur");
			}
			//
			//
			//
		}else if(indiceDeRenvoi==4) {
			//Post de d'interaction notification Commentaire reservation
			accesBddReservation.modifierString("commentairereservation", commentaire, Integer.parseInt(notificationClick.getParametreNotification()));
			//notification traitée
			int nbrNew=Integer.parseInt(String.valueOf(sessionEnCours.getAttribute("nouvellesNotifications")));
			accesBddNotification.definirTraitement(notificationClick.getIdNotification(), true);
			ArrayList<Notification> listeDesNotifications =(ArrayList<Notification>)sessionEnCours.getAttribute("notificationDelUtilisateur");
			for(Notification notif:listeDesNotifications) {
				if(notif.getIdNotification()==notificationClick.getIdNotification()) {
					notif.setTraitementNotification("true");
					if(nbrNew>0) {
						nbrNew=nbrNew-1;
					}
				}
			}
			sessionEnCours.setAttribute("nouvellesNotifications", nbrNew);
			sessionEnCours.setAttribute("notificationDelUtilisateur", listeDesNotifications);
			response.sendRedirect(request.getContextPath() + "/espaceutilisateur");			
		}else if(indiceDeRenvoi==5) {
			//
			//fichier
			//
			String nomFichier=Photo.ajout(request.getPart("fichier"),getServletContext().getInitParameter("filePath"),utilisateurConnecte.getId());
			if(nomFichier.equals("Erreur")==false) {
				accesBddPhotoUtilisateur.ajoutPhoto(utilisateurConnecte.getId(), nomFichier);
			}
			response.sendRedirect(request.getContextPath() + "/espaceutilisateur");
			//
			//
		}
		
	}

}
