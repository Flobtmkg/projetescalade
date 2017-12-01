package projetescalade;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
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
	        	   saveScaledImage(cheminFichier+idUnique+nomFichier,cheminFichier+"preview"+idUnique+nomFichier);
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
	private static  void saveScaledImage(String filePath,String outputFile){//génère une copie en apperçu de 100*100 px
	    try {

	        BufferedImage sourceImage = ImageIO.read(new File(filePath));
	        int width = sourceImage.getWidth();
	        int height = sourceImage.getHeight();

	        if(width>height){
	            float extraSize=height-100;
	            float percentHight = (extraSize/height)*100;
	            float percentWidth = width - ((width/100)*percentHight);
	            BufferedImage img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
	            Image scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);
	            BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);
	            img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);

	            ImageIO.write(img2, "jpg", new File(outputFile));    
	        }else{
	            float extraSize= width-100;
	            float percentWidth = (extraSize/width)*100;
	            float  percentHight = height - ((height/100)*percentWidth);
	            BufferedImage img = new BufferedImage(100, (int)percentHight, BufferedImage.TYPE_INT_RGB);
	            Image scaledImage = sourceImage.getScaledInstance(100,(int)percentHight, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);
	            BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);
	            img2 = img.getSubimage(0, (int)((percentHight-100)/2), 100, 100);

	            ImageIO.write(img2, "jpg", new File(outputFile));
	        }

	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	}
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
