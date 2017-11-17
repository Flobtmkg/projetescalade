package projetescalade;

import java.util.ArrayList;

import javabeans.Topo;
import javabeans.Utilisateur;

public interface UtilisateurDao {
String ajouter(Utilisateur utilisateurinput);
Utilisateur verifier(String emailinput, String mdpinput);
Utilisateur trouver(int idutilisateurinput);
String modifierCol(String colonne,String enregValue,String whereId,int whereValue,boolean isDate,boolean isMdp);//les modifs de mot de passe ou de dates doivent être explicites
String modifier(Utilisateur utilisateurinput);//pas de modif de mot de passe
ArrayList<Utilisateur> trouverProprietaireTopo(ArrayList<Topo> topoListInput);
}
