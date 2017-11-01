package projetescalade;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

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
		theDay=String.valueOf(maintenant.getDayOfMonth());
		theMonth=String.valueOf(maintenant.getMonthValue());
		theYear=String.valueOf(maintenant.getYear());
		theDate=maintenant.toLocalDate();
	}

int diff(LocalDate inputDate) {
	Period difference;
	difference=inputDate.until(theDate);
	return difference.getYears();
}
	
}
