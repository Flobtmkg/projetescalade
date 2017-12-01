package javabeans;

public class Secteur extends Site{
private int idSecteur;
private String nomSecteur;
private String descriptionSecteur;
private double hauteurSecteur;
//
//
public int getIdSecteur() {
	return idSecteur;
}
public void setIdSecteur(int idSecteur) {
	this.idSecteur = idSecteur;
}
public String getNomSecteur() {
	return nomSecteur;
}
public void setNomSecteur(String nomSecteur) {
	this.nomSecteur = nomSecteur;
}
public String getDescriptionSecteur() {
	return descriptionSecteur;
}
public void setDescriptionSecteur(String descriptionSecteur) {
	this.descriptionSecteur = descriptionSecteur;
}
public double getHauteurSecteur() {
	return hauteurSecteur;
}
public void setHauteurSecteur(double hauteurSecteur) {
	this.hauteurSecteur = hauteurSecteur;
}
}
