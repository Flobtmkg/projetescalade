package projetescalade;
import java.util.ArrayList;

import javabeans.Notification;

public interface NotificationDao {
ArrayList<Notification> trouverNotificationsParUtilisateur(int idutilisateur);
String ajouter(Notification notificationInput);
String definirTraitement(int idNotification, boolean traitement);
}
