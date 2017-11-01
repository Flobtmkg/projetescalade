package projetescalade;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Utilisateur;


public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao utilisateurEnCours;

    public ServletConnexion() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        ConnexionDao maconnexion = ConnexionDao.getInstance();
        this.utilisateurEnCours = maconnexion.getUtilisateurDao();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Gestion de la connexion et de la session
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		Utilisateur nouvelutilisateur=utilisateurEnCours.verifier(request.getParameter("email"), request.getParameter("motDePasse"));
		HttpSession session= request.getSession();
		session.setAttribute("utilisateurencours", nouvelutilisateur);
		//
			if(nouvelutilisateur.getId()==0) {
				response.sendRedirect(request.getContextPath() + fromWho +"#Modal");
			}else {
				response.sendRedirect(request.getContextPath() + fromWho);
			}
	}
}
