package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javabeans.Secteur;
import javabeans.Site;

public class BddSecteurDao implements SecteurDao{
	private ConnexionDao connexionEnCours;

	BddSecteurDao(ConnexionDao connexionDao) {
		//
		this.connexionEnCours=connexionDao;
		//
	}

	public ArrayList<Secteur> trouver(int idSiteInput) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Secteur> secteursDuSite = new ArrayList<Secteur>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM secteur WHERE idsite="+idSiteInput+";");
	         while(resultat.next()) {
	        	 Secteur chaqueSecteur=new Secteur();
	        	 chaqueSecteur.setIdSecteur(resultat.getInt(1));
	        	 chaqueSecteur.setIdSite(resultat.getInt(2));
	        	 chaqueSecteur.setNomSecteur(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueSecteur.setDescriptionSecteur(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueSecteur.setHauteurSecteur(resultat.getDouble(5));
	        	 secteursDuSite.add(chaqueSecteur);
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
		return secteursDuSite;
	}
	//
	//
	public ArrayList<Secteur> toutLesSecteurs() {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Secteur> secteurs = new ArrayList<Secteur>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM secteur;");
	         while(resultat.next()) {
	        	 Secteur chaqueSecteur=new Secteur();
	        	 chaqueSecteur.setIdSecteur(resultat.getInt(1));
	        	 chaqueSecteur.setIdSite(resultat.getInt(2));
	        	 chaqueSecteur.setNomSecteur(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueSecteur.setDescriptionSecteur(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueSecteur.setHauteurSecteur(resultat.getDouble(5));
	        	 secteurs.add(chaqueSecteur);
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
		return secteurs;
	}
	//
	//
	public Secteur trouverSecteur(int idSecteurInput) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Secteur secteurTrouve = new Secteur();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM secteur WHERE idsecteur="+idSecteurInput+";");
	         while(resultat.next()) {
	        	 secteurTrouve.setIdSecteur(resultat.getInt(1));
	        	 secteurTrouve.setIdSite(resultat.getInt(2));
	        	 secteurTrouve.setNomSecteur(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 secteurTrouve.setDescriptionSecteur(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 secteurTrouve.setHauteurSecteur(resultat.getDouble(5));
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
		return secteurTrouve;
	}
	//
	//
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
	         resultat=statement.executeQuery("SELECT MAX(idsecteur) FROM secteur;");
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
	//
	//
	public String ajouter(Secteur inputSecteur) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement = connexion.prepareStatement("INSERT INTO secteur(idsite,nomsecteur,descriptionsecteur,hauteursecteur) VALUES(?,?,?,?);");
		    preparedStatement.setInt(1, inputSecteur.getIdSite());
		    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(inputSecteur.getNomSecteur()));
		    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(inputSecteur.getDescriptionSecteur()));
		    preparedStatement.setDouble(4, inputSecteur.getHauteurSecteur());
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
