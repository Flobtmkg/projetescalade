package javabeans;

import projetescalade.CalculDate;

public class Commentaire implements Comparable<Commentaire>{
private int idCommentaire;
private int idUtilisateur;
private int idSite;
private String contenuCommentaire;
private String ipCommentaire;
private String dateCommentaire;
private String dateCommentaireFR;
private Site siteCommentaire;
//
private Utilisateur commentateur;
//
public int getIdCommentaire() {
	return idCommentaire;
}
public void setIdCommentaire(int idCommentaire) {
	this.idCommentaire = idCommentaire;
}
public int getIdUtilisateur() {
	return idUtilisateur;
}
public void setIdUtilisateur(int idUtilisateur) {
	this.idUtilisateur = idUtilisateur;
}
public int getIdSite() {
	return idSite;
}
public void setIdSite(int idSite) {
	this.idSite = idSite;
}
public String getContenuCommentaire() {
	return contenuCommentaire;
}
public void setContenuCommentaire(String contenuCommentaire) {
	this.contenuCommentaire = contenuCommentaire;
}
public String getIpCommentaire() {
	return ipCommentaire;
}
public void setIpCommentaire(String ipCommentaire) {
	this.ipCommentaire = ipCommentaire;
}
public String getDateCommentaire() {
	return dateCommentaire;
}
public void setDateCommentaire(String dateCommentaire) {
	this.dateCommentaire = dateCommentaire;
	this.dateCommentaireFR=CalculDate.conversionFormatFr(dateCommentaire);
}
public Site getSiteCommentaire() {
	return siteCommentaire;
}
public void setSiteCommentaire(Site siteCommentaire) {
	this.siteCommentaire = siteCommentaire;
}
public String getDateCommentaireFR() {
	return dateCommentaireFR;
}
////
public int compareTo(Commentaire autreCommentaire) {
	//
	if(this.idCommentaire>autreCommentaire.getIdCommentaire()) {
		return -1;
	}else if(this.idCommentaire<autreCommentaire.getIdCommentaire()) {
		return 1;
	}else {
		return 0;
	}
}
public Utilisateur getCommentateur() {
	return commentateur;
}
public void setCommentateur(Utilisateur commentateur) {
	this.commentateur = commentateur;
}

}
