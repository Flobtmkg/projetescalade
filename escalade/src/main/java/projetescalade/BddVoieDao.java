package projetescalade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javabeans.Secteur;
import javabeans.Voie;

public class BddVoieDao implements VoieDao{
	//
	ConnexionDao connexionEnCours;
	//

	BddVoieDao(ConnexionDao connexionDao) {
		//
		this.connexionEnCours=connexionDao;
		//
	}

	public ArrayList<Voie> trouver(int idSecteurInput) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Voie> voiesDuSecteur = new ArrayList<Voie>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM voie WHERE idsecteur="+idSecteurInput+";");
	         while(resultat.next()) {
	        	 Voie chaqueVoie=new Voie();
	        	 chaqueVoie.setIdVoie(resultat.getInt(1));
	        	 chaqueVoie.setIdSecteur(resultat.getInt(2));
	        	 chaqueVoie.setDescriptionVoie(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueVoie.setEquipementVoie(resultat.getBoolean(4));
	        	 chaqueVoie.setQuotationVoie(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 chaqueVoie.setNomVoie(CodageGuillemets.getTexteDecode(resultat.getString(6)));
	        	 voiesDuSecteur.add(chaqueVoie);
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
		return voiesDuSecteur;
	}

	public Voie trouverParVoieid(int idVoie) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Voie voieTrouvee = new Voie();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM voie WHERE idvoie="+idVoie+";");
	         while(resultat.next()) {
	        	 voieTrouvee.setIdVoie(resultat.getInt(1));
	        	 voieTrouvee.setIdSecteur(resultat.getInt(2));
	        	 voieTrouvee.setDescriptionVoie(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 voieTrouvee.setEquipementVoie(resultat.getBoolean(4));
	        	 voieTrouvee.setQuotationVoie(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 voieTrouvee.setNomVoie(CodageGuillemets.getTexteDecode(resultat.getString(6)));
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
		return voieTrouvee;
	}
//
	//
	//
	public ArrayList<Voie> trouverParCritere(String villeinput,String keyWordInput) {
		//
		//On récupère tout sous forme de voies en effectuant la recherche des villes et des mots clés
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Voie> voies = new ArrayList<Voie>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         if(keyWordInput.equals("")==false && villeinput.equals("")==false) {
	        	 resultat=statement.executeQuery("SELECT * FROM voie FULL JOIN secteur ON voie.idsecteur = secteur.idsecteur FULL JOIN site ON secteur.idsite=site.idsite FULL JOIN photovoie ON voie.idvoie = photovoie.idvoie WHERE villesite='"+villeinput+"' AND (UPPER(nomsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(payssite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(villesite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomsecteur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionsecteur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionvoie) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomvoie) LIKE UPPER('%"+keyWordInput+"%'));");
		     }else if(keyWordInput.equals("")==false && villeinput.equals("")==true) {
		    	 resultat=statement.executeQuery("SELECT * FROM voie FULL JOIN secteur ON voie.idsecteur = secteur.idsecteur FULL JOIN site ON secteur.idsite=site.idsite FULL JOIN photovoie ON voie.idvoie = photovoie.idvoie WHERE UPPER(nomsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(payssite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(villesite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomsecteur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionsecteur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionvoie) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomvoie) LIKE UPPER('%"+keyWordInput+"%');");
		     }else if(keyWordInput.equals("")==true && villeinput.equals("")==false) {
		    	 resultat=statement.executeQuery("SELECT * FROM voie FULL JOIN secteur ON voie.idsecteur = secteur.idsecteur FULL JOIN site ON secteur.idsite=site.idsite FULL JOIN photovoie ON voie.idvoie = photovoie.idvoie WHERE villesite='"+villeinput+"';");
		     }else if(keyWordInput.equals("")==true && villeinput.equals("")==true) {
		    	 resultat=statement.executeQuery("SELECT * FROM voie FULL JOIN secteur ON voie.idsecteur = secteur.idsecteur FULL JOIN site ON secteur.idsite=site.idsite FULL JOIN photovoie ON voie.idvoie = photovoie.idvoie;");
		     }
	         while(resultat.next()) {
	        	 Voie chaqueVoie=new Voie();
	        	 chaqueVoie.setIdVoie(resultat.getInt(1));
	        	 chaqueVoie.setIdSecteur(resultat.getInt(2));
	        	 chaqueVoie.setDescriptionVoie(CodageGuillemets.getTexteDecode(resultat.getString(3)));
	        	 chaqueVoie.setEquipementVoie(resultat.getBoolean(4));
	        	 chaqueVoie.setQuotationVoie(CodageGuillemets.getTexteDecode(resultat.getString(5)));
	        	 chaqueVoie.setNomVoie(CodageGuillemets.getTexteDecode(resultat.getString(6)));
	        	 chaqueVoie.setIdSite(resultat.getInt(8));
	        	 chaqueVoie.setNomSecteur(CodageGuillemets.getTexteDecode(resultat.getString(9)));
	        	 chaqueVoie.setDescriptionSecteur(CodageGuillemets.getTexteDecode(resultat.getString(10)));
	        	 chaqueVoie.setHauteurSecteur(resultat.getDouble(11));
	        	 chaqueVoie.setNomSite(CodageGuillemets.getTexteDecode(resultat.getString(13)));
	        	 chaqueVoie.setPaysSite(CodageGuillemets.getTexteDecode(resultat.getString(14)));
	        	 chaqueVoie.setVilleSite(CodageGuillemets.getTexteDecode(resultat.getString(15)));
	        	 chaqueVoie.setDescriptionSite(CodageGuillemets.getTexteDecode(resultat.getString(18)));
	        	 String photo=CodageGuillemets.getTexteDecode(resultat.getString(21));
	        	 //traitement spé des données d'aperçus photo
	        	 if(photo!=null && !photo.isEmpty()) {
	        		 photo="/escalade/img/"+"preview"+photo;
	        		 chaqueVoie.setPhotopath(photo);
	        	 }
	        	 voies.add(chaqueVoie);
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
		return voies;
	}

	public String ajouter(Voie inputVoie) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement = connexion.prepareStatement("INSERT INTO voie(idsecteur, nomvoie, descriptionvoie, equipementvoie, quotationvoie) VALUES(?,?,?,?,?);");
		    preparedStatement.setInt(1, inputVoie.getIdSecteur());
		    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(inputVoie.getNomVoie()));
		    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(inputVoie.getDescriptionVoie()));
		    preparedStatement.setBoolean(4, inputVoie.isEquipementVoie());
		    preparedStatement.setString(5, inputVoie.getQuotationVoie());
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

	public int trouverLastIDVoie() {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        int dernierid = 0;
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT MAX(idvoie) FROM voie;");
	         while(resultat.next()) {
	        	 dernierid=resultat.getInt(1);
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
		return dernierid;
	}
	//
	//

}
