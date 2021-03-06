package interfacesdao;

import java.util.ArrayList;

import javabeans.Topo;

public interface TopoDao {
ArrayList<Topo> trouverToposParUtilisateur(int idproprietaire);
Topo trouverTopo(int idtopo);
ArrayList<Topo> trouverTopo(ArrayList<Integer> idTopoInput);
String ajoutTopo(Topo topoAAjouter);
Topo trouverTopoSansIdTopo(int idproprietaire, String titreTopo, String descriptionTopo);
ArrayList<Topo> trouverParCritere(String nomSiteInput, String keyWordInput, String disponibilite);
}
