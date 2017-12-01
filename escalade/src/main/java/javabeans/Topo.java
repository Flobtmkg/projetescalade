package javabeans;

import java.util.ArrayList;

public class Topo implements Comparable<Topo>{
private int idTopo;
private String nomTopo;
private String descriptionTopo;
private int idProprietaire;
private boolean dispoTopo;
//propriétés rajoutés pour le besoin de la requète de recherche topo
private String photopath;
private Utilisateur proprietaire;
private Site siteAssocie;
private ArrayList<Site> sitesAssocies;
//
public int getIdTopo() {
	return idTopo;
}
public void setIdTopo(int idTopo) {
	this.idTopo = idTopo;
}
public String getNomTopo() {
	return nomTopo;
}
public void setNomTopo(String nomTopo) {
	this.nomTopo = nomTopo;
}
public String getDescriptionTopo() {
	return descriptionTopo;
}
public void setDescriptionTopo(String descriptionTopo) {
	this.descriptionTopo = descriptionTopo;
}
public int getIdProprietaire() {
	return idProprietaire;
}
public void setIdProprietaire(int idProprietaire) {
	this.idProprietaire = idProprietaire;
}
public boolean isDispoTopo() {
	return dispoTopo;
}
public void setDispoTopo(boolean dispoTopo) {
	this.dispoTopo = dispoTopo;
}
public Utilisateur getProprietaire() {
	return proprietaire;
}
public void setProprietaire(Utilisateur proprietaire) {
	this.proprietaire = proprietaire;
}
public String getPhotopath() {
	return photopath;
}
public void setPhotopath(String photopath) {
	this.photopath = photopath;
}
public Site getSiteAssocie() {
	return siteAssocie;
}
public void setSiteAssocie(Site siteAssocie) {
	this.siteAssocie = siteAssocie;
}
public ArrayList<Site> getSitesAssocies() {
	return sitesAssocies;
}
public void setSitesAssocies(ArrayList<Site> sitesAssocies) {
	this.sitesAssocies = sitesAssocies;
}
//
//
public int compareTo(Topo autreTopo) {
	//
	if(this.idTopo>autreTopo.getIdTopo()) {
		return -1;
	}else if(this.idTopo<autreTopo.getIdTopo()) {
		return 1;
	}else {
		return 0;
	}
}
}
