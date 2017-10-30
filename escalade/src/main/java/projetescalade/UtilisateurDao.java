package projetescalade;

import javabeans.Utilisateur;

public interface UtilisateurDao {
void ajouter(Utilisateur utilisateurinput);
Utilisateur verifier(String emailinput, String mdpinput);
}
