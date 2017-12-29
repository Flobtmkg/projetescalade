package classestravail;

import factorydao.ConnexionDao;
import interfacesdao.NotificationDao;
import interfacesdao.ReservationDao;
import javabeans.Notification;
import javabeans.Reservation;
import javabeans.Utilisateur;

public class BilanReservation {
//
	private NotificationDao accesBddNotification;
	private ReservationDao accesBddReservation;
	//
	private ConnexionDao maconnexion;
//
	public BilanReservation(ConnexionDao connexionInput){
		this.maconnexion=connexionInput;
		this.accesBddNotification=maconnexion.getNotificationDao();
		this.accesBddReservation=maconnexion.getReservationDao();
	}
	
	public void bilanDeFinDeReservation(Reservation derniereReservation, Utilisateur proprietaire) {//A n'appeller que si le topo assossié est disponible!!!
		if(derniereReservation!=null) {
			if(derniereReservation.isBilanReservation()==false) {
				//Topo dispo mais bilan de la derniereReservation==false -> notification de bilan
				Notification notificationBilan=new Notification();
				notificationBilan.setIdTopo(derniereReservation.getIdTopo());
				notificationBilan.setIdUtilisateurDestinataire(derniereReservation.getIdUtilisateur());
				//notificationBilan.setIdUtilisateurExpediteur(proprietaire.getId());
				notificationBilan.setTypeNotification("Bilan de réservation");
				notificationBilan.setTraitementNotification("");
				notificationBilan.setParametreNotification(String.valueOf(derniereReservation.getIdReservation()));
				accesBddNotification.ajouter(notificationBilan);
				//modifier l'état de bilanreservation dans reservation!!!
				accesBddReservation.modifierBilan(true, derniereReservation.getIdReservation());
			}
		}
	}
}
