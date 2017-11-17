package projetescalade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javabeans.Topo;
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
	        LocalDate utildate=null;
			try {
				utildate=LocalDate.parse(utilisateurinput.getDateNaissance());
			} catch (Exception e1) {
				e1.printStackTrace();
				erreurEventuelle=erreurEventuelle + e1.getMessage() + ";";
				return erreurEventuelle;
			}
			long millisecondsSince1970 =utildate.toEpochDay()*86400000;
			java.sql.Date sqlDate=new java.sql.Date(millisecondsSince1970);
	        //
	        //chiffrage du mdp
	        String mdpChiffre=BCrypt.hashpw(utilisateurinput.getMotDePasse(), BCrypt.gensalt());
	        //
	        //
	        try {
	            connexion = connexionEnCours.getConnection();
	            preparedStatement = connexion.prepareStatement("INSERT INTO utilisateur(nomutilisateur, prenomutilisateur, emailutilisateur, datenaissanceutilisateur, paysutilisateur, villeutilisateur, mdputilisateur,descriptionutilisateur) VALUES(?, ?, ?, ?, ?, ?, ?,?);");
	            preparedStatement.setString(1, CodageGuillemets.getTexteEncode(utilisateurinput.getNom()));
	            preparedStatement.setString(2, CodageGuillemets.getTexteEncode(utilisateurinput.getPrenom()));
	            preparedStatement.setString(3, CodageGuillemets.getTexteEncode(utilisateurinput.getEmail()));
	            preparedStatement.setDate(4,sqlDate);
	            preparedStatement.setString(5, CodageGuillemets.getTexteEncode(utilisateurinput.getPays()));
	            preparedStatement.setString(6, CodageGuillemets.getTexteEncode(utilisateurinput.getVille()));
	            preparedStatement.setString(7, mdpChiffre);
	            preparedStatement.setString(8, CodageGuillemets.getTexteEncode(utilisateurinput.getDescription()));
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
	public String modifierCol(String colonne,String enregValue,String whereId,int whereValue,boolean isDate,boolean isMdp) {
		// TODO Auto-generated method stub
			String erreurEventuelle="";
	        Connection connexion = null;
	        PreparedStatement preparedStatement = null;
	        //conversion de dates
	        //Definition des variables pour les cas particuliers
	        LocalDate utildate=null;
	        java.sql.Date sqlDate=null;
	        String mdpChiffre=null;
	        if(isDate==true) {
	        	try {
					utildate=LocalDate.parse(enregValue);
				} catch (Exception e1) {
					e1.printStackTrace();
					erreurEventuelle=erreurEventuelle + e1.getMessage() + ";";
					return erreurEventuelle;
				}
				long millisecondsSince1970 =utildate.toEpochDay()*86400000;
				sqlDate=new java.sql.Date(millisecondsSince1970);
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
	            	preparedStatement.setString(1, CodageGuillemets.getTexteEncode(enregValue));
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
	public String modifier(Utilisateur utilisateurInput) {
		// TODO Auto-generated method stub
					int idUtilisateurInput=utilisateurInput.getId();
					String erreurEventuelle="";
			        Connection connexion = null;
			        PreparedStatement preparedStatement = null;
			        //
			        //conversion de dates
			        LocalDate utildate=null;
					try {
						utildate=LocalDate.parse(utilisateurInput.getDateNaissance());
					} catch (Exception e1) {
						e1.printStackTrace();
						erreurEventuelle=erreurEventuelle + e1.getMessage() + ";";
						return erreurEventuelle;
					}
					long millisecondsSince1970 =utildate.toEpochDay()*86400000;
					java.sql.Date sqlDate=new java.sql.Date(millisecondsSince1970);
			        //
			        //
	        try {
	            connexion = connexionEnCours.getConnection();
	            preparedStatement=connexion.prepareStatement("UPDATE utilisateur SET nomutilisateur=?, prenomutilisateur=?, emailutilisateur=?, datenaissanceutilisateur=?, paysutilisateur=?, villeutilisateur=?,descriptionutilisateur=?  WHERE idutilisateur = ?;");
	            //
	            preparedStatement.setString(1, CodageGuillemets.getTexteEncode(utilisateurInput.getNom()));
	            preparedStatement.setString(2, CodageGuillemets.getTexteEncode(utilisateurInput.getPrenom()));
	            preparedStatement.setString(3, CodageGuillemets.getTexteEncode(utilisateurInput.getEmail()));
	            preparedStatement.setDate(4,sqlDate);
	            preparedStatement.setString(5, CodageGuillemets.getTexteEncode(utilisateurInput.getPays()));
	            preparedStatement.setString(6, CodageGuillemets.getTexteEncode(utilisateurInput.getVille()));
	            preparedStatement.setString(7, CodageGuillemets.getTexteEncode(utilisateurInput.getDescription()));
	            preparedStatement.setInt(8, idUtilisateurInput);
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
				Utilisateur utilisateurOutput= new Utilisateur(0,"","","","","","","","");
		        Connection connexion = null;
		        Statement statement = null;
		        ResultSet resultat=null;
		        emailinput=CodageGuillemets.getTexteEncode(emailinput);
		        try {
		            connexion = connexionEnCours.getConnection();
		            statement=connexion.createStatement();
		            resultat=statement.executeQuery("SELECT * FROM utilisateur WHERE emailutilisateur='"+emailinput+"'");
		            Date conversiondedate = null;
		            //
		            	while(resultat.next()) {
		            	utilisateurOutput.setId(resultat.getInt("idutilisateur"));
			            utilisateurOutput.setNom(CodageGuillemets.getTexteDecode(resultat.getString("nomutilisateur")));
			            utilisateurOutput.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString("prenomutilisateur")));
			            utilisateurOutput.setEmail(CodageGuillemets.getTexteDecode(resultat.getString("emailutilisateur")));
			            conversiondedate=resultat.getDate("datenaissanceutilisateur");
			            utilisateurOutput.setPays(CodageGuillemets.getTexteDecode(resultat.getString("paysutilisateur")));
			            utilisateurOutput.setVille(CodageGuillemets.getTexteDecode(resultat.getString("villeutilisateur")));
			            utilisateurOutput.setMotDePasse(resultat.getString("mdputilisateur"));
			            utilisateurOutput.setDescription(CodageGuillemets.getTexteDecode(resultat.getString("descriptionutilisateur")));
		            	}
		            //Conversion de date
		            	if(conversiondedate!=null) {
		            		utilisateurOutput.setDateNaissance(conversiondedate.toString());
		            	}
		            //Verification mdp
		            	if(utilisateurOutput.getMotDePasse()!=null && utilisateurOutput.getMotDePasse().equals("")==false) {
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


	public Utilisateur trouver(int idutilisateurinput) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Utilisateur utilisateurOutput=new Utilisateur(0,"","","","","","","","");
        Date conversiondedate = null;
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM utilisateur WHERE idutilisateur='"+idutilisateurinput+"'");
	         while(resultat.next()) {
	        	 utilisateurOutput.setId(resultat.getInt(1));
	        	 utilisateurOutput.setNom(CodageGuillemets.getTexteDecode(resultat.getString(2)));
	        	 utilisateurOutput.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 utilisateurOutput.setEmail(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 conversiondedate=resultat.getDate(5);
	        	 utilisateurOutput.setPays(CodageGuillemets.getTexteDecode(resultat.getString(6)));
	        	 utilisateurOutput.setVille(CodageGuillemets.getTexteDecode(resultat.getString(7)));
	        	 utilisateurOutput.setMotDePasse(resultat.getString(8));
	        	 utilisateurOutput.setDescription(CodageGuillemets.getTexteDecode(resultat.getString(9)));
	         }
	       //Conversion de date
         	if(conversiondedate!=null) {
         		utilisateurOutput.setDateNaissance(conversiondedate.toString());
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
		return utilisateurOutput;
	}
	//
	//
	public ArrayList<Utilisateur> trouverProprietaireTopo(ArrayList<Topo> topoListInput) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Utilisateur> utilisateursOutput=new ArrayList<Utilisateur>();
        Date conversiondedate = null;
        String clauseWhere="";
        if(topoListInput.isEmpty()) {
        	return utilisateursOutput;
        }else {
        	 for(Topo top:topoListInput) {
             	clauseWhere=clauseWhere+"idutilisateur="+top.getIdProprietaire()+" OR ";
             }
             clauseWhere=clauseWhere.substring(0, clauseWhere.length()-4);//on enlève le dernier " OR "
        }
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM utilisateur WHERE "+clauseWhere+";");
	         while(resultat.next()) {
	        	 Utilisateur chaqueUtilisateur=new Utilisateur(0,"","","","","","","","");
	        	 chaqueUtilisateur.setId(resultat.getInt(1));
	        	 chaqueUtilisateur.setNom(CodageGuillemets.getTexteDecode(resultat.getString(2)));
	        	 chaqueUtilisateur.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueUtilisateur.setEmail(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 conversiondedate=resultat.getDate(5);
	        	 if(conversiondedate!=null) {
	        		 chaqueUtilisateur.setDateNaissance(conversiondedate.toString());
	        	 }
	        	 chaqueUtilisateur.setPays(CodageGuillemets.getTexteDecode(resultat.getString(6)));
	        	 chaqueUtilisateur.setVille(CodageGuillemets.getTexteDecode(resultat.getString(7)));
	        	 chaqueUtilisateur.setMotDePasse(resultat.getString(8));
	        	 chaqueUtilisateur.setDescription(CodageGuillemets.getTexteDecode(resultat.getString(9)));
	        	 utilisateursOutput.add(chaqueUtilisateur);
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
		return utilisateursOutput;
	}

}
