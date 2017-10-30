package projetescalade;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CalculDate {
private String theDay;
private String theMonth;
private String theYear;


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
	}
	
}
