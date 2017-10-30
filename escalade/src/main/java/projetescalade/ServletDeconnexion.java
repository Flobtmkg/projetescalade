package projetescalade;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ServletDeconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ServletDeconnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    /* Récupération et destruction de la session en cours */
	    HttpSession session = request.getSession();
	    session.invalidate();
	    response.sendRedirect( request.getContextPath() + "/index.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
