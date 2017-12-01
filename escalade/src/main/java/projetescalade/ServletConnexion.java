package projetescalade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Commentaire;
import javabeans.Notification;
import javabeans.Reservation;
import javabeans.Site;
import javabeans.Topo;
import javabeans.Utilisateur;


public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao accesBddUtilisateur;
	private TopoDao accesBddTopo;
	private ReservationDao accesBddReservation;
	private CommentaireDao accesBddCommentaire;
	private SiteDao accesBddSite;
	private NotificationDao accesBddNotification;
	//
	private ConnexionDao maconnexion;

    public ServletConnexion() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance();
        this.accesBddUtilisateur = maconnexion.getUtilisateurDao();
        this.accesBddTopo = maconnexion.getTopoDao();
        this.accesBddReservation=maconnexion.getReservationDao();
        this.accesBddCommentaire=maconnexion.getCommentaireDao();
        this.accesBddSite=maconnexion.getSiteDao();
        this.accesBddNotification=maconnexion.getNotificationDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Gestion de la connexion et de la session
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//courtcircuiter la proc�dure si idadmin+mdpadmin
		if(request.getParameter("email").equals(getServletContext().getInitParameter("idadmin")) && request.getParameter("motDePasse").equals(getServletContext().getInitParameter("mdpadmin"))) {
			HttpSession session= request.getSession();
			session.setAttribute("Admin","true");
			response.sendRedirect(request.getContextPath() + "/ajoutadministrateur");
	   }else {
			//
			Utilisateur nouvelutilisateur=accesBddUtilisateur.verifier(request.getParameter("email"), request.getParameter("motDePasse"));
			HttpSession session= request.getSession();
			session.setAttribute("utilisateurencours", nouvelutilisateur);
			//
				if(nouvelutilisateur.getId()==0) {
					response.sendRedirect(request.getContextPath() + fromWho +"#Modal");
				}else {
					//
					//utilisateur identifi� et chargement des donn�es li� au profil
					//
					//topos que poss�de l'utilisateur
					ArrayList<Topo> topoDelUtilisateur=accesBddTopo.trouverToposParUtilisateur(nouvelutilisateur.getId());
					//affectation de la disponibilit� des topo
					for(Topo top:topoDelUtilisateur) {
						DisponibiliteTopo dispotopo = new DisponibiliteTopo(maconnexion);
						top.setDispoTopo(dispotopo.isDisponible(top.getIdTopo()));
						//definir si c'est la fin d'une periode de r�servation et envoyer une notification de bilan
						if(top.isDispoTopo()) {
							Reservation res = accesBddReservation.getLastReservationDuTopo(top.getIdTopo());
							if(res.getIdReservation()!=0) {
								BilanReservation bilan=new BilanReservation(maconnexion);
								bilan.bilanDeFinDeReservation(res,nouvelutilisateur);
							}
							//
						}
						//
					}
					//On organise les topo de mani�re � afficher le plus r�cent en 1er
					topoDelUtilisateur.sort(null);
					//
					//
					//r�servations effectu�es par l'utilisateur avec les topos correspondantes et leur propri�taire
					ArrayList<Reservation> reservationsDelUtilisateur=accesBddReservation.getReservationUtilisateur(nouvelutilisateur.getId());
					//
					for(Reservation res :reservationsDelUtilisateur) {
						Topo toporecherche=accesBddTopo.trouverTopo(res.getIdTopo());
						Utilisateur proprietairerecherche=accesBddUtilisateur.trouver(toporecherche.getIdProprietaire());
						res.setTopoAssoci�(toporecherche);
						res.setProprietaireAssoci�(proprietairerecherche);
						//definir si c'est la fin d'une periode de r�servation et envoyer une notification de bilan
						if(toporecherche.isDispoTopo()) {
							BilanReservation bilan=new BilanReservation(maconnexion);
							bilan.bilanDeFinDeReservation(res,proprietairerecherche);
							//
						}
					}
					//On organise les reservations de mani�re � afficher la plus r�cente en 1er
					reservationsDelUtilisateur.sort(null);
					////Commentaires laiss�s par l'utilisateur avec le site correspondant
					ArrayList<Commentaire> commentairesDelUtilisateur=accesBddCommentaire.trouverCommentairesParUtilisateur(nouvelutilisateur.getId());
					//
					for(Commentaire comm :commentairesDelUtilisateur) {
						Site siterecherche=accesBddSite.trouverSite(comm.getIdSite());
						comm.setSiteCommentaire(siterecherche);
					}
					//On organise les commentaires de mani�re � afficher le plus r�cent en 1er
					commentairesDelUtilisateur.sort(null);
					//Notification de l'utilisateur avec l'exp�diteur associ�
					ArrayList<Notification> notificationDelUtilisateur=accesBddNotification.trouverNotificationsParUtilisateur(nouvelutilisateur.getId());
					//
					int nouvellesNotifications=0;
					//
					for(Notification notif :notificationDelUtilisateur) {
						Utilisateur expediteur=accesBddUtilisateur.trouver(notif.getIdUtilisateurExpediteur());
						notif.setUtilisateurExpediteur(expediteur);
						if(notif.getTraitementNotification()==null||notif.getTraitementNotification().equals("")){
							nouvellesNotifications++;
						}
					}
					//On organise les notifications de mani�re � afficher la plus r�cente en 1er
					notificationDelUtilisateur.sort(null);
					//
					//
					session.setAttribute("nouvellesNotifications",nouvellesNotifications);
					session.setAttribute("notificationDelUtilisateur", notificationDelUtilisateur);
					session.setAttribute("commentairesDelUtilisateur", commentairesDelUtilisateur);
					session.setAttribute("topoDelUtilisateur", topoDelUtilisateur);
					session.setAttribute("reservationDelUtilisateur", reservationsDelUtilisateur);
					//
					response.sendRedirect(request.getContextPath() + fromWho);
				}
			}
		}
		
}
