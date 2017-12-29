package interfacesdao;

import javabeans.PhotoTopo;

public interface PhotoTopoDao {
String ajoutPhoto(int idTopo, String photoPath);
PhotoTopo getPhotoPath(int idTopo);
}
