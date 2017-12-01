package projetescalade;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class SuggestionsUtilisateurs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SuggestionsUtilisateurs() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//cookie important pour tracer la dernière page atteinte par l'utilisateur
		String adresse =request.getRequestURI();
		response.addCookie(new Cookie("last",adresse));
		//
		this.getServletContext().getRequestDispatcher("/WEB-INF/suggestions.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		request.setCharacterEncoding("UTF-8");
		//
		String quiPrenom=request.getParameter("prenom");
		String quinom=request.getParameter("nom");
		String quiemail=request.getParameter("email");
		String emailSujet=request.getParameter("objet");
		String emailMessage=request.getParameter("message");
		emailSujet=emailSujet+" - "+quiPrenom+" - "+quinom+" - "+quiemail;
		//
		String erreur=EnvoiEmail.envoi(getServletContext().getInitParameter("emailHost"),quiemail, getServletContext().getInitParameter("emailAccount"), getServletContext().getInitParameter("emailAccountPassword"), getServletContext().getInitParameter("emailAccount"), emailSujet, emailMessage);
		//
		if(erreur.equals("")==true) {
			response.sendRedirect(request.getContextPath() + "/suggestions#ModalValidation");
		}else {
			response.sendRedirect(request.getContextPath() + "/suggestions#Modalerreur");
		}
	}

}
