package projetescalade;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

public class CalculDate {
private String theDay;
private String theMonth;
private String theYear;
private LocalDate theDate;


public LocalDate getTheDate() {
	return theDate;
}

public String getTheDay() {
	return theDay;
}

public String getTheMonth() {
	return theMonth;
}

public String getTheYear() {
	return theYear;
}


CalculDate(){
		LocalDateTime maintenant = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		String textDate;
		textDate=String.valueOf(maintenant.getDayOfMonth());
		if(textDate.length()==1) {
			theDay="0"+textDate;
		}else {
			theDay=textDate;
		}
		textDate=String.valueOf(maintenant.getMonthValue());
		if(textDate.length()==1) {
			theMonth="0"+textDate;
		}else {
			theMonth=textDate;
		}
		theYear=String.valueOf(maintenant.getYear());
		theDate=maintenant.toLocalDate();
	}

int diff(LocalDate inputDate) {
	Period difference;
	difference=inputDate.until(theDate);
	return difference.getYears();
}

public static String conversionFormatFr(String Dateinput) {
		String formatFR;
		if(Dateinput.contains("-")) {
			String[] formatEN = Dateinput.split("-");
			formatFR = formatEN[2]+"/"+formatEN[1]+"/"+formatEN[0];
			Dateinput=formatFR;
		}
	return Dateinput;
}

public static String conversionFormatEn(String Dateinput) {
	String formatEN;
	if(Dateinput.contains("/")) {
		String[] formatFR = Dateinput.split("/");
		formatEN = formatFR[2]+"-"+formatFR[1]+"-"+formatFR[0];
		Dateinput=formatEN;
	}
return Dateinput;
}
	
}
