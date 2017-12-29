package classestravail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class EnvoiEmail {
	public static String envoi(String host, String FromWho, String UserName, String PassWord, String toWho, String subject, String message) {
		String erreureventuelle="";
		Email suggestion= new SimpleEmail();
		try {
			suggestion.setHostName(host);//en passant par Gmail smtp.googlemail.com
			suggestion.setAuthenticator(new DefaultAuthenticator(UserName, PassWord));//on passe par un compte Gmail il faut donc s'identifier
			suggestion.setSSLOnConnect(true);//connexion sécurisée?
			suggestion.setFrom(FromWho);//Définit le champ From de l'email lors des tests: suggestions.projetescalade@gmail.com
			suggestion.setSubject(subject);
			suggestion.setMsg(message);
			suggestion.addTo(toWho);//A qui l'email doit être envoyé
			suggestion.send();
			return erreureventuelle;
		}catch(Exception e) {
			//erreur
			erreureventuelle="erreur d'envoi";
			return erreureventuelle;
		}
	}
}
