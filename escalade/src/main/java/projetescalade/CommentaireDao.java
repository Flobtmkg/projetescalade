package projetescalade;

import java.util.ArrayList;

import javabeans.Commentaire;


public interface CommentaireDao {
	ArrayList<Commentaire> trouverCommentairesParUtilisateur(int idutilisateur);
	ArrayList<Commentaire> trouverCommentairesParSite(int idSite);
	ArrayList<Commentaire> trouverCommentairesParSecteur(int idSecteur);
	ArrayList<Commentaire> trouverCommentairesParVoie(int idVoie);
	String ajouterCommentaire(Commentaire commentaireInput,int idSecteur, int idVoie);
}
