package projetescalade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDao {
	private String url;
	private String username;
	private String password;
	
	ConnexionDao(String urlinput, String usernameinput, String passwordinput){
		this.url=urlinput;
		this.username=usernameinput;
		this.password=passwordinput;
	}
	
	public static ConnexionDao getInstance() {
		
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e){
			
		}
		ConnexionDao instance = new ConnexionDao("jdbc:postgresql://localhost:5432/escalade", "postgres", "flosql75301");
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
}
