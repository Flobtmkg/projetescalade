package projetescalade;

import java.sql.Connection;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

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

	
	public String ajouter(Utilisateur utilisateurinput) {
		// TODO Auto-generated method stub
			String erreurEventuelle="";
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        //
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
	        //chiffrage du mdp
	        String mdpChiffre=BCrypt.hashpw(utilisateurinput.getMotDePasse(), BCrypt.gensalt());
	        //
	        //
	        try {
	            connexion = connexionEnCours.getConnection();
	            preparedStatement = connexion.prepareStatement("INSERT INTO utilisateur(nomutilisateur, prenomutilisateur, emailutilisateur, datenaissanceutilisateur, paysutilisateur, villeutilisateur, mdputilisateur,descriptionutilisateur) VALUES(?, ?, ?, ?, ?, ?, ?,?);");
	            preparedStatement.setString(1, utilisateurinput.getNom());
	            preparedStatement.setString(2, utilisateurinput.getPrenom());
	            preparedStatement.setString(3, utilisateurinput.getEmail());
	            preparedStatement.setDate(4,sqlDate);
	            preparedStatement.setString(5, utilisateurinput.getPays());
	            preparedStatement.setString(6, utilisateurinput.getVille());
	            preparedStatement.setString(7, mdpChiffre);
	            preparedStatement.setString(8, utilisateurinput.getDescription());
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
	public String modifier(String colonne,String enregValue,String whereId,int whereValue,boolean isDate,boolean isMdp) {
		// TODO Auto-generated method stub
			String erreurEventuelle="";
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        //conversion de dates
	        SimpleDateFormat conversiondedate = new SimpleDateFormat("yyyy-MM-dd");
	        //Definition des variables pour les cas particuliers
	        java.util.Date utilDate = null;
	        java.sql.Date sqlDate=null;
	        String mdpChiffre=null;
	        if(isDate==true) {
	        	try {
					utilDate = conversiondedate.parse(enregValue);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
		        sqlDate=new java.sql.Date(utilDate.getTime());
	        }else if(isMdp==true) {
		        //chiffrage du mdp
		        mdpChiffre=BCrypt.hashpw(enregValue, BCrypt.gensalt());
		        //
	        }
	        try {
	            connexion = connexionEnCours.getConnection();
	            preparedStatement=connexion.prepareStatement("UPDATE utilisateur SET "+colonne+" = ? WHERE "+whereId+" = ?;");
	            //
	            if(isDate==false && isMdp==false) {
	            	preparedStatement.setString(1, enregValue);
		            preparedStatement.setInt(2, whereValue);
	            }else if(isDate==true && isMdp==false) {
	            	preparedStatement.setDate(1, sqlDate);
		            preparedStatement.setInt(2, whereValue);
	            }else if(isDate==false && isMdp==true) {
	            	preparedStatement.setString(1, mdpChiffre);
		            preparedStatement.setInt(2, whereValue);
	            }
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
	public Utilisateur verifier(String emailinput, String mdpinput) {
		//
				Utilisateur utilisateurOutput= new Utilisateur();
		        Connection connexion = null;
		        Statement statement = null;
		        ResultSet resultat=null;
		        try {
		            connexion = connexionEnCours.getConnection();
		            statement=connexion.createStatement();
		            resultat=statement.executeQuery("SELECT * FROM utilisateur WHERE emailutilisateur='"+emailinput+"'");
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
			            utilisateurOutput.setDescription(resultat.getString("descriptionutilisateur"));
		            	}
		            //Conversion de date
		            	if(conversiondedate!=null) {
		            		utilisateurOutput.setDateNaissance(conversiondedate.toString());
		            	}
		            //Verification mdp
		            	if(utilisateurOutput.getMotDePasse()!=null) {
		            		if(BCrypt.checkpw(mdpinput, utilisateurOutput.getMotDePasse())==false) {
			            		utilisateurOutput.setId(0);
			            		utilisateurOutput.setNom("");
			            		utilisateurOutput.setPrenom("");
			            		utilisateurOutput.setEmail("");
			            		utilisateurOutput.setPays("");
			            		utilisateurOutput.setVille("");
			            		utilisateurOutput.setMotDePasse("");
			            		utilisateurOutput.setDescription("");
			            	}
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
