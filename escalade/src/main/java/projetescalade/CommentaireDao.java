package projetescalade;

import java.util.ArrayList;

import javabeans.Commentaire;


public interface CommentaireDao {
	ArrayList<Commentaire> trouverCommentairesParUtilisateur(int idutilisateur);
	ArrayList<Commentaire> trouverCommentairesParSite(int idSite);
	String ajouterCommentaire(Commentaire commentaireInput);
}
