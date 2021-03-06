package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.NotificationDao;
import javabeans.Notification;
import javabeans.Utilisateur;

public class BddNotificationsDao implements NotificationDao{
ConnexionDao connexionEnCours;

	public BddNotificationsDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}

	public ArrayList<Notification> trouverNotificationsParUtilisateur(int idutilisateur) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Notification>notificationsUtilisateur=new ArrayList<Notification>();
        Date conversionDate=new Date();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM notifications FULL JOIN utilisateur ON notifications.idutilisateurexpediteur=utilisateur.idutilisateur WHERE idutilisateurdestinataire="+idutilisateur+";");
	         while(resultat.next()) {
	        	 Notification chaqueNotification =new Notification();
	        	 Utilisateur expediteur=new Utilisateur(0,"","","","","","","","");
	        	 chaqueNotification.setIdNotification(resultat.getInt("idnotification"));
	        	 chaqueNotification.setIdUtilisateurDestinataire(resultat.getInt("idutilisateurdestinataire"));
	        	 chaqueNotification.setIdUtilisateurExpediteur(resultat.getInt("idutilisateurexpediteur"));
	        	 chaqueNotification.setTypeNotification(CodageGuillemets.getTexteDecode(resultat.getString("typedenotification")));
	        	 chaqueNotification.setTraitementNotification(CodageGuillemets.getTexteDecode(resultat.getString("traitementnotification")));
	        	 chaqueNotification.setParametreNotification(CodageGuillemets.getTexteDecode(resultat.getString("parametrenotification")));
	        	 chaqueNotification.setIdTopo(resultat.getInt("idtopo"));
	        	 //
	        	 expediteur.setId(resultat.getInt("idutilisateur"));
	        	 expediteur.setNom(CodageGuillemets.getTexteDecode(resultat.getString("nomutilisateur")));
	        	 expediteur.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString("prenomutilisateur")));
	        	 expediteur.setEmail(CodageGuillemets.getTexteDecode(resultat.getString("emailutilisateur")));
	        	 conversionDate=resultat.getDate("datenaissanceutilisateur");
	        	 if(conversionDate!=null) {
	        		 expediteur.setDateNaissance(conversionDate.toString());
	        	 }
	        	 expediteur.setPays(CodageGuillemets.getTexteDecode(resultat.getString("paysutilisateur")));
	        	 expediteur.setVille(CodageGuillemets.getTexteDecode(resultat.getString("villeutilisateur")));
	        	 expediteur.setDescription(CodageGuillemets.getTexteDecode(resultat.getString("descriptionutilisateur")));
	        	 //
	        	 chaqueNotification.setUtilisateurExpediteur(expediteur);
	        	 //
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
