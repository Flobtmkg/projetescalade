package interfacesdao;

import javabeans.PhotoSite;

public interface PhotoSiteDao {
	String ajoutPhoto(int idSite, String photoPath);
	PhotoSite getPhotoPath(int idSite);
}
