package projetescalade;

import java.util.ArrayList;

import javabeans.LienTopoSite;

public interface TopoSitesDao {
	
	ArrayList<Integer> trouverSitesConcerneParTopo(int idtopo);
	ArrayList<Integer> trouverTopoConcerneParSite(int idSite);
	ArrayList<Integer> trouverToposDecrivantSite(int idsite);
	String ajoutTopoSites(ArrayList<LienTopoSite> relation);
}
