package projetescalade;

import javabeans.PhotoSecteur;

public interface PhotoSecteurDao {
	String ajoutPhoto(int idSecteur, String photoPath);
	PhotoSecteur getPhotoPath(int idSecteur);
}
