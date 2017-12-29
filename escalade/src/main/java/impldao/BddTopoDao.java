package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.TopoDao;
import javabeans.Site;
import javabeans.Topo;
import javabeans.Utilisateur;

public class BddTopoDao implements TopoDao {
	//
	private ConnexionDao connexionEnCours;
	//
	public BddTopoDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}
	public ArrayList<Topo> trouverToposParUtilisateur(int idproprietaire) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Topo>toposPossede=new ArrayList<Topo>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE idproprietaire='"+idproprietaire+"'");
	         while(resultat.next()) {
	        	 Topo chaqueTopo =new Topo();
	        	 chaqueTopo.setIdTopo(resultat.getInt("idtopo"));
	        	 chaqueTopo.setIdProprietaire(resultat.getInt("idproprietaire"));
	        	 chaqueTopo.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString("nomtopo")));
	        	 chaqueTopo.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString("descriptiontopo")));
	        	 toposPossede.add(chaqueTopo);
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
		return toposPossede;
	}
	
	public Topo trouverTopo(int idtopo) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Topo topoOutput=new Topo();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE idtopo='"+idtopo+"'");
	         while(resultat.next()) {
	        	 topoOutput.setIdTopo(resultat.getInt("idtopo"));
	        	 topoOutput.setIdProprietaire(resultat.getInt("idproprietaire"));
	        	 topoOutput.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString("nomtopo")));
	        	 topoOutput.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString("descriptiontopo")));
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
		return topoOutput;
	}
	//
	//
	public ArrayList<Topo> trouverTopo(ArrayList<Integer> idTopos) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Topo> toposOutput=new ArrayList<Topo>();
        String clauseWhere="";
        if(idTopos.isEmpty()) {
        	return toposOutput;
        }else if(idTopos.get(0)==0){
        	clauseWhere="1=1";
        }else {
        	 for(int id:idTopos) {
             	clauseWhere=clauseWhere+"idtopo="+id+" OR ";
             }
             clauseWhere=clauseWhere.substring(0, clauseWhere.length()-4);//on enlève le dernier " OR "
        }
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE "+clauseWhere+";");
	         while(resultat.next()) {
	        	 Topo chaqueTopo=new Topo();
	        	 chaqueTopo.setIdTopo(resultat.getInt("idtopo"));
	        	 chaqueTopo.setIdProprietaire(resultat.getInt("idproprietaire"));
	        	 chaqueTopo.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString("nomtopo")));
	        	 chaqueTopo.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString("descriptiontopo")));
	        	 toposOutput.add(chaqueTopo);
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
		return toposOutput;
	}
	//
	//
	public String ajoutTopo(Topo topoAAjouter) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement = connexion.prepareStatement("INSERT INTO topo(idproprietaire,nomtopo,descriptiontopo) VALUES(?,?,?);");
		    preparedStatement.setInt(1, topoAAjouter.getIdProprietaire());
		    preparedStatement.setString(2, CodageGuillemets.getTexteEncode(topoAAjouter.getNomTopo()));
		    preparedStatement.setString(3, CodageGuillemets.getTexteEncode(topoAAjouter.getDescriptionTopo()));
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
	//
	//
	public Topo trouverTopoSansIdTopo(int idproprietaire, String titreTopo, String descriptionTopo) {
		//
		titreTopo=CodageGuillemets.getTexteEncode(titreTopo);
		descriptionTopo=CodageGuillemets.getTexteEncode(descriptionTopo);
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Topo topoOutput=new Topo();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM topo WHERE idproprietaire="+idproprietaire+" AND nomtopo='"+titreTopo+"' AND descriptiontopo='"+descriptionTopo+"'");
	         while(resultat.next()) {
	        	 topoOutput.setIdTopo(resultat.getInt("idtopo"));
	        	 topoOutput.setIdProprietaire(resultat.getInt("idproprietaire"));
	        	 topoOutput.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString("nomtopo")));
	        	 topoOutput.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString("descriptiontopo")));
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
		return topoOutput;
	}
	//
	//
	public ArrayList<Topo> trouverParCritere(String nomSiteInput, String keyWordInput, String disponibilite) {
		//
		//On récupère tout sous forme de topos en effectuant la recherche et des mots clés et des sites
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        ArrayList<Topo> topos = new ArrayList<Topo>();
        String requete;
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         if(keyWordInput.equals("")==false && nomSiteInput.equals("")==false) {
	        	 requete="SELECT DISTINCT topo.idtopo,topo.nomtopo,topo.descriptiontopo,phototopo.pathphoto,topo.idproprietaire,utilisateur.prenomutilisateur,utilisateur.nomutilisateur,site.idsite,site.nomsite FROM topo FULL JOIN toposites ON topo.idtopo = toposites.idtopo FULL JOIN site ON toposites.idsite=site.idsite FULL JOIN utilisateur ON topo.idproprietaire=utilisateur.idutilisateur FULL JOIN phototopo ON topo.idtopo = phototopo.idtopo FULL JOIN reservation ON reservation.idtopo = topo.idtopo WHERE toposites.idtopo IN (SELECT idtopo FROM toposites FULL JOIN site ON toposites.idsite=site.idsite WHERE nomsite='"+nomSiteInput+"') AND nomsite='"+nomSiteInput+"' AND (UPPER(nomtopo) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptiontopo) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(payssite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(villesite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomutilisateur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(prenomutilisateur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionutilisateur) LIKE UPPER('%"+keyWordInput+"%'))";
	        	 if(disponibilite.equals("")==false) {
	        		 requete=requete+" AND topo.idtopo NOT IN (SELECT topo.idtopo FROM topo FULL JOIN reservation ON topo.idtopo=reservation.idtopo WHERE DATE(NOW()) BETWEEN datedebut AND datefin)";
	        	 }
	        	 resultat=statement.executeQuery(requete);
		     }else if(keyWordInput.equals("")==false && nomSiteInput.equals("")==true) {
		    	 requete="SELECT DISTINCT topo.idtopo,topo.nomtopo,topo.descriptiontopo,phototopo.pathphoto,topo.idproprietaire,utilisateur.prenomutilisateur,utilisateur.nomutilisateur,site.idsite,site.nomsite FROM topo FULL JOIN toposites ON topo.idtopo = toposites.idtopo FULL JOIN site ON toposites.idsite=site.idsite FULL JOIN utilisateur ON topo.idproprietaire=utilisateur.idutilisateur FULL JOIN phototopo ON topo.idtopo = phototopo.idtopo FULL JOIN reservation ON reservation.idtopo = topo.idtopo WHERE toposites.idtopo IN (SELECT idtopo FROM toposites FULL JOIN site ON toposites.idsite=site.idsite) AND (UPPER(nomtopo) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptiontopo) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(payssite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(villesite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionsite) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(nomutilisateur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(prenomutilisateur) LIKE UPPER('%"+keyWordInput+"%') OR UPPER(descriptionutilisateur) LIKE UPPER('%"+keyWordInput+"%'))";
		    	 if(disponibilite.equals("")==false) {
	        		 requete=requete+" AND topo.idtopo NOT IN (SELECT topo.idtopo FROM topo FULL JOIN reservation ON topo.idtopo=reservation.idtopo WHERE DATE(NOW()) BETWEEN datedebut AND datefin)";
	        	 }
		    	 resultat=statement.executeQuery(requete);
		     }else if(keyWordInput.equals("")==true && nomSiteInput.equals("")==false) {
		    	 requete="SELECT DISTINCT topo.idtopo,topo.nomtopo,topo.descriptiontopo,phototopo.pathphoto,topo.idproprietaire,utilisateur.prenomutilisateur,utilisateur.nomutilisateur,site.idsite,site.nomsite FROM topo FULL JOIN toposites ON topo.idtopo = toposites.idtopo FULL JOIN site ON toposites.idsite=site.idsite FULL JOIN utilisateur ON topo.idproprietaire=utilisateur.idutilisateur FULL JOIN phototopo ON topo.idtopo = phototopo.idtopo FULL JOIN reservation ON reservation.idtopo = topo.idtopo WHERE toposites.idtopo IN (SELECT idtopo FROM toposites FULL JOIN site ON toposites.idsite=site.idsite WHERE nomsite='"+nomSiteInput+"')";
		    	 if(disponibilite.equals("")==false) {
	        		 requete=requete+" AND topo.idtopo NOT IN (SELECT topo.idtopo FROM topo FULL JOIN reservation ON topo.idtopo=reservation.idtopo WHERE DATE(NOW()) BETWEEN datedebut AND datefin)";
	        	 }
		    	 resultat=statement.executeQuery(requete);
		     }else if(keyWordInput.equals("")==true && nomSiteInput.equals("")==true) {
		    	 requete="SELECT DISTINCT topo.idtopo,topo.nomtopo,topo.descriptiontopo,phototopo.pathphoto,topo.idproprietaire,utilisateur.prenomutilisateur,utilisateur.nomutilisateur,site.idsite,site.nomsite FROM topo FULL JOIN toposites ON topo.idtopo = toposites.idtopo FULL JOIN site ON toposites.idsite=site.idsite FULL JOIN utilisateur ON topo.idproprietaire=utilisateur.idutilisateur FULL JOIN phototopo ON topo.idtopo = phototopo.idtopo FULL JOIN reservation ON reservation.idtopo = topo.idtopo WHERE toposites.idtopo IN (SELECT idtopo FROM toposites FULL JOIN site ON toposites.idsite=site.idsite)";
		    	 if(disponibilite.equals("")==false) {
	        		 requete=requete+" AND topo.idtopo NOT IN (SELECT topo.idtopo FROM topo FULL JOIN reservation ON topo.idtopo=reservation.idtopo WHERE DATE(NOW()) BETWEEN datedebut AND datefin)";
	        	 }
		    	 resultat=statement.executeQuery(requete);
		     }
	         while(resultat.next()) {
	        	 Topo chaqueTopo=new Topo();
	        	 chaqueTopo.setIdTopo(resultat.getInt("idtopo"));
	        	 chaqueTopo.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString("nomtopo")));
	        	 chaqueTopo.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString("descriptiontopo")));
	        	 String photo=CodageGuillemets.getTexteDecode(resultat.getString("pathphoto"));
	        	 //
	        	 Utilisateur proprietaire = new Utilisateur(0,"","","","","","","","");
	        	 proprietaire.setId(resultat.getInt("idproprietaire"));
	        	 proprietaire.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString("prenomutilisateur")));
	        	 proprietaire.setNom(CodageGuillemets.getTexteDecode(resultat.getString("nomutilisateur")));
	        	 //
	        	 chaqueTopo.setProprietaire(proprietaire);
	        	 chaqueTopo.setIdProprietaire(resultat.getInt("idproprietaire"));
	        	 //
	        	 Site siteAssocie = new Site();
	        	 siteAssocie.setIdSite(resultat.getInt("idsite"));
	        	 siteAssocie.setNomSite(CodageGuillemets.getTexteDecode(resultat.getString("nomsite")));
	        	 //
	        	 chaqueTopo.setSiteAssocie(siteAssocie);
	        	 //
	        	 //traitement spé des données d'aperçus photo
	        	 if(photo!=null && !photo.isEmpty()) {
	        		 photo="/escalade/img/"+"preview"+photo;
	        		 chaqueTopo.setPhotopath(photo);
	        	 }
	        	 topos.add(chaqueTopo);
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
		return topos;
	}

}
