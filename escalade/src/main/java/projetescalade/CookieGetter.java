package projetescalade;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieGetter {
private static String valeurCookie;

public static String getValeurCookie(HttpServletRequest requestInput, String cookieAChercher) {
	valeurCookie="";
	Cookie[] cookies =requestInput.getCookies();
	if ( cookies != null ) {
        for ( Cookie cookie : cookies ) {
            if ( cookie != null && cookieAChercher.equals(cookie.getName())) {
            	valeurCookie=cookie.getValue();
            }
        }
    }
	return valeurCookie;
}

public static String getCookiePagePrecedente(HttpServletRequest requestInput) {
	valeurCookie="";
	String cookieAChercher="last";
	Cookie[] cookies =requestInput.getCookies();
	if ( cookies != null ) {
        for ( Cookie cookie : cookies ) {
            if ( cookie != null && cookieAChercher.equals(cookie.getName())) {
            	valeurCookie=cookie.getValue();
            }
        }
    }
	if(valeurCookie.equals("")) {
		valeurCookie="/accueil";
	} else {
		valeurCookie=valeurCookie.substring(valeurCookie.lastIndexOf('/'), valeurCookie.length());
	}
	return valeurCookie;
}

}
