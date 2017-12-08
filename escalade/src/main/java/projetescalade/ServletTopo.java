package projetescalade;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javabeans.Notification;
import javabeans.PhotoTopo;
import javabeans.Reservation;
import javabeans.Site;
import javabeans.Topo;
import javabeans.Utilisateur;

public class ServletTopo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurDao accesBddUtilisateur;
	private TopoDao accesBddTopo;
	private ReservationDao accesBddReservation;
	private SiteDao accesBddSite;
	private TopoSitesDao accesBddTopoSite;
	private NotificationDao accesBddNotification;
	private PhotoTopoDao accesBddPhotoTopo;
	//
	private ConnexionDao maconnexion;
	//
	private int id;
	private Utilisateur proprietaire;
	private CalculDate aujourdhui;
	//
	
	public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
        this.accesBddUtilisateur = maconnexion.getUtilisateurDao();
        this.accesBddTopo=maconnexion.getTopoDao();
        this.accesBddReservation=maconnexion.getReservationDao();
        this.accesBddSite=maconnexion.getSiteDao();
        this.accesBddTopoSite=maconnexion.getTopoSitesDao();
        this.accesBddNotification=maconnexion.getNotificationDao();
        this.accesBddPhotoTopo=maconnexion.getPhotoTopoDao();
    }
	
    public ServletTopo() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		//
		String fromWho=CookieGetter.getCookiePagePrecedente(request);
		//
		String err="";
		id = 0;
		try {
			id =Integer.valueOf(request.getParameter("topo"));
			err =request.getParameter("err");
		}catch(Exception e) {
			
		}
		
		if(id!=0) {
			//Récupération des informations Topo
			Topo topoAAfficher=accesBddTopo.trouverTopo(id);
			if(topoAAfficher.getIdTopo()!=0) {
				//
				//cookie important pour tracer la dernière page atteinte par l'utilisateur
				String adresse =request.getRequestURI()+"?topo="+id;
				response.addCookie(new Cookie("last",adresse));
				//
				//Récupération adress img
				PhotoTopo relationPhotoTopo= accesBddPhotoTopo.getPhotoPath(id);
				//String hostURL="http://"+request.getServerName() + ":" + request.getServerPort()+"/escalade/img/";
				String imgPath=null;
				if(relationPhotoTopo.getPathPhoto()!=null) {
					imgPath="/escalade/img/"+relationPhotoTopo.getPathPhoto();
				}
				//
				//Récupération date
				aujourdhui = new CalculDate();
				//
				//Récupération des informations du propriétaire du topo
				proprietaire =accesBddUtilisateur.trouver(topoAAfficher.getIdProprietaire());
				//Récupération des sites concernés par le topo
				ArrayList<Integer> idDesSites=accesBddTopoSite.trouverSitesConcerneParTopo(topoAAfficher.getIdTopo());
				ArrayList<Site> sitesConcernes=accesBddSite.trouverListeSite(idDesSites);
				//Récupération des commentaires de réservation de ce topo et des utilisateurs ayant commenté
				ArrayList<Reservation> reservationsDuTopo =accesBddReservation.getReservationDuTopo(topoAAfficher.getIdTopo());
				ArrayList<Reservation> listeNonDisponible= new ArrayList<Reservation>();
				for(Reservation res:reservationsDuTopo) {
					if(aujourdhui.getTheDate().isBefore(LocalDate.parse(res.getDatefinReservation()))||aujourdhui.getTheDate().isEqual(LocalDate.parse(res.getDatefinReservation()))) {
						listeNonDisponible.add(res);
					}
					
				}
				//Nombre de réservations
				int nbrReservations=reservationsDuTopo.size();
				if(nbrReservations==0) {
					reservationsDuTopo=null;
				}
				//Disponibilité du topo
				DisponibiliteTopo dispotopo= new DisponibiliteTopo(maconnexion);
				if(dispotopo.isDisponible(topoAAfficher.getIdTopo())) {
					topoAAfficher.setDispoTopo(true);
					if(reservationsDuTopo!=null) {
						//definir si c'est la fin d'ne periode de réservation et envoyer une notification de bilan
						BilanReservation bilan=new BilanReservation(maconnexion);
						ArrayList<Reservation> Reservationlist =new ArrayList<Reservation>();
						Reservationlist.addAll(reservationsDuTopo);
						Reservationlist.sort(null);
						bilan.bilanDeFinDeReservation(Reservationlist.get(0),proprietaire);
					//
					}
				}
				//
				request.setAttribute("imgPath", imgPath);
				request.setAttribute("err", err);
				request.setAttribute("maintenant", aujourdhui);
				request.setAttribute("nbrReservations", nbrReservations);
				request.setAttribute("listeNonDisponible", listeNonDisponible);
				request.setAttribute("sitesconcernes", sitesConcernes);
				request.setAttribute("reservationsdutopo", reservationsDuTopo);
				request.setAttribute("proprietairefound", proprietaire);
				request.setAttribute("topofound", topoAAfficher);
				this.getServletContext().getRequestDispatcher("/WEB-INF/topo.jsp").forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath() + fromWho);
			}
		}else {
			response.sendRedirect(request.getContextPath() + fromWho);
		}
		//
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// demande de réservation
		//
		Notification nouvelleNotification= new Notification();
		nouvelleNotification.setIdTopo(id);
		nouvelleNotification.setIdUtilisateurDestinataire(proprietaire.getId());
		Utilisateur expediteur=(Utilisateur)request.getSession().getAttribute("utilisateurencours");
		nouvelleNotification.setIdUtilisateurExpediteur(expediteur.getId());
		nouvelleNotification.setTypeNotification("Demande de réservation");
		//Test de validité des dates
		String erreur1="";
		String erreur2="";
		try {
			LocalDate dateATester1;
			LocalDate dateATester2;
			dateATester1=LocalDate.parse(CalculDate.conversionFormatEn(request.getParameter("dateDebut")));
			dateATester2=LocalDate.parse(CalculDate.conversionFormatEn(request.getParameter("dateFin")));
			if(dateATester1.isBefore(aujourdhui.getTheDate())||dateATester2.isBefore(aujourdhui.getTheDate())||dateATester2.isBefore(dateATester1)) {
				//erreur
				erreur1="ChronologyError";
			}
		}catch(Exception e) {
			erreur1="ConversionError";
		}
		if(erreur1.equals("")==false) {
			//erreur
			response.sendRedirect(request.getContextPath() + "/topo?topo="+id+"&err="+erreur1+"#Modalerreur");
		}else {
			//vérification de la plage de date!!!
			DisponibiliteTopo dispoTopo =new DisponibiliteTopo(maconnexion);
			if(dispoTopo.isDisponible(id, request.getParameter("dateDebut"), request.getParameter("dateFin"))) {
				nouvelleNotification.setParametreNotification(request.getParameter("dateDebut") + "&" + request.getParameter("dateFin"));
				erreur2=accesBddNotification.ajouter(nouvelleNotification);
					
			}else {
				erreur2="DatePeriodError";
			}
				
			if(erreur2.equals("")==false) {
				response.sendRedirect(request.getContextPath() + "/topo?topo="+id+"&err="+erreur2+"#Modalerreur");
			}else {
				//ModalValidation
				response.sendRedirect(request.getContextPath() + "/topo?topo="+id+"#ModalValidation");
			}	
			
		}
		
	}

}
