package javabeans;

import java.time.LocalDate;

public class Utilisateur {
private int id;
private String nom;
private String prenom;
private String email;
private String motDePasse;
private String dateNaissance;
private String description;
private String pays;
private String ville;
//

public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, String dateNaissance, String description, String pays, String ville) {
	this.id=id;
	this.nom=nom;
	this.prenom=prenom;
	this.email=email;
	this.motDePasse=motDePasse;
	this.dateNaissance=dateNaissance;
	this.description=description;
	this.pays=pays;
	this.ville=ville;
}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//
	public String getNom() {
		return nom;
	}
	//
	public void setNom(String nom) {
		this.nom = nom;
	}
	//
	public String getPrenom() {
		return prenom;
	}
	//
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	//
	public String getEmail() {
		return email;
	}
	//
	public void setEmail(String email) {
		this.email = email;
	}
	//
	public String getMotDePasse() {
		return motDePasse;
	}
	//
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	//
	public String getDateNaissance() {
		return dateNaissance;
	}
	//
	public void setDateNaissance(String dateNaissance) {
		//
		try {
			String formatEN;
			if(dateNaissance.contains("/")) {
				String[] formatFr = dateNaissance.split("/");
				formatEN = formatFr[2]+"-"+formatFr[1]+"-"+formatFr[0];
			}else {
				//
			    @SuppressWarnings("unused")
				LocalDate inputDate = null;
				try {//Si la conversion passe c'est que tout va bien et on garde, si c'est catché on met le default
					inputDate=LocalDate.parse(dateNaissance);
					formatEN=dateNaissance;
				} catch (Exception e) {
					formatEN="0001-01-01";// default
				}
				//
			}
			this.dateNaissance = formatEN;
		}catch(Exception e) {
			this.dateNaissance = "0001-01-01";// default
		}
	}
	//
	public String getPays() {
		return pays;
	}
	//
	public void setPays(String pays) {
		this.pays = pays;
	}
	//
	public String getVille() {
		return ville;
	}
	//
	public void setVille(String ville) {
		this.ville = ville;
	}

}
