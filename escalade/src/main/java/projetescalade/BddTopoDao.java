package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javabeans.Site;
import javabeans.Topo;

public class BddTopoDao implements TopoDao {
	//
	private ConnexionDao connexionEnCours;
	//
	BddTopoDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}
	public ArrayList<Topo> trouverToposParUtilisateur(int idproprietaire) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Topo>toposPossede=new ArrayList<Topo>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE idproprietaire='"+idproprietaire+"'");
	         while(resultat.next()) {
	        	 Topo chaqueTopo =new Topo();
	        	 chaqueTopo.setIdTopo(resultat.getInt(1));
	        	 chaqueTopo.setIdProprietaire(resultat.getInt(2));
	        	 chaqueTopo.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueTopo.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 toposPossede.add(chaqueTopo);
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
		return toposPossede;
	}
	
	public Topo trouverTopo(int idtopo) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Topo topoOutput=new Topo();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE idtopo='"+idtopo+"'");
	         while(resultat.next()) {
	        	 topoOutput.setIdTopo(resultat.getInt(1));
	        	 topoOutput.setIdProprietaire(resultat.getInt(2));
	        	 topoOutput.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 topoOutput.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString(4)));
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
		return topoOutput;
	}
	//
	//
	public ArrayList<Topo> trouverTopo(ArrayList<Integer> idTopos) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Topo> toposOutput=new ArrayList<Topo>();
        String clauseWhere="";
        if(idTopos.isEmpty()) {
        	return toposOutput;
        }else if(idTopos.get(0)==0){
        	clauseWhere="1=1";
        }else {
        	 for(int id:idTopos) {
             	clauseWhere=clauseWhere+"idtopo="+id+" OR ";
             }
             clauseWhere=clauseWhere.substring(0, clauseWhere.length()-4);//on enlève le dernier " OR "
        }
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE "+clauseWhere+";");
	         while(resultat.next()) {
	        	 Topo chaqueTopo=new Topo();
	        	 chaqueTopo.setIdTopo(resultat.getInt(1));
	        	 chaqueTopo.setIdProprietaire(resultat.getInt(2));
	        	 chaqueTopo.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueTopo.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 toposOutput.add(chaqueTopo);
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
		return toposOutput;
	}
	//
	//
	public String ajoutTopo(Topo topoAAjouter) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement = connexion.prepareStatement("INSERT INTO topo(idproprietaire,nomtopo,descriptiontopo) VALUES(?,?,?);");
		    preparedStatement.setInt(1, topoAAjouter.getIdProprietaire());
		    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(topoAAjouter.getNomTopo()));
		    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(topoAAjouter.getDescriptionTopo()));
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
	//
	//
	public Topo trouverTopoSansIdTopo(int idproprietaire, String titreTopo, String descriptionTopo) {
		//
		titreTopo=CodageGuillemets.getTexteEncode(titreTopo);
		descriptionTopo=CodageGuillemets.getTexteEncode(descriptionTopo);
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Topo topoOutput=new Topo();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE idproprietaire="+idproprietaire+" AND nomtopo='"+titreTopo+"' AND descriptiontopo='"+descriptionTopo+"'");
	         while(resultat.next()) {
	        	 topoOutput.setIdTopo(resultat.getInt(1));
	        	 topoOutput.setIdProprietaire(resultat.getInt(2));
	        	 topoOutput.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 topoOutput.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString(4)));
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
		return topoOutput;
	}
	//
	//
}
