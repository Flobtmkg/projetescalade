package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javabeans.Commentaire;

public class BddCommentairesDao implements CommentaireDao {
	//
	private ConnexionDao connexionEnCours;
	//
	BddCommentairesDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}
	//
	public ArrayList<Commentaire> trouverCommentairesParUtilisateur(int idutilisateur) {//tout les commentaires
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedate = new Date();
        ArrayList<Commentaire>commentairesenvoye=new ArrayList<Commentaire>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM commentaires WHERE idutilisateur="+idutilisateur+";");
	         while(resultat.next()) {
	        	 Commentaire chaqueCommentaire =new Commentaire();
	        	 chaqueCommentaire.setIdCommentaire(resultat.getInt(1));
	        	 chaqueCommentaire.setIdUtilisateur(resultat.getInt(2));
	        	 chaqueCommentaire.setIdSite(resultat.getInt(3));
	        	 chaqueCommentaire.setContenuCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueCommentaire.setIpCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 conversiondedate=resultat.getDate(6);
	        	 chaqueCommentaire.setDateCommentaire(conversiondedate.toString());
	        	 commentairesenvoye.add(chaqueCommentaire);
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
		return commentairesenvoye;
	}
	//
	//
	public ArrayList<Commentaire> trouverCommentairesParSite(int idSite) {//tout les commentaires
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedate = new Date();
        ArrayList<Commentaire>commentairesenvoye=new ArrayList<Commentaire>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM commentaires WHERE idsite="+idSite+";");
	         while(resultat.next()) {
	        	 Commentaire chaqueCommentaire =new Commentaire();
	        	 chaqueCommentaire.setIdCommentaire(resultat.getInt(1));
	        	 chaqueCommentaire.setIdUtilisateur(resultat.getInt(2));
	        	 chaqueCommentaire.setIdSite(resultat.getInt(3));
	        	 chaqueCommentaire.setContenuCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueCommentaire.setIpCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 conversiondedate=resultat.getDate(6);
	        	 chaqueCommentaire.setDateCommentaire(conversiondedate.toString());
	        	 chaqueCommentaire.setIdSecteur(resultat.getInt(7));
	        	 chaqueCommentaire.setIdVoie(resultat.getInt(8));
	        	 commentairesenvoye.add(chaqueCommentaire);
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
		return commentairesenvoye;
	}
	//
	//
	public ArrayList<Commentaire> trouverCommentairesParSecteur(int idSecteur) {//commentaires Secteur
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedate = new Date();
        ArrayList<Commentaire>commentairesenvoye=new ArrayList<Commentaire>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM commentaires WHERE idsecteur="+idSecteur+"");
	         while(resultat.next()) {
	        	 Commentaire chaqueCommentaire =new Commentaire();
	        	 chaqueCommentaire.setIdCommentaire(resultat.getInt(1));
	        	 chaqueCommentaire.setIdUtilisateur(resultat.getInt(2));
	        	 chaqueCommentaire.setIdSite(resultat.getInt(3));
	        	 chaqueCommentaire.setContenuCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueCommentaire.setIpCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 conversiondedate=resultat.getDate(6);
	        	 chaqueCommentaire.setDateCommentaire(conversiondedate.toString());
	        	 chaqueCommentaire.setIdSecteur(resultat.getInt(7));
	        	 chaqueCommentaire.setIdVoie(resultat.getInt(8));
	        	 commentairesenvoye.add(chaqueCommentaire);
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
		return commentairesenvoye;
	}
	//
	//
	//
	//
	public ArrayList<Commentaire> trouverCommentairesParVoie(int idVoie) {//commentaires Voie
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedate = new Date();
        ArrayList<Commentaire>commentairesenvoye=new ArrayList<Commentaire>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM commentaires WHERE idvoie="+idVoie+"");
	         while(resultat.next()) {
	        	 Commentaire chaqueCommentaire =new Commentaire();
	        	 chaqueCommentaire.setIdCommentaire(resultat.getInt(1));
	        	 chaqueCommentaire.setIdUtilisateur(resultat.getInt(2));
	        	 chaqueCommentaire.setIdSite(resultat.getInt(3));
	        	 chaqueCommentaire.setContenuCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(4)));
	        	 chaqueCommentaire.setIpCommentaire(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 conversiondedate=resultat.getDate(6);
	        	 chaqueCommentaire.setDateCommentaire(conversiondedate.toString());
	        	 chaqueCommentaire.setIdSecteur(resultat.getInt(7));
	        	 chaqueCommentaire.setIdVoie(resultat.getInt(8));
	        	 commentairesenvoye.add(chaqueCommentaire);
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
		return commentairesenvoye;
	}
	//
	//
	public String ajouterCommentaire(Commentaire commentaireInput, int idSecteur, int idVoie) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		LocalDate utildate1=null;
		try {
			utildate1=LocalDate.parse(commentaireInput.getDateCommentaire());
		} catch (Exception e1) {
			e1.printStackTrace();
			erreurEventuelle=erreurEventuelle + e1.getMessage() + ";";
			return erreurEventuelle;
		}
		long millisecondsSince1970A =utildate1.toEpochDay()*86400000;
		java.sql.Date sqlDate1=new java.sql.Date(millisecondsSince1970A);
			//
		try {
			if(idSecteur==0 && idVoie==0) {//Commenté Sur Site
				//
					if(commentaireInput.getIdUtilisateur()==0) {//anonyme
						connexion = connexionEnCours.getConnection();
					    preparedStatement = connexion.prepareStatement("INSERT INTO commentaires(idsite,contenucommentaire,ipcommentaire,datecommentaire) VALUES(?,?,?,?);");
					    preparedStatement.setInt(1,commentaireInput.getIdSite());
					    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(commentaireInput.getContenuCommentaire()));
					    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(commentaireInput.getIpCommentaire()));
					    preparedStatement.setDate(4, sqlDate1);
					    preparedStatement.executeUpdate();				
					}else {
						connexion = connexionEnCours.getConnection();
					    preparedStatement = connexion.prepareStatement("INSERT INTO commentaires(idutilisateur,idsite,contenucommentaire,ipcommentaire,datecommentaire) VALUES(?,?,?,?,?);");
					    preparedStatement.setInt(1,commentaireInput.getIdUtilisateur());
					    preparedStatement.setInt(2,commentaireInput.getIdSite());
					    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(commentaireInput.getContenuCommentaire()));
					    preparedStatement.setString(4, CodageGuillemets.getTexteEncode(commentaireInput.getIpCommentaire()));
					    preparedStatement.setDate(5, sqlDate1);
					    preparedStatement.executeUpdate();
					}   
			}else if(idSecteur!=0 && idVoie==0) {//Commenté Sur Secteur
				//
					if(commentaireInput.getIdUtilisateur()==0) {//anonyme
						connexion = connexionEnCours.getConnection();
					    preparedStatement = connexion.prepareStatement("INSERT INTO commentaires(idsite,contenucommentaire,ipcommentaire,datecommentaire,idsecteur) VALUES(?,?,?,?,?);");
					    preparedStatement.setInt(1,commentaireInput.getIdSite());
					    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(commentaireInput.getContenuCommentaire()));
					    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(commentaireInput.getIpCommentaire()));
					    preparedStatement.setDate(4, sqlDate1);
					    preparedStatement.setInt(5,idSecteur);
					    preparedStatement.executeUpdate();				
					}else {
						connexion = connexionEnCours.getConnection();
					    preparedStatement = connexion.prepareStatement("INSERT INTO commentaires(idutilisateur,idsite,contenucommentaire,ipcommentaire,datecommentaire,idsecteur) VALUES(?,?,?,?,?,?);");
					    preparedStatement.setInt(1,commentaireInput.getIdUtilisateur());
					    preparedStatement.setInt(2,commentaireInput.getIdSite());
					    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(commentaireInput.getContenuCommentaire()));
					    preparedStatement.setString(4, CodageGuillemets.getTexteEncode(commentaireInput.getIpCommentaire()));
					    preparedStatement.setDate(5, sqlDate1);
					    preparedStatement.setInt(6,idSecteur);
					    preparedStatement.executeUpdate();
					}   
			}else if(idSecteur!=0 && idVoie!=0) {//Commenté Sur Voie
					//
					if(commentaireInput.getIdUtilisateur()==0) {//anonyme
						connexion = connexionEnCours.getConnection();
					    preparedStatement = connexion.prepareStatement("INSERT INTO commentaires(idsite,contenucommentaire,ipcommentaire,datecommentaire,idsecteur,idvoie) VALUES(?,?,?,?,?,?);");
					    preparedStatement.setInt(1,commentaireInput.getIdSite());
					    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(commentaireInput.getContenuCommentaire()));
					    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(commentaireInput.getIpCommentaire()));
					    preparedStatement.setDate(4, sqlDate1);
					    preparedStatement.setInt(5,idSecteur);
					    preparedStatement.setInt(6,idVoie);
					    preparedStatement.executeUpdate();				
					}else {
						connexion = connexionEnCours.getConnection();
					    preparedStatement = connexion.prepareStatement("INSERT INTO commentaires(idutilisateur,idsite,contenucommentaire,ipcommentaire,datecommentaire,idsecteur,idvoie) VALUES(?,?,?,?,?,?,?);");
					    preparedStatement.setInt(1,commentaireInput.getIdUtilisateur());
					    preparedStatement.setInt(2,commentaireInput.getIdSite());
					    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(commentaireInput.getContenuCommentaire()));
					    preparedStatement.setString(4, CodageGuillemets.getTexteEncode(commentaireInput.getIpCommentaire()));
					    preparedStatement.setDate(5, sqlDate1);
					    preparedStatement.setInt(6,idSecteur);
					    preparedStatement.setInt(7,idVoie);
					    preparedStatement.executeUpdate();
					}   
			}
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

}
