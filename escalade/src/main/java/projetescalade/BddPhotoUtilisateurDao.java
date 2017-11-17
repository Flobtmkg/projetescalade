package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javabeans.PhotoTopo;
import javabeans.PhotoUtilisateur;

public class BddPhotoUtilisateurDao implements PhotoUtilisateurDao{
	//
	private ConnexionDao connexionEnCours;
	//
	public BddPhotoUtilisateurDao(ConnexionDao connexionDao) {
		//
		this.connexionEnCours=connexionDao;
		//
	}

	public String ajoutPhoto(int idUtilisateur, String photoPath) {
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        try {
            connexion = connexionEnCours.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO photoutilisateur(idutilisateur, pathphoto) VALUES(?, ?);");
            preparedStatement.setInt(1, idUtilisateur);
            preparedStatement.setString(2, CodageGuillemets.getTexteEncode(photoPath));
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

	public PhotoUtilisateur getPhotoPath(int idUtilisateur) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        PhotoUtilisateur lienPhotoUtilisateur = new PhotoUtilisateur();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM photoutilisateur WHERE idutilisateur="+idUtilisateur+";");
	         while(resultat.next()) {
	        	 lienPhotoUtilisateur.setIdPhotoUtilisateur(resultat.getInt(1));
	        	 lienPhotoUtilisateur.setIdUtilisateur(resultat.getInt(2));
	        	 lienPhotoUtilisateur.setPathPhoto(CodageGuillemets.getTexteDecode(resultat.getString(3)));
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
		return lienPhotoUtilisateur;
	}

}
