package projetescalade;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Servlet1() {
        super();
        // 
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			//cookie important pour tracer la dernière page atteinte par l'utilisateur
			String adresse =request.getRequestURI();
			response.addCookie(new Cookie("last",adresse));
			//
			this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
