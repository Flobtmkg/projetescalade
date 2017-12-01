package projetescalade;

import java.util.ArrayList;

import javabeans.Voie;

public interface VoieDao {
ArrayList<Voie> trouver(int idSecteurInput);
Voie trouverParVoieid(int idVoie);
ArrayList<Voie> trouverParCritere(String villeinput, String keyWordInput);
String ajouter(Voie inputVoie);
int trouverLastIDVoie();
}
