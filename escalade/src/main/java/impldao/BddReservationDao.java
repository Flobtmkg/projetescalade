package impldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import classestravail.CalculDate;
import classestravail.CodageGuillemets;
import factorydao.ConnexionDao;
import interfacesdao.ReservationDao;
import javabeans.Reservation;
import javabeans.Topo;
import javabeans.Utilisateur;

public class BddReservationDao implements ReservationDao {
	//
	private ConnexionDao connexionEnCours;
	//
	public BddReservationDao(ConnexionDao connexioninput){
		connexionEnCours=connexioninput;
	}
	//
	public ArrayList<Reservation> getReservationUtilisateur(int idutilisateur) {
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedatedebut = new Date();
        Date conversiondedatefin = new Date();
        Date conversiondedate = new Date();
        ArrayList<Reservation>reservationEffectuees=new ArrayList<Reservation>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM reservation FULL JOIN topo ON reservation.idtopo=topo.idtopo FULL JOIN utilisateur ON topo.idproprietaire=utilisateur.idutilisateur WHERE reservation.idutilisateur="+idutilisateur+";");
	         while(resultat.next()) {
	        	 Reservation chaqueReservation =new Reservation();
	        	 Utilisateur proprietaireTopo =new Utilisateur(0,"","","","","","","","");
	        	 Topo topoAssocieReservation =new Topo();
	        	 chaqueReservation.setIdReservation(resultat.getInt("idreservation"));
	        	 chaqueReservation.setIdUtilisateur(resultat.getInt("idutilisateur"));
	        	 chaqueReservation.setIdTopo(resultat.getInt("idtopo"));
	        	 conversiondedatedebut=resultat.getDate("datedebut");
	        	 conversiondedatefin=resultat.getDate("datefin");
	        	 chaqueReservation.setCommentaireReservation(CodageGuillemets.getTexteDecode(resultat.getString("commentairereservation")));
	        	 chaqueReservation.setBilanReservation(resultat.getBoolean("bilanreservation"));
	        	 if(conversiondedatedebut!=null) {
	        		 chaqueReservation.setDatedebutReservation(conversiondedatedebut.toString());
	        	 }
	        	 if(conversiondedatefin!=null) {
	        		 chaqueReservation.setDatefinReservation(conversiondedatefin.toString());
	        	 }
	        	 //
	        	 proprietaireTopo.setId(resultat.getInt("idproprietaire"));
	        	 proprietaireTopo.setNom(CodageGuillemets.getTexteDecode(resultat.getString("nomutilisateur")));
	        	 proprietaireTopo.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString("prenomutilisateur")));
	        	 proprietaireTopo.setEmail(CodageGuillemets.getTexteDecode(resultat.getString("emailutilisateur")));
	        	 conversiondedate=resultat.getDate("datenaissanceutilisateur");
	        	 if(conversiondedate!=null) {
	        		 proprietaireTopo.setDateNaissance(conversiondedate.toString());
	        	 }
	        	 proprietaireTopo.setPays(CodageGuillemets.getTexteDecode(resultat.getString("paysutilisateur")));
	        	 proprietaireTopo.setVille(CodageGuillemets.getTexteDecode(resultat.getString("villeutilisateur")));
	        	 proprietaireTopo.setDescription(CodageGuillemets.getTexteDecode(resultat.getString("descriptionutilisateur")));
	        	 //
	        	 topoAssocieReservation.setIdTopo(resultat.getInt("idtopo"));
	        	 topoAssocieReservation.setIdProprietaire(resultat.getInt("idproprietaire"));
	        	 topoAssocieReservation.setNomTopo(CodageGuillemets.getTexteDecode(resultat.getString("nomtopo")));
	        	 topoAssocieReservation.setDescriptionTopo(CodageGuillemets.getTexteDecode(resultat.getString("descriptiontopo")));
	        	 //
	        	 chaqueReservation.setProprietaireAssocié(proprietaireTopo);
	        	 chaqueReservation.setTopoAssocié(topoAssocieReservation);
	        	 //
	        	 reservationEffectuees.add(chaqueReservation);
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
		return reservationEffectuees;
	}
	//
	//
	//
	public ArrayList<Reservation> getReservationDuTopoEnCoursEtFuture(int idtopoInput) {
		//
		CalculDate calculdate1=new CalculDate();
		LocalDate dateActuelle=calculdate1.getTheDate();
		String dateDuJour=dateActuelle.toString();
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedatedebut = new Date();
        Date conversiondedatefin = new Date();
        ArrayList<Reservation>reservationEffectuees=new ArrayList<Reservation>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM reservation WHERE idtopo='"+idtopoInput+"' AND datefin >= '"+dateDuJour+"'::date");
	         while(resultat.next()) {
	        	 Reservation chaqueReservation =new Reservation();
	        	 chaqueReservation.setIdReservation(resultat.getInt("idreservation"));
	        	 chaqueReservation.setIdUtilisateur(resultat.getInt("idutilisateur"));
	        	 chaqueReservation.setIdTopo(resultat.getInt("idtopo"));
	        	 conversiondedatedebut=resultat.getDate("datedebut");
	        	 conversiondedatefin=resultat.getDate("datefin");
	        	 chaqueReservation.setCommentaireReservation(CodageGuillemets.getTexteDecode(resultat.getString("commentairereservation")));
	        	 chaqueReservation.setBilanReservation(resultat.getBoolean("bilanreservation"));
	        	 if(conversiondedatedebut!=null) {
	        		 chaqueReservation.setDatedebutReservation(conversiondedatedebut.toString());
	        	 }
	        	 if(conversiondedatefin!=null) {
	        		 chaqueReservation.setDatefinReservation(conversiondedatefin.toString());
	        	 }
	        	 reservationEffectuees.add(chaqueReservation);
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
		return reservationEffectuees;
	}
	//
	//
	public Reservation getLastReservationDuTopo(int idtopo) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        CalculDate aujourdhui= new CalculDate();
        Date conversiondedatedebut = new Date();
        Date conversiondedatefin = new Date();
        Reservation lastReservation=new Reservation();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM reservation WHERE idtopo="+idtopo+" AND datefin<'"+aujourdhui.getTheDate().toString()+"'::date ORDER BY idreservation DESC LIMIT 1;");
	         while(resultat.next()) {
	        	 lastReservation.setIdReservation(resultat.getInt("idreservation"));
	        	 lastReservation.setIdUtilisateur(resultat.getInt("idutilisateur"));
	        	 lastReservation.setIdTopo(resultat.getInt("idtopo"));
	        	 conversiondedatedebut=resultat.getDate("datedebut");
	        	 conversiondedatefin=resultat.getDate("datefin");
	        	 lastReservation.setCommentaireReservation(CodageGuillemets.getTexteDecode(resultat.getString("commentairereservation")));
	        	 lastReservation.setBilanReservation(resultat.getBoolean("bilanreservation"));
	        	 if(conversiondedatedebut!=null) {
	        		 lastReservation.setDatedebutReservation(conversiondedatedebut.toString());
	        	 }
	        	 if(conversiondedatefin!=null) {
	        		 lastReservation.setDatefinReservation(conversiondedatefin.toString());
	        	 }
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
		return lastReservation;
	}
	//
	//
	//
	public String ajout(Reservation reservationinput) {
		//
		String erreurEventuelle="";
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		//
		//conversion de dates
		LocalDate utildate1=null;
		LocalDate utildate2=null;
		try {
			utildate1=LocalDate.parse(reservationinput.getDatedebutReservation());
			utildate2=LocalDate.parse(reservationinput.getDatefinReservation());
		} catch (Exception e1) {
			e1.printStackTrace();
			erreurEventuelle=erreurEventuelle + e1.getMessage() + ";";
			return erreurEventuelle;
		}
		long millisecondsSince1970A =utildate1.toEpochDay()*86400000;
		long millisecondsSince1970B =utildate2.toEpochDay()*86400000;
		java.sql.Date sqlDate1=new java.sql.Date(millisecondsSince1970A);
		java.sql.Date sqlDate2=new java.sql.Date(millisecondsSince1970B);
		//
		//
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement = connexion.prepareStatement("INSERT INTO reservation(idutilisateur, idtopo, datedebut, datefin, commentairereservation, bilanreservation) VALUES(?, ?, ?, ?, ?, ?);");
		    preparedStatement.setInt(1, reservationinput.getIdUtilisateur());
		    preparedStatement.setInt(2, reservationinput.getIdTopo());
		    preparedStatement.setDate(3, sqlDate1);
		    preparedStatement.setDate(4, sqlDate2);
		    preparedStatement.setString(5, CodageGuillemets.getTexteEncode(reservationinput.getCommentaireReservation()));
		    preparedStatement.setBoolean(6, reservationinput.isBilanReservation());
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
	public ArrayList<Reservation> getReservationDuTopoApresDebutPeriode(int idtopoInput, LocalDate debut) {
		//
		//
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedatedebut = new Date();
        Date conversiondedatefin = new Date();
        ArrayList<Reservation>reservationEffectuees=new ArrayList<Reservation>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM reservation WHERE idtopo='"+idtopoInput+"' AND datefin >= '"+debut.toString()+"'::date");
	         while(resultat.next()) {
	        	 Reservation chaqueReservation =new Reservation();
	        	 chaqueReservation.setIdReservation(resultat.getInt("idreservation"));
	        	 chaqueReservation.setIdUtilisateur(resultat.getInt("idutilisateur"));
	        	 chaqueReservation.setIdTopo(resultat.getInt("idtopo"));
	        	 conversiondedatedebut=resultat.getDate("datedebut");
	        	 conversiondedatefin=resultat.getDate("datefin");
	        	 chaqueReservation.setCommentaireReservation(CodageGuillemets.getTexteDecode(resultat.getString("commentairereservation")));
	        	 chaqueReservation.setBilanReservation(resultat.getBoolean("bilanreservation"));
	        	 if(conversiondedatedebut!=null) {
	        		 chaqueReservation.setDatedebutReservation(conversiondedatedebut.toString());
	        	 }
	        	 if(conversiondedatefin!=null) {
	        		 chaqueReservation.setDatefinReservation(conversiondedatefin.toString());
	        	 }
	        	 reservationEffectuees.add(chaqueReservation);
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
		return reservationEffectuees;
	}
	//
	//
	public String modifierString(String titreCol, String valeurCol,int idReservation) {
		//
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        //
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement=connexion.prepareStatement("UPDATE reservation SET "+titreCol+"=? WHERE idreservation = ?;");
		    //
		    preparedStatement.setString(1, CodageGuillemets.getTexteEncode(valeurCol));
		    preparedStatement.setInt(2, idReservation);
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
	//
	//
	public String modifierBilan(boolean valeurCol,int idReservation) {
		//
		//
		String erreurEventuelle="";
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        //
        //
		try {
		    connexion = connexionEnCours.getConnection();
		    preparedStatement=connexion.prepareStatement("UPDATE reservation SET bilanreservation=? WHERE idreservation = ?;");
		    //
		    preparedStatement.setBoolean(1, valeurCol);
		    preparedStatement.setInt(2, idReservation);
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
		
		//
		//
	}
	public ArrayList<Reservation> getReservationDuTopo(int idtopo) {
		//
		Connection connexion = null;
        Statement statement = null;
        ResultSet resultat=null;
        Date conversiondedatedebut = new Date();
        Date conversiondedatefin = new Date();
        Date conversiondedate = new Date();
        ArrayList<Reservation>reservationEffectuees=new ArrayList<Reservation>();
        try {
        	 connexion = connexionEnCours.getConnection();
	         statement=connexion.createStatement();
	         resultat=statement.executeQuery("SELECT * FROM reservation FULL JOIN utilisateur ON reservation.idutilisateur=utilisateur.idutilisateur WHERE idtopo='"+idtopo+"'");
	         while(resultat.next()) {
	        	 Reservation chaqueReservation =new Reservation();
	        	 Utilisateur utilisateurQuiReserveTopo= new Utilisateur(0,"","","","","","","","");
	        	 
	        	 chaqueReservation.setIdReservation(resultat.getInt("idreservation"));
	        	 chaqueReservation.setIdUtilisateur(resultat.getInt("idutilisateur"));
	        	 chaqueReservation.setIdTopo(resultat.getInt("idtopo"));
	        	 conversiondedatedebut=resultat.getDate("datedebut");
	        	 conversiondedatefin=resultat.getDate("datefin");
	        	 chaqueReservation.setCommentaireReservation(CodageGuillemets.getTexteDecode(resultat.getString("commentairereservation")));
	        	 chaqueReservation.setBilanReservation(resultat.getBoolean("bilanreservation"));
	        	 if(conversiondedatedebut!=null) {
	        		 chaqueReservation.setDatedebutReservation(conversiondedatedebut.toString());
	        	 }
	        	 if(conversiondedatefin!=null) {
	        		 chaqueReservation.setDatefinReservation(conversiondedatefin.toString());
	        	 }
	        	 //
	        	 utilisateurQuiReserveTopo.setId(resultat.getInt("idutilisateur"));
	        	 utilisateurQuiReserveTopo.setNom(CodageGuillemets.getTexteDecode(resultat.getString("nomutilisateur")));
	        	 utilisateurQuiReserveTopo.setPrenom(CodageGuillemets.getTexteDecode(resultat.getString("prenomutilisateur")));
	        	 utilisateurQuiReserveTopo.setEmail(CodageGuillemets.getTexteDecode(resultat.getString("emailutilisateur")));
	        	 conversiondedate=resultat.getDate("datenaissanceutilisateur");
	        	 if(conversiondedate!=null) {
	        		 utilisateurQuiReserveTopo.setDateNaissance(conversiondedate.toString());
	        	 }
	        	 utilisateurQuiReserveTopo.setPays(CodageGuillemets.getTexteDecode(resultat.getString("paysutilisateur")));
	        	 utilisateurQuiReserveTopo.setVille(CodageGuillemets.getTexteDecode(resultat.getString("villeutilisateur")));
	        	 utilisateurQuiReserveTopo.setDescription(CodageGuillemets.getTexteDecode(resultat.getString("descriptionutilisateur")));
	        	 //
	        	 chaqueReservation.setUtilisateurQuiReserve(utilisateurQuiReserveTopo);;
	        	 reservationEffectuees.add(chaqueReservation);
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
		return reservationEffectuees;
	}
}
