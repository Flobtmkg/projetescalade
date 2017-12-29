package interfacesdao;

import java.util.ArrayList;

import javabeans.Site;

public interface SiteDao {
	Site trouverSite(int idsite);
	ArrayList<Site> trouverListeSite(ArrayList<Integer> iddessites);
	ArrayList<Site> trouverVille();
	String ajouter(Site inputSite);
	int trouverLastIDSite();
}
