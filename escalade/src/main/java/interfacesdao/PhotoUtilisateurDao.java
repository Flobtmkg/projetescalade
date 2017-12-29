package interfacesdao;

import javabeans.PhotoUtilisateur;

public interface PhotoUtilisateurDao {
	String ajoutPhoto(int idUtilisateur, String photoPath);
	PhotoUtilisateur getPhotoPath(int idUtilisateur);
}
