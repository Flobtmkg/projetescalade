package projetescalade;

import java.util.ArrayList;

import javabeans.Site;

public interface SiteDao {
	Site trouverSite(int idsite);
	ArrayList<Site> trouverListeSite(ArrayList<Integer> iddessites);
}
