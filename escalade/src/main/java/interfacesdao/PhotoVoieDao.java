package interfacesdao;

import javabeans.PhotoVoie;

public interface PhotoVoieDao {
	String ajoutPhoto(int idVoie, String photoPath);
	PhotoVoie getPhotoPath(int idVoie);
}
