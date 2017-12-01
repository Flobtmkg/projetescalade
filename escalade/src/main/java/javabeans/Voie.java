package javabeans;

public class Voie extends Secteur{
private int idVoie;
private String nomVoie;
private String descriptionVoie;
private boolean equipementVoie;
private String quotationVoie;
private String photopath;
//
//
public int getIdVoie() {
	return idVoie;
}
public void setIdVoie(int idvoie) {
	this.idVoie = idvoie;
}
public String getDescriptionVoie() {
	return descriptionVoie;
}
public void setDescriptionVoie(String descriptionVoie) {
	this.descriptionVoie = descriptionVoie;
}
public boolean isEquipementVoie() {
	return equipementVoie;
}
public void setEquipementVoie(boolean equipementVoie) {
	this.equipementVoie = equipementVoie;
}
public String getQuotationVoie() {
	return quotationVoie;
}
public void setQuotationVoie(String quotationVoie) {
	this.quotationVoie = quotationVoie;
}
public String getNomVoie() {
	return nomVoie;
}
public void setNomVoie(String nomVoie) {
	this.nomVoie = nomVoie;
}
public String getPhotopath() {
	return photopath;
}
public void setPhotopath(String photopath) {
	this.photopath = photopath;
}
}
