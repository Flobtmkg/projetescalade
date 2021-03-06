package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.SiteDao;
import javabeans.Site;

public class BddSitesDao implements SiteDao{
	//
	private ConnexionDao connexionEnCours;
	//
	public BddSitesDao(ConnexionDao connexioninput){
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
	        	 siteOutput.setIdSite(resultat.getInt("idsite"));
	        	 siteOutput.setNomSite(CodageGuillemets.getTexteDecode(resultat.getString("nomsite")));
	        	 siteOutput.setPaysSite(CodageGuillemets.getTexteDecode(resultat.getString("payssite")));
	        	 siteOutput.setVilleSite(CodageGuillemets.getTexteDecode(resultat.getString("villesite")));
	        	 siteOutput.setLatitudeSite(resultat.getDouble("latitudesite"));
	        	 siteOutput.setLongitudeSite(resultat.getDouble("longitudesite"));
	        	 siteOutput.setDescriptionSite(CodageGuillemets.getTexteDecode(resultat.getString("descriptionsite")));
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
	        	 chaqueSite.setIdSite(resultat.getInt("idsite"));
	        	 chaqueSite.setNomSite(CodageGuillemets.getTexteDecode(resultat.getString("nomsite")));
	        	 chaqueSite.setPaysSite(CodageGuillemets.getTexteDecode(resultat.getString("payssite")));
	        	 chaqueSite.setVilleSite(CodageGuillemets.getTexteDecode(resultat.getString("villesite")));
	        	 chaqueSite.setLatitudeSite(resultat.getDouble("latitudesite"));
	        	 chaqueSite.setLongitudeSite(resultat.getDouble("longitudesite"));
	        	 chaqueSite.setDescriptionSite(CodageGuillemets.getTexteDecode(resultat.getString("descriptionsite")));
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
	//
	//
	//
	//
	public ArrayList<Site> trouverVille() {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Site> sitesOutput=new ArrayList<Site>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT DISTINCT villesite,payssite FROM site;");
	         while(resultat.next()) {
	        	 Site chaqueSite=new Site();
	        	 chaqueSite.setVilleSite(CodageGuillemets.getTexteDecode(resultat.getString(1)));
	        	 chaqueSite.setPaysSite(CodageGuillemets.getTexteDecode(resultat.getString(2)));
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
	//
	//
	public int trouverLastIDSite() {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        int dernierid = 0;
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT MAX(idsite) FROM site;");
	         while(resultat.next()) {
	        	 dernierid=resultat.getInt(1);
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
		return dernierid;
	}
	//
	//
	public String ajouter(Site inputSite) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement = connexion.prepareStatement("INSERT INTO site(nomsite,payssite,villesite,latitudesite,longitudesite,descriptionsite) VALUES(?,?,?,?,?,?);");
		    preparedStatement.setString(1, CodageGuillemets.getTexteEncode(inputSite.getNomSite()));
		    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(inputSite.getPaysSite()));
		    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(inputSite.getVilleSite()));
		    preparedStatement.setDouble(4, inputSite.getLatitudeSite());
		    preparedStatement.setDouble(5, inputSite.getLongitudeSite());
		    preparedStatement.setString(6, CodageGuillemets.getTexteEncode(inputSite.getDescriptionSite()));
		    preparedStatement.executeUpdate();
		    connexion.commit();
		} catch (SQLException e) {
			erreurEventuelle=erreurEventuelle + e.getSQLState() + ";";
			e.printStackTrace();
		   	try {
		   		connexion.rollback();
			} catch (SQLException e1) {
				erreurEventuelle=erreurEventuelle + e.getSQLState() + ";";
				e.printStackTrace();
			}
		}finally {
		   	try {
		   		connexion.close();
				preparedStatement.close();
			} catch (SQLException e) {
				erreurEventuelle=erreurEventuelle + e.getSQLState() + ";";
				e.printStackTrace();
			}
		}
		return erreurEventuelle;
	}

}
