package projetescalade;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javabeans.Commentaire;
import javabeans.PhotoTopo;
import javabeans.Reservation;
import javabeans.Site;
import javabeans.Topo;
import javabeans.Utilisateur;


public class ServletSite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao accesBddUtilisateur;
	private TopoDao accesBddTopo;
	private ReservationDao accesBddReservation;
	private SiteDao accesBddSite;
	private TopoSitesDao accesBddTopoSite;
	private NotificationDao accesBddNotification;
	private PhotoTopoDao accesBddPhotoTopo;
	private CommentaireDao accesBddCommentaire;
	//
	private ConnexionDao maconnexion;
	//
	private int id;
	private CalculDate aujourdhui;
	//
	
	public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance();
        this.accesBddUtilisateur = maconnexion.getUtilisateurDao();
        this.accesBddTopo=maconnexion.getTopoDao();
        this.accesBddReservation=maconnexion.getReservationDao();
        this.accesBddSite=maconnexion.getSiteDao();
        this.accesBddTopoSite=maconnexion.getTopoSitesDao();
        this.accesBddNotification=maconnexion.getNotificationDao();
        this.accesBddPhotoTopo=maconnexion.getPhotoTopoDao();
        this.accesBddCommentaire=maconnexion.getCommentaireDao();
    }

    public ServletSite() {
        super();
        //
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//
		id = 0;
		try {
			id =Integer.valueOf(request.getParameter("site"));
		}catch(Exception e) {
			
		}
		
		if(id!=0) {
			//Récupération des informations Site
			Site siteAAfficher=accesBddSite.trouverSite(id);
			if(siteAAfficher.getIdSite()!=0) {
				//
				//cookie important pour tracer la dernière page atteinte par l'utilisateur
				String adresse =request.getRequestURI()+"?site="+id;
				response.addCookie(new Cookie("last",adresse));
				//
				//Récupération des topo concernés par les sites et leur propriétaire
				ArrayList<Integer> idDesTopos=accesBddTopoSite.trouverTopoConcerneParSite(id);
				ArrayList<Topo> topoConcernes=accesBddTopo.trouverTopo(idDesTopos);
				ArrayList<Utilisateur> listProprietaires=accesBddUtilisateur.trouverProprietaireTopo(topoConcernes);
				//On assigne les propriétaires aux topos
				for(Topo top:topoConcernes) {
					for(Utilisateur prop:listProprietaires) {
						if(top.getIdProprietaire()==prop.getId()) {
							top.setProprietaire(prop);
						}
					}
				}
				//Récupération des commentaires du Site et des utilisateurs ayant commenté
				ArrayList<Commentaire> commentaireDuSite = accesBddCommentaire.trouverCommentairesParSite(id);
				for(Commentaire comm:commentaireDuSite) {
					if(comm.getContenuCommentaire()!=null && comm.getContenuCommentaire().equals("")==false) {
							Utilisateur commentateur = accesBddUtilisateur.trouver(comm.getIdUtilisateur());
							comm.setCommentateur(commentateur);
						if(comm.getIdUtilisateur()==0) {
							Utilisateur commentateur2= new Utilisateur(0,"","","","","","","","");
							commentateur2.setPrenom("Anonyme");
							comm.setCommentateur(commentateur2);
						}
						
					}
				}
				//
				commentaireDuSite.sort(null);
				//request.setAttribute("imgPath", imgPath);
				request.setAttribute("maintenant", aujourdhui);
				request.setAttribute("toposconcernes", topoConcernes);
				request.setAttribute("commentaireDuSite", commentaireDuSite);;
				request.setAttribute("sitefound", siteAAfficher);
				this.getServletContext().getRequestDispatcher("/WEB-INF/site.jsp").forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + fromWho);
			}
		}else {
			response.sendRedirect(request.getContextPath() + fromWho);
		}
		//
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		HttpSession sessionEnCours=request.getSession();
		Utilisateur utilisateurConnecte=(Utilisateur)sessionEnCours.getAttribute("utilisateurencours");
		//
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//
		String contenuDuCommentaire=request.getParameter("commentaire");
		if(contenuDuCommentaire!=null) {
			//
			aujourdhui=new CalculDate();
			//
			Commentaire newCommentaire = new Commentaire();
			newCommentaire.setContenuCommentaire(contenuDuCommentaire);
			newCommentaire.setDateCommentaire(aujourdhui.getTheDate().toString());
			newCommentaire.setIdSite(id);
			//i.p.
			String ip = request.getRemoteAddr();
			if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			    InetAddress inetAddress = InetAddress.getLocalHost();
			    String ipAddress = inetAddress.getHostAddress();
			    ip = ipAddress;
			}
			newCommentaire.setIpCommentaire(ip);
			if(utilisateurConnecte!=null) {
				newCommentaire.setIdUtilisateur(utilisateurConnecte.getId());
			}else {
				newCommentaire.setIdUtilisateur(0);//anonyme
			}
			accesBddCommentaire.ajouterCommentaire(newCommentaire);
			//
		}
		//
		response.sendRedirect(request.getContextPath() + fromWho);
		//
	}

}
