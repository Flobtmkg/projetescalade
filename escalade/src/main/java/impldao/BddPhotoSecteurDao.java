package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.PhotoSecteurDao;
import javabeans.PhotoSecteur;

public class BddPhotoSecteurDao implements PhotoSecteurDao{
	//
	ConnexionDao connexionEnCours;
	//
	//
	public BddPhotoSecteurDao(ConnexionDao connexionDao) {
		//
		this.connexionEnCours=connexionDao;
	}
	public String ajoutPhoto(int idSecteur, String photoPath) {
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        try {
            connexion = connexionEnCours.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO photosecteur(idsecteur, pathphoto) VALUES(?, ?);");
            preparedStatement.setInt(1, idSecteur);
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

	public PhotoSecteur getPhotoPath(int idSecteur) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        PhotoSecteur lienPhotoSecteur = new PhotoSecteur();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM photosecteur WHERE idsecteur="+idSecteur+";");
	         while(resultat.next()) {
	        	 lienPhotoSecteur.setIdPhotoSecteur(resultat.getInt("idphoto"));
	        	 lienPhotoSecteur.setIdSecteur(resultat.getInt("idsecteur"));
	        	 lienPhotoSecteur.setPathPhoto(CodageGuillemets.getTexteDecode(resultat.getString("pathphoto")));
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
		return lienPhotoSecteur;
	}
}
