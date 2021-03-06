package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.PhotoTopoDao;
import javabeans.PhotoTopo;

public class BddPhotoTopoDao implements PhotoTopoDao{
	//
	ConnexionDao connexionEnCours;
	//
	public BddPhotoTopoDao(ConnexionDao connexionInput){
		connexionEnCours=connexionInput;
	}
	
	public String ajoutPhoto(int idTopo, String photoPath) {
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        try {
            connexion = connexionEnCours.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO phototopo(idtopo, pathphoto) VALUES(?, ?);");
            preparedStatement.setInt(1, idTopo);
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

	public PhotoTopo getPhotoPath(int idTopo) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        PhotoTopo lienPhotoTopo = new PhotoTopo();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM phototopo WHERE idtopo="+idTopo+";");
	         while(resultat.next()) {
	        	 lienPhotoTopo.setIdPhotoTopo(resultat.getInt("idphoto"));
	        	 lienPhotoTopo.setIdTopo(resultat.getInt("idtopo"));
	        	 lienPhotoTopo.setPathPhoto(CodageGuillemets.getTexteDecode(resultat.getString("pathphoto")));
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
		return lienPhotoTopo;
	}

}
