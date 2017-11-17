package projetescalade;

import java.time.LocalDate;
import java.util.ArrayList;

import javabeans.Reservation;

public interface ReservationDao {
	ArrayList<Reservation> getReservationUtilisateur(int idutilisateur);
	ArrayList<Reservation> getReservationDuTopo(int idtopo);
	ArrayList<Reservation> getReservationDuTopoEnCoursEtFuture(int idtopo);
	ArrayList<Reservation> getReservationDuTopoApresDebutPeriode(int idtopo, LocalDate debut);
	Reservation getLastReservationDuTopo(int idtopo);
	String ajout(Reservation reservationinput);
	String modifierString(String titreCol, String valeurCol, int idReservation);
	String modifierBilan(boolean valeurCol, int idReservation);
}
