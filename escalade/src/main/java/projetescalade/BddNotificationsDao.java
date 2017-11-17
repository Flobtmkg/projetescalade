package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import javabeans.Notification;
import javabeans.Topo;

public class BddNotificationsDao implements NotificationDao{
ConnexionDao connexionEnCours;

	BddNotificationsDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}

	public ArrayList<Notification> trouverNotificationsParUtilisateur(int idutilisateur) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Notification>notificationsUtilisateur=new ArrayList<Notification>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM notifications WHERE idutilisateurdestinataire='"+idutilisateur+"'");
	         while(resultat.next()) {
	        	 Notification chaqueNotification =new Notification();
	        	 chaqueNotification.setIdNotification(resultat.getInt(1));
	        	 chaqueNotification.setIdUtilisateurDestinataire(resultat.getInt(2));
	        	 chaqueNotification.setIdUtilisateurExpediteur(resultat.getInt(3));
	        	 chaqueNotification.setTypeNotification(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueNotification.setTraitementNotification(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 chaqueNotification.setParametreNotification(CodageGuillemets.getTexteDecode(resultat.getString(6)));
	        	 chaqueNotification.setIdTopo(resultat.getInt(7));
	        	 notificationsUtilisateur.add(chaqueNotification);
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
		return notificationsUtilisateur;
	}
		//

	public String ajouter(Notification notificationInput) {
		//
					String erreurEventuelle="";
			        Connection connexion = null;
			        PreparedStatement preparedStatement = null;
			        //
			        try {
			            connexion = connexionEnCours.getConnection();
			            preparedStatement = connexion.prepareStatement("INSERT INTO notifications(idutilisateurdestinataire, idutilisateurexpediteur, typedenotification, parametrenotification, idtopo) VALUES(?, ?, ?, ?, ?);");
			            preparedStatement.setInt(1, notificationInput.getIdUtilisateurDestinataire());
			            preparedStatement.setInt(2, notificationInput.getIdUtilisateurExpediteur());
			            preparedStatement.setString(3, CodageGuillemets.getTexteEncode(notificationInput.getTypeNotification()));
			            preparedStatement.setString(4, CodageGuillemets.getTexteEncode(notificationInput.getParametreNotification()));
			            preparedStatement.setInt(5, notificationInput.getIdTopo());
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

	public String definirTraitement(int idNotification, boolean traitement) {
		//
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        //
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement=connexion.prepareStatement("UPDATE notifications SET traitementnotification=? WHERE idnotification = ?;");
		    //
		    preparedStatement.setString(1, String.valueOf(traitement));
		    preparedStatement.setInt(2, idNotification);
		   //
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

	///
	///
}
