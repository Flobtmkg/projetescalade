package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javabeans.PhotoSite;
import javabeans.PhotoTopo;

public class BddPhotoSiteDao implements PhotoSiteDao{
	//
	ConnexionDao connexionEnCours;
	//

	BddPhotoSiteDao(ConnexionDao connexionDao) {
		//
		this.connexionEnCours=connexionDao;
	}

	public String ajoutPhoto(int idSite, String photoPath) {
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        try {
            connexion = connexionEnCours.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO photosite(idsite, pathphoto) VALUES(?, ?);");
            preparedStatement.setInt(1, idSite);
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

	public PhotoSite getPhotoPath(int idSite) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        PhotoSite lienPhotoSite = new PhotoSite();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM photosite WHERE idsite="+idSite+";");
	         while(resultat.next()) {
	        	 lienPhotoSite.setIdPhotoSite(resultat.getInt(1));
	        	 lienPhotoSite.setIdSite(resultat.getInt(2));
	        	 lienPhotoSite.setPathPhoto(CodageGuillemets.getTexteDecode(resultat.getString(3)));
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
		return lienPhotoSite;
	}

}
