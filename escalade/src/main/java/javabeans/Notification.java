package javabeans;
//
public class Notification implements Comparable<Notification> {
	private int idNotification;
	private int idUtilisateurDestinataire;
	private int idUtilisateurExpediteur;
	private String typeNotification;
	private String traitementNotification;
	private String parametreNotification;
	private int idTopo;
	private Utilisateur utilisateurExpediteur;
	private Topo topoNotification;
	//
	//
	public int getIdNotification() {
		return idNotification;
	}
	public void setIdNotification(int idNotification) {
		this.idNotification = idNotification;
	}
	public int getIdUtilisateurDestinataire() {
		return idUtilisateurDestinataire;
	}
	public void setIdUtilisateurDestinataire(int idUtilisateurDestinataire) {
		this.idUtilisateurDestinataire = idUtilisateurDestinataire;
	}
	public int getIdUtilisateurExpediteur() {
		return idUtilisateurExpediteur;
	}
	public void setIdUtilisateurExpediteur(int idUtilisateurExpediteur) {
		this.idUtilisateurExpediteur = idUtilisateurExpediteur;
	}
	public String getTypeNotification() {
		return typeNotification;
	}
	public void setTypeNotification(String typeNotification) {
		this.typeNotification = typeNotification;
	}
	public Utilisateur getUtilisateurExpediteur() {
		return utilisateurExpediteur;
	}
	public void setUtilisateurExpediteur(Utilisateur utilisateurExpediteur) {
		this.utilisateurExpediteur = utilisateurExpediteur;
	}
	public String getParametreNotification() {
		return parametreNotification;
	}
	public void setParametreNotification(String parametreNotification) {
		this.parametreNotification = parametreNotification;
	}
	public int getIdTopo() {
		return idTopo;
	}
	public void setIdTopo(int idTopo) {
		this.idTopo = idTopo;
	}
	public Topo getTopoNotification() {
		return topoNotification;
	}
	public void setTopoNotification(Topo topoNotification) {
		this.topoNotification = topoNotification;
	}
	public String getTraitementNotification() {
		return traitementNotification;
	}
	public void setTraitementNotification(String traitementNotification) {
		this.traitementNotification = traitementNotification;
	}
	//
	//
	public int compareTo(Notification autreNotification) {
		if(this.idNotification>autreNotification.getIdNotification()) {
			return -1;
		}else if(this.idNotification<autreNotification.getIdNotification()) {
			return 1;
		}else {
			return 0;
		}
	}
}
