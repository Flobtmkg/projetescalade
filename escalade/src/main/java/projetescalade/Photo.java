package projetescalade;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.http.Part;

public class Photo {
	//private static final String cheminFichier="C:/projetescalade/imgdone/";
	//private static final String cheminFichier="/resources/img/upload/imgdone";
	private static final int sizeTampon=10240;
	//
	public static String ajout(Part partFichier, String cheminFichier, int idUnique) {
		//
		//
		String nomFichier = getNomFichier(partFichier);
		   // Si on a bien un fichier
	       if (nomFichier != null && !nomFichier.isEmpty()) {
	           // Corrige un bug du fonctionnement d'Internet Explorer
	            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
	           // On écrit définitivement le fichier sur le disque
	           try {
	        	   ecrireFichier(partFichier, idUnique+nomFichier, cheminFichier);
				} catch (IOException e) {
					return "Erreur";
				}
	       }else {
	    	   return "Erreur";
	       }
		//
		return idUnique+nomFichier;
		
	}
	//
	//
	//
	//
	//
		private static void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
	        BufferedInputStream entree = null;
	        BufferedOutputStream sortie = null;
	        try {
	            entree = new BufferedInputStream(part.getInputStream(), sizeTampon);
	            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), sizeTampon);
	            byte[] tampon = new byte[sizeTampon];
	            int longueur;
	            while ((longueur = entree.read(tampon)) > 0) {
	                sortie.write(tampon, 0, longueur);
	            }
	        } finally {
	            try {
	                sortie.close();
	            } catch (IOException ignore) {
	            }
	            try {
	                entree.close();
	            } catch (IOException ignore) {
	            }
	        }
	    }

	    

	    private static String getNomFichier(Part part ) {
	        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	            if ( contentDisposition.trim().startsWith( "filename" ) ) {
	                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
	            }
	        }
	        return null;
	    }   
}
