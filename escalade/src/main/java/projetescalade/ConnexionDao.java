package projetescalade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jdk.nashorn.internal.runtime.Context;

public class ConnexionDao {
	private String url;
	private String username;
	private String password;
	
	ConnexionDao(String urlinput, String usernameinput, String passwordinput){
		this.url=urlinput;
		this.username=usernameinput;
		this.password=passwordinput;
		
	}
	
	public static ConnexionDao getInstance(String postgresqlUsername, String postgresqlPassword) {
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e){
			
		}
		ConnexionDao instance = new ConnexionDao("jdbc:postgresql://localhost:5432/escalade", postgresqlUsername, postgresqlPassword);
		return instance;
	}
	
	 public Connection getConnection() throws SQLException {
		 Connection connectionEnCours;
		 connectionEnCours=DriverManager.getConnection(url, username, password);
		 connectionEnCours.setAutoCommit(false);
		 return connectionEnCours;
	    }
	 
	 public UtilisateurDao getUtilisateurDao() {
	        return new BddUtilisateurDao(this);
	    }
	 
	 public TopoDao getTopoDao() {
	        return new BddTopoDao(this);
	    }
	 
	 public ReservationDao getReservationDao() {
	        return new BddReservationDao(this);
	    }
	 
	 public CommentaireDao getCommentaireDao() {
	        return new BddCommentairesDao(this);
	    }
	 
	 public SiteDao getSiteDao() {
	        return new BddSitesDao(this);
	    }
	 
	 public NotificationDao getNotificationDao() {
	        return new BddNotificationsDao(this);
	    }
	 
	 public TopoSitesDao getTopoSitesDao() {
	        return new BddTopoSitesDao(this);
	    }
	 
	 public PhotoTopoDao getPhotoTopoDao() {
	        return new BddPhotoTopoDao(this);
	    }
	 public PhotoUtilisateurDao getPhotoUtilisateurDao() {
	        return new BddPhotoUtilisateurDao(this);
	    }
	 public SecteurDao getSecteurDao() {
		 return new BddSecteurDao(this);
	 }
	 public VoieDao getVoieDao() {
		 return new BddVoieDao(this);
	 }
	 public PhotoSiteDao getPhotoSiteDao() {
		 return new BddPhotoSiteDao(this);
	 }
	 public PhotoSecteurDao getPhotoSecteurDao() {
		 return new BddPhotoSecteurDao(this);
	 }
	 public PhotoVoieDao getPhotoVoieDao() {
		 return new BddPhotoVoieDao(this);
	 }
}
