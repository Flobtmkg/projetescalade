package projetescalade;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Utilisateur;


public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao utilisateurEnCours;

	public void init() throws ServletException {
        ConnexionDao maconnexion = ConnexionDao.getInstance();
        this.utilisateurEnCours = maconnexion.getUtilisateurDao();
    }
	
    public ServletInscription() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//cookie important pour tracer la dernière page atteinte par l'utilisateur
		String adresse =request.getRequestURI();
		response.addCookie(new Cookie("last",adresse));
		//
		//
		CalculDate aujourdhui = new CalculDate();
		request.setAttribute("maintenant", aujourdhui);
		//
		//
		//
		this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//set utilisateur à partir du formulaire
		Utilisateur nouvelutilisateur=new Utilisateur();
		nouvelutilisateur.setEmail(request.getParameter("email"));
		nouvelutilisateur.setMotDePasse(request.getParameter("motDePasse"));
		nouvelutilisateur.setNom(request.getParameter("nom"));
		nouvelutilisateur.setPrenom(request.getParameter("prenom"));
		nouvelutilisateur.setPays(request.getParameter("pays"));
		nouvelutilisateur.setVille(request.getParameter("ville"));
		nouvelutilisateur.setDateNaissance(request.getParameter("dateNaissance"));
		nouvelutilisateur.setDescription("");
		//
		//
		String erreur=utilisateurEnCours.ajouter(nouvelutilisateur);
		if(erreur.equals("")==false) {
			if(erreur.contains("23505")) {
				response.sendRedirect(request.getContextPath() + "/inscription#ModalNonValidation");
			}else {
				response.sendRedirect(request.getContextPath() + "/inscription#Modalerreur");	
			}
		}else {
			//ModalValidation
			response.sendRedirect(request.getContextPath() + "/inscription#ModalValidation");
		}
	}

}
