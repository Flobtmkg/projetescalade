package javabeans;

public class PhotoTopo {
private int idPhotoTopo;	
private int idTopo;
private String pathPhoto;
//
public int getIdTopo() {
	return this.idTopo;
}
public String getPathPhoto() {
	return pathPhoto;
}
public void setPathPhoto(String pathPhoto) {
	this.pathPhoto = pathPhoto;
}
public void setIdTopo(int idTopo) {
	this.idTopo = idTopo;
}
public int getIdPhotoTopo() {
	return idPhotoTopo;
}
public void setIdPhotoTopo(int idPhotoTopo) {
	this.idPhotoTopo = idPhotoTopo;
}

}
