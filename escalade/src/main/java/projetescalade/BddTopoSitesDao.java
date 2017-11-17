package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javabeans.LienTopoSite;
import javabeans.Reservation;

public class BddTopoSitesDao implements TopoSitesDao {
	//
	private ConnexionDao connexionEnCours;
	//
	BddTopoSitesDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}
	public ArrayList<Integer> trouverSitesConcerneParTopo(int idtopo) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Integer>idDesSites=new ArrayList<Integer>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM toposites WHERE idtopo='"+idtopo+"'");
	         while(resultat.next()) {
	        	 idDesSites.add(resultat.getInt(3));
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
		return idDesSites;
	}
	//
	//
	public ArrayList<Integer> trouverToposDecrivantSite(int idsite) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Integer>idDesTopos=new ArrayList<Integer>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM toposites WHERE idsite='"+idsite+"'");
	         while(resultat.next()) {
	        	 idDesTopos.add(resultat.getInt(2));
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
		return idDesTopos;
	}
	//
	//
	public String ajoutTopoSites(ArrayList<LienTopoSite> relation) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    //
		    for(LienTopoSite contenuTopoSite:relation) {
		    	preparedStatement = connexion.prepareStatement("INSERT INTO toposites(idtopo,idsite) VALUES(?,?);");
			    preparedStatement.setInt(1,contenuTopoSite.getIdTopo());
			    preparedStatement.setInt(2,contenuTopoSite.getIdSite());
			    preparedStatement.executeUpdate();
		    }
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
	//
	public ArrayList<Integer> trouverTopoConcerneParSite(int idSite) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Integer>idDesTopos=new ArrayList<Integer>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM toposites WHERE idsite="+idSite+";");
	         while(resultat.next()) {
	        	 idDesTopos.add(resultat.getInt(2));
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
		return idDesTopos;
	}
	//
	//
	
}
