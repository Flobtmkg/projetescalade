package projetescalade;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javabeans.Utilisateur;

public class BddUtilisateurDao implements UtilisateurDao{
	//
	private ConnexionDao connexionEnCours;
	//
	BddUtilisateurDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}

	
	public void ajouter(Utilisateur utilisateurinput) {
		// TODO Auto-generated method stub
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        //conversion de dates
	        SimpleDateFormat conversiondedate = new SimpleDateFormat("yyyy-MM-dd");
	        java.util.Date utilDate = null;
			try {
				utilDate = conversiondedate.parse(utilisateurinput.getDateNaissance());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
	        java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
	        //
	        try {
	            connexion = connexionEnCours.getConnection();
	            preparedStatement = connexion.prepareStatement("INSERT INTO utilisateur(nomutilisateur, prenomutilisateur, emailutilisateur, datenaissanceutilisateur, paysutilisateur, villeutilisateur, mdputilisateur) VALUES(?, ?, ?, ?, ?, ?, ?);");
	            preparedStatement.setString(1, utilisateurinput.getNom());
	            preparedStatement.setString(2, utilisateurinput.getPrenom());
	            preparedStatement.setString(3, utilisateurinput.getEmail());
	            preparedStatement.setDate(4,sqlDate);
	            preparedStatement.setString(5, utilisateurinput.getPays());
	            preparedStatement.setString(6, utilisateurinput.getVille());
	            preparedStatement.setString(7, utilisateurinput.getMotDePasse());
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {
	        	try {
					connexion.close();
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	}


	public Utilisateur verifier(String emailinput, String mdpinput) {
		//
				//
				Utilisateur utilisateurOutput= new Utilisateur();
		        Connection connexion = null;
		        Statement statement = null;
		        ResultSet resultat=null;
		        try {
		            connexion = connexionEnCours.getConnection();
		            statement=connexion.createStatement();
		            resultat=statement.executeQuery("SELECT * FROM utilisateur WHERE emailutilisateur='"+emailinput+"' AND mdputilisateur= '"+mdpinput+"'");
		            Date conversiondedate = null;
		            //
		            	while(resultat.next()) {
		            	utilisateurOutput.setId(resultat.getInt("idutilisateur"));
			            utilisateurOutput.setNom(resultat.getString("nomutilisateur"));
			            utilisateurOutput.setPrenom(resultat.getString("prenomutilisateur"));
			            utilisateurOutput.setEmail(resultat.getString("emailutilisateur"));
			            conversiondedate=resultat.getDate("datenaissanceutilisateur");
			            utilisateurOutput.setPays(resultat.getString("paysutilisateur"));
			            utilisateurOutput.setVille(resultat.getString("villeutilisateur"));
			            utilisateurOutput.setMotDePasse(resultat.getString("mdputilisateur"));
		            	}
		            //Conversion de date
		            	if(conversiondedate!=null) {
		            		utilisateurOutput.setDateNaissance(conversiondedate.toString());
		            	}
		            //

		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		        	try {
						connexion.close();
						statement.close();
			        	resultat.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		        }
				return utilisateurOutput;
	}
	

}
