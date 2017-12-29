package classestravail;

import java.time.LocalDate;
import java.util.ArrayList;

import factorydao.ConnexionDao;
import interfacesdao.ReservationDao;
import javabeans.Reservation;

public class DisponibiliteTopo {
	private ReservationDao accesBddReservation;
	
	 
	 public DisponibiliteTopo(ConnexionDao maconnexion){
		 this.accesBddReservation = maconnexion.getReservationDao();
	 }
	 
	 public boolean isDisponible(int idTopoInput) {//est-ce disponible maintenant?
		 ArrayList<Reservation> listeReservatonEnCoursEtFuture = accesBddReservation.getReservationDuTopoEnCoursEtFuture(idTopoInput);
		 CalculDate today=new CalculDate();
		 if(listeReservatonEnCoursEtFuture.size()==0) {
			 return true;
		 }else {
			 boolean pasDispo=false;
			 for(Reservation res:listeReservatonEnCoursEtFuture) {
				 if(today.getTheDate().isAfter(LocalDate.parse(res.getDatedebutReservation()))||today.getTheDate().isEqual(LocalDate.parse(res.getDatedebutReservation()))){
					 pasDispo=true;
				 }
			 }
			 if(pasDispo==true) {
				 return false;
			 }else {
				 return true;
			 }		 
		 }
	 }
	 
	 public boolean isDisponible(int idTopoInput, String dateDebutInput, String dateFinInput) {//Cette periode ne se supperpose t-elle pas avec une réservation déjà en cours?		 
		 //check de format
		 if(dateDebutInput.contains("/")) {
			 dateDebutInput=CalculDate.conversionFormatEn(dateDebutInput);
		 }
		 if(dateFinInput.contains("/")) {
			 dateFinInput=CalculDate.conversionFormatEn(dateFinInput);
		 }
		 ArrayList<Reservation> listeReservatonSurLaPeriode = new ArrayList<Reservation>();
		 listeReservatonSurLaPeriode=accesBddReservation.getReservationDuTopoApresDebutPeriode(idTopoInput, LocalDate.parse(dateDebutInput));
		 if(listeReservatonSurLaPeriode.size()==0) {
			 return true;
		 }else {
			 boolean pasDispo=false;
			 LocalDate finPeriode=LocalDate.parse(dateFinInput);
			 for(Reservation res:listeReservatonSurLaPeriode) {
				 if(finPeriode.isAfter(LocalDate.parse(res.getDatedebutReservation()))||finPeriode.isEqual(LocalDate.parse(res.getDatedebutReservation()))){
					 pasDispo=true;
				 }
			 }
			 if(pasDispo==true) {
				 return false;
			 }else {
				 return true;
			 }		 
		 }
	}
	 
	 //
	 public ArrayList<Reservation> periodesNonDisponibles(int idTopoInput, String dateDebutInput, String dateFinInput) {//Sort la liste des periodes futures non disponibles		 
		 //check de format
		 if(dateDebutInput.contains("/")) {
			 dateDebutInput=CalculDate.conversionFormatEn(dateDebutInput);
		 }
		 if(dateFinInput.contains("/")) {
			 dateFinInput=CalculDate.conversionFormatEn(dateFinInput);
		 }
		 ArrayList<Reservation> listeReservatonOutput = new ArrayList<Reservation>();
		 ArrayList<Reservation> listeReservatonSurLaPeriode = new ArrayList<Reservation>();
		 listeReservatonSurLaPeriode=accesBddReservation.getReservationDuTopoApresDebutPeriode(idTopoInput, LocalDate.parse(dateDebutInput));
		 if(listeReservatonSurLaPeriode.size()==0) {
			 return listeReservatonOutput;
		 }else {
			 LocalDate finPeriode=LocalDate.parse(dateFinInput);
			 for(Reservation res:listeReservatonSurLaPeriode) {
				 if(finPeriode.isAfter(LocalDate.parse(res.getDatedebutReservation()))||finPeriode.isEqual(LocalDate.parse(res.getDatedebutReservation()))){
					 listeReservatonOutput.add(res);
				 }
			 }
			return listeReservatonOutput;	 
		 }
	}
}
