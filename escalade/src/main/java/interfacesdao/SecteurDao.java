package interfacesdao;

import java.util.ArrayList;

import javabeans.Secteur;

public interface SecteurDao {
ArrayList<Secteur> trouver(int idSiteInput);
Secteur trouverSecteur(int idSecteurInput);
String ajouter(Secteur inputSecteur);
int trouverLastIDSite();
ArrayList<Secteur> toutLesSecteurs();
}
