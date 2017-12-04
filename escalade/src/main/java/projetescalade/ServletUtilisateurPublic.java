package projetescalade;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabeans.Commentaire;
import javabeans.Reservation;
import javabeans.Topo;
import javabeans.Utilisateur;


public class ServletUtilisateurPublic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao accesBddUtilisateur;
	private TopoDao accesBddTopo;
	private ReservationDao accesBddReservation;
	private CommentaireDao accesBddCommentaire;
	private SiteDao accesBddSite;
	//
	ConnexionDao maconnexion;
	//

    public ServletUtilisateurPublic() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
        this.accesBddUtilisateur = maconnexion.getUtilisateurDao();
        this.accesBddTopo=maconnexion.getTopoDao();
        this.accesBddReservation=maconnexion.getReservationDao();
        this.accesBddCommentaire=maconnexion.getCommentaireDao();
        this.accesBddSite=maconnexion.getSiteDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//
		int id = 0;
		try {
			id =Integer.valueOf(request.getParameter("user"));
		}catch(Exception e) {
			
		}
		
		if(id!=0) {
			//Récupération des informations utilisateur
			Utilisateur utilisateurAAfficher=accesBddUtilisateur.trouver(id);
			if(utilisateurAAfficher.getId()!=0) {
				//
				//cookie important pour tracer la dernière page atteinte par l'utilisateur
				String adresse =request.getRequestURI()+"?user="+id;
				response.addCookie(new Cookie("last",adresse));
				//
				//
				//Récupération date et l'age de l'utilisateur
				CalculDate aujourdhui = new CalculDate();
				request.setAttribute("maintenant", aujourdhui);
				//
				String annees;
				if(utilisateurAAfficher.getDateNaissance().equals("0001-01-01")==false) {
					annees=String.valueOf(aujourdhui.diff(LocalDate.parse(utilisateurAAfficher.getDateNaissance())));
				}else {
					annees="Indéterminé";
				}
				request.setAttribute("age", annees);
				//
				//Récupération des informations topos liées à l'utilisateur
				ArrayList<Topo> topoAAfficher=accesBddTopo.trouverToposParUtilisateur(utilisateurAAfficher.getId());
				//Recherche de diponibilité
				for(Topo top:topoAAfficher) {
					DisponibiliteTopo dispotopo= new DisponibiliteTopo(maconnexion);
					top.setDispoTopo(dispotopo.isDisponible(top.getIdTopo()));
				}
				//
				//Récupération des commentaires de réservation
				ArrayList<Reservation> reservationsAAfficher=accesBddReservation.getReservationUtilisateur(utilisateurAAfficher.getId());
				for(Reservation res:reservationsAAfficher) {
					res.setTopoAssocié(accesBddTopo.trouverTopo(res.getIdTopo()));
				}
				//Récupération des commentaires de sites
				ArrayList<Commentaire> commentairesAAfficher=accesBddCommentaire.trouverCommentairesParUtilisateur(utilisateurAAfficher.getId());
				for(Commentaire comm:commentairesAAfficher) {
					comm.setSiteCommentaire(accesBddSite.trouverSite(comm.getIdSite()));
				}
				//
				request.setAttribute("commentairesfound", commentairesAAfficher);
				request.setAttribute("reservationsfound", reservationsAAfficher);
				request.setAttribute("toposfound", topoAAfficher);
				request.setAttribute("utilisateurfound", utilisateurAAfficher);
				this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateur.jsp").forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + fromWho);
			}
		}else {
			response.sendRedirect(request.getContextPath() + fromWho);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
