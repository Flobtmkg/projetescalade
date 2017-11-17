package projetescalade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javabeans.Site;

public class BddSitesDao implements SiteDao{
	//
	private ConnexionDao connexionEnCours;
	//
	BddSitesDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}
	public Site trouverSite(int idsite) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Site siteOutput=new Site();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM site WHERE idsite='"+idsite+"'");
	         while(resultat.next()) {
	        	 siteOutput.setIdSite(resultat.getInt(1));
	        	 siteOutput.setNomSite(CodageGuillemets.getTexteDecode(resultat.getString(2)));
	        	 siteOutput.setPaysSite(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 siteOutput.setVilleSite(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 siteOutput.setLatitudeSite(resultat.getDouble(5));
	        	 siteOutput.setLongitudeSite(resultat.getDouble(6));
	        	 siteOutput.setDescriptionSite(CodageGuillemets.getTexteDecode(resultat.getString(7)));
	         }
        }catch(SQLException e) {
        	e.printStackTrace();
        }finally {
        	try {
				connexion.close();
				statement.close();
	        	resultat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		return siteOutput;
	}
	//
	//
	public ArrayList<Site> trouverListeSite(ArrayList<Integer> iddessites) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Site> sitesOutput=new ArrayList<Site>();
        String clauseWhere="";
        if(iddessites.isEmpty()) {
        	return sitesOutput;
        }else if(iddessites.get(0)==0){
        	clauseWhere="1=1";
        }else {
        	 for(int id:iddessites) {
             	clauseWhere=clauseWhere+"idsite="+id+" OR ";
             }
             clauseWhere=clauseWhere.substring(0, clauseWhere.length()-4);
        }
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM site WHERE "+clauseWhere);
	         while(resultat.next()) {
	        	 Site chaqueSite=new Site();
	        	 chaqueSite.setIdSite(resultat.getInt(1));
	        	 chaqueSite.setNomSite(CodageGuillemets.getTexteDecode(resultat.getString(2)));
	        	 chaqueSite.setPaysSite(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueSite.setVilleSite(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueSite.setLatitudeSite(resultat.getDouble(5));
	        	 chaqueSite.setLongitudeSite(resultat.getDouble(6));
	        	 chaqueSite.setDescriptionSite(CodageGuillemets.getTexteDecode(resultat.getString(7)));
	        	 sitesOutput.add(chaqueSite);
	         }
        }catch(SQLException e) {
        	e.printStackTrace();
        }finally {
        	try {
				connexion.close();
				statement.close();
	        	resultat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		return sitesOutput;
	}

}
