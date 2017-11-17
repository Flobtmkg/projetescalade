package javabeans;

public class Topo implements Comparable<Topo>{
private int idTopo;
private String nomTopo;
private String descriptionTopo;
private int idProprietaire;
private boolean dispoTopo;
//
private Utilisateur proprietaire;
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
