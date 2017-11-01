package javabeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utilisateur {
private int id;
private String nom;
private String prenom;
private String email;
private String motDePasse;
private String dateNaissance;
private String description;
//
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
	private String pays;
	private String ville;
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
				SimpleDateFormat conversiondedate = new SimpleDateFormat("yyyy-MM-dd");
			    @SuppressWarnings("unused")
				java.util.Date utilDate = null;
				try {//Si la conversion passe c'est que tout va bien et on garde, si c'est catché on met le default 
					utilDate = conversiondedate.parse(dateNaissance);
					formatEN=dateNaissance;
				} catch (ParseException e) {
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
