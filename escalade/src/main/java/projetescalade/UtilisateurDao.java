package projetescalade;

import javabeans.Utilisateur;

public interface UtilisateurDao {
String ajouter(Utilisateur utilisateurinput);
Utilisateur verifier(String emailinput, String mdpinput);
String modifier(String colonne,String enregValue,String whereId,int whereValue,boolean isDate,boolean isMdp);
}
