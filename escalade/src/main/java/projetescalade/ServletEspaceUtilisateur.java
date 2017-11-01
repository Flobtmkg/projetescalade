package projetescalade;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Utilisateur;

public class ServletEspaceUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao utilisateurEnCours;

	public void init() throws ServletException {
        ConnexionDao maconnexion = ConnexionDao.getInstance();
        this.utilisateurEnCours = maconnexion.getUtilisateurDao();
    }
	
    public ServletEspaceUtilisateur() {
        super();
        //
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//Pas de connexion utilisateur -> pas d'accès possible
		//
		HttpSession sessionEnCours=request.getSession();
		Utilisateur utilisateurConnecte=(Utilisateur) sessionEnCours.getAttribute("utilisateurencours");
		if(sessionEnCours.getAttribute("utilisateurencours")!=null) {
				//
				//cookie important pour tracer la dernière page atteinte par l'utilisateur
				String adresse =request.getRequestURI();
				response.addCookie(new Cookie("last",adresse));
				//
				//Récupération de l'age de l'utilisateur
				String annees;
				if(utilisateurConnecte.getDateNaissance().equals("0001-01-01")==false) {
					CalculDate aujourdhui = new CalculDate();
					annees=String.valueOf(aujourdhui.diff(LocalDate.parse(utilisateurConnecte.getDateNaissance())));
					//
				}else {
					annees="Indéterminé";
				}
				request.setAttribute("age", annees);
				//
				//
				this.getServletContext().getRequestDispatcher("/WEB-INF/espaceutilisateur.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Ajout de la description à l'utilisateur en cours et à la base
		HttpSession sessionEnCours=request.getSession();
		Utilisateur utilisateurConnecte=(Utilisateur) sessionEnCours.getAttribute("utilisateurencours");
		utilisateurConnecte.setDescription(request.getParameter("description"));
		sessionEnCours.setAttribute("utilisateurencours", utilisateurConnecte);
		String erreurs=utilisateurEnCours.modifier("descriptionutilisateur", utilisateurConnecte.getDescription(), "idutilisateur", utilisateurConnecte.getId(), false, false);
		if(erreurs.equals("")) {
			response.sendRedirect(request.getContextPath() + "/espaceutilisateur#ModalValidation");
		}else {
			response.sendRedirect(request.getContextPath() + "/espaceutilisateur#Modalerreur");
		}
	}

}
