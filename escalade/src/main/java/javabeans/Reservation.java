package javabeans;

import java.time.LocalDate;

import projetescalade.CalculDate;

public class Reservation implements Comparable<Reservation>{
private int idReservation;
private int idUtilisateur;//utilisateur qui est connecté
private int idTopo;
private String datedebutReservation;
private String datefinReservation;
private String datedebutReservationFR;
private String datefinReservationFR;
private String commentaireReservation;
private boolean bilanReservation;
private Topo topoAssocié;
private Utilisateur proprietaireAssocié;//utilisateur qui est propriétaire du topo
private Utilisateur utilisateurQuiReserve;
//
//
public int getIdReservation() {
	return idReservation;
}
public void setIdReservation(int idReservation) {
	this.idReservation = idReservation;
}
public int getIdUtilisateur() {
	return idUtilisateur;
}
public void setIdUtilisateur(int idUtilisateur) {
	this.idUtilisateur = idUtilisateur;
}
public int getIdTopo() {
	return idTopo;
}
public void setIdTopo(int idTopo) {
	this.idTopo = idTopo;
}
public String getDatedebutReservation() {
	return datedebutReservation;
}
public void setDatedebutReservation(String datedebutReservation) {
	//
	try {
		String formatEN;
		if(datedebutReservation.contains("/")) {
			String[] formatFr = datedebutReservation.split("/");
			formatEN = formatFr[2]+"-"+formatFr[1]+"-"+formatFr[0];
			this.datedebutReservationFR=datedebutReservation;
		}else {
			//
		    @SuppressWarnings("unused")
			LocalDate inputDate = null;
			try {//Si la conversion passe c'est que tout va bien et on garde, si c'est catché on met le default 
				inputDate = LocalDate.parse(datedebutReservation);
				formatEN=datedebutReservation;
				this.datedebutReservationFR=CalculDate.conversionFormatFr(datedebutReservation);
			} catch (Exception e) {
				formatEN="0001-01-01";// default
			}
		}
			this.datedebutReservation = formatEN;
	}catch(Exception e) {
			this.datedebutReservation = "0001-01-01";// default
	}
	//
}
public String getDatefinReservation() {
	return datefinReservation;
}
public void setDatefinReservation(String datefinReservation) {
	try {
		String formatEN;
		if(datefinReservation.contains("/")) {
			String[] formatFr = datefinReservation.split("/");
			formatEN = formatFr[2]+"-"+formatFr[1]+"-"+formatFr[0];
			this.datefinReservationFR=datefinReservation;
		}else {
			//
		    @SuppressWarnings("unused")
			LocalDate inputDate = null;
			try {//Si la conversion passe c'est que tout va bien et on garde, si c'est catché on met le default 
				inputDate = LocalDate.parse(datefinReservation);
				formatEN=datefinReservation;
				this.datefinReservationFR=CalculDate.conversionFormatFr(datefinReservation);
			} catch (Exception e) {
				formatEN="0001-01-01";// default
			}
	}
			this.datefinReservation = formatEN;
	}catch(Exception e) {
			this.datefinReservation = "0001-01-01";// default
	}
	//
}
public String getCommentaireReservation() {
	return commentaireReservation;
}
public void setCommentaireReservation(String commentaireReservation) {
	this.commentaireReservation = commentaireReservation;
}
public Topo getTopoAssocié() {
	return topoAssocié;
}
public void setTopoAssocié(Topo topoAssocié) {
	this.topoAssocié = topoAssocié;
}
public Utilisateur getProprietaireAssocié() {
	return proprietaireAssocié;
}
public void setProprietaireAssocié(Utilisateur proprietaireAssocié) {
	this.proprietaireAssocié = proprietaireAssocié;
}
public Utilisateur getUtilisateurQuiReserve() {
	return utilisateurQuiReserve;
}
public void setUtilisateurQuiReserve(Utilisateur utilisateurQuiReserve) {
	this.utilisateurQuiReserve = utilisateurQuiReserve;
}
public boolean isBilanReservation() {
	return bilanReservation;
}
public void setBilanReservation(boolean bilanReservation) {
	this.bilanReservation = bilanReservation;
}
public String getDatedebutReservationFR() {
	return datedebutReservationFR;
}
public String getDatefinReservationFR() {
	return datefinReservationFR;
}
//
//
public int compareTo(Reservation autreReservation) {
	//
	if(this.idReservation>autreReservation.getIdReservation()) {
		return -1;
	}else if(this.idReservation<autreReservation.getIdReservation()) {
		return 1;
	}else {
		return 0;
	}
}

}
