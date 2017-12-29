package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.PhotoVoieDao;
import javabeans.PhotoVoie;

public class BddPhotoVoieDao implements PhotoVoieDao{
	//
	ConnexionDao connexionEnCours;
	//
	//
	public BddPhotoVoieDao(ConnexionDao connexionDao) {
		//
		connexionEnCours=connexionDao;
	}

	public String ajoutPhoto(int idVoie, String photoPath) {
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        try {
            connexion = connexionEnCours.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO photovoie(idvoie, pathphoto) VALUES(?, ?);");
            preparedStatement.setInt(1, idVoie);
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

	public PhotoVoie getPhotoPath(int idVoie) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        PhotoVoie lienPhotoVoie = new PhotoVoie();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM photovoie WHERE idvoie="+idVoie+";");
	         while(resultat.next()) {
	        	 lienPhotoVoie.setIdPhotoVoie(resultat.getInt("idphoto"));
	        	 lienPhotoVoie.setIdVoie(resultat.getInt("idvoie"));
	        	 lienPhotoVoie.setPathPhoto(CodageGuillemets.getTexteDecode(resultat.getString("pathphoto")));
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
		return lienPhotoVoie;
	}

}
