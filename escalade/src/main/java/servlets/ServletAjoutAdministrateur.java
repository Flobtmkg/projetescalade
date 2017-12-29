package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classestravail.Photo;
import factorydao.ConnexionDao;
import interfacesdao.PhotoSecteurDao;
import interfacesdao.PhotoSiteDao;
import interfacesdao.PhotoVoieDao;
import interfacesdao.SecteurDao;
import interfacesdao.SiteDao;
import interfacesdao.VoieDao;
import javabeans.Secteur;
import javabeans.Site;
import javabeans.Voie;


public class ServletAjoutAdministrateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//
	ConnexionDao maconnexion;
	SiteDao accesBddSites;
	SecteurDao accesBddSecteur;
	VoieDao accesBddVoie;
	PhotoSiteDao accesBddPhotoSite;
	PhotoSecteurDao accesBddPhotoSecteur;
	PhotoVoieDao accesBddPhotoVoie;
    
	public void init() throws ServletException {
        maconnexion = ConnexionDao.getInstance(getServletContext().getInitParameter("postgresqlUsername"),getServletContext().getInitParameter("postgresqlPassword"));
        this.accesBddSites = maconnexion.getSiteDao();
        this.accesBddSecteur=maconnexion.getSecteurDao();
        this.accesBddPhotoSite=maconnexion.getPhotoSiteDao();
        this.accesBddPhotoSecteur=maconnexion.getPhotoSecteurDao();
        this.accesBddVoie=maconnexion.getVoieDao();
        this.accesBddPhotoVoie=maconnexion.getPhotoVoieDao();
    }

    public ServletAjoutAdministrateur() {
        super();
        //
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String admin=(String)session.getAttribute("Admin");
		if(admin==null) {
			response.sendRedirect(request.getContextPath() + "/accueil");
		}else if(admin.equals("true")==false) {
			response.sendRedirect(request.getContextPath() + "/accueil");
		}else {
			//Liste de tout les sites
			ArrayList<Site> listeDesSites=new ArrayList<Site>();
			ArrayList<Integer> toutLesSites= new ArrayList<Integer>();
			toutLesSites.add(0);//une liste contenant un 0 à [0] pour signifier tout les sites
			listeDesSites=accesBddSites.trouverListeSite(toutLesSites);
			//Liste de tout les secteurs
			ArrayList<Secteur> listeDesSecteurs=new ArrayList<Secteur>();
			listeDesSecteurs=accesBddSecteur.toutLesSecteurs();
			//
			request.setAttribute("Sites", listeDesSites);
			request.setAttribute("Secteurs", listeDesSecteurs);
			//
			this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutadministrateur.jsp").forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		request.setCharacterEncoding("UTF-8");
		//
		String typeAjout=request.getParameter("typedAjout");
		try {
			if(typeAjout.equals("Ajouter site")) {
				//
				Site nouveauSite=new Site();
				nouveauSite.setNomSite(request.getParameter("nomSite"));
				nouveauSite.setPaysSite(request.getParameter("paysSite"));
				nouveauSite.setVilleSite(request.getParameter("villeSite"));
				nouveauSite.setLatitudeSite(Double.valueOf(request.getParameter("latitudeSite")));
				nouveauSite.setLongitudeSite(Double.valueOf(request.getParameter("longitudeSite")));
				nouveauSite.setDescriptionSite(request.getParameter("descriptionSite"));
				accesBddSites.ajouter(nouveauSite);
				//trouver l'id du dernier ajout
				int idDuSite=accesBddSites.trouverLastIDSite();
				//Ajout de la photo
				if (request.getPart("fichier").getSize()!=0) {
					String nomFichier=Photo.ajout(request.getPart("fichier"), getServletContext().getInitParameter("filePath"), idDuSite);
					accesBddPhotoSite.ajoutPhoto(idDuSite, nomFichier);
				}
				
				//
				response.sendRedirect(request.getContextPath() + "/ajoutadministrateur#ModalValidation");
				//
			}else if(typeAjout.equals("Ajouter secteur")) {
				//
				Secteur nouveauSecteur=new Secteur();
				//récupérer id du site associé
				//
				String descripteurSite=request.getParameter("siteattache");
				descripteurSite=descripteurSite.split(":")[0];//le descripteur site deviens un idSite
				//
				nouveauSecteur.setIdSite(Integer.valueOf(descripteurSite));
				nouveauSecteur.setNomSecteur(request.getParameter("nomSecteur"));
				nouveauSecteur.setDescriptionSecteur(request.getParameter("descriptionSecteur"));
				nouveauSecteur.setHauteurSecteur(Double.valueOf(request.getParameter("hauteurSecteur")));
				accesBddSecteur.ajouter(nouveauSecteur);
				//trouver l'id du dernier ajout
				int idDuSecteur=accesBddSecteur.trouverLastIDSite();
				//Ajout de la photo
				if (request.getPart("fichier").getSize()!=0) {
					String nomFichier=Photo.ajout(request.getPart("fichier"), getServletContext().getInitParameter("filePath"), idDuSecteur);
					accesBddPhotoSecteur.ajoutPhoto(idDuSecteur, nomFichier);
				}
				//
				response.sendRedirect(request.getContextPath() + "/ajoutadministrateur#ModalValidation");
				//
			}else if(typeAjout.equals("Ajouter voie")) {
				//
				Voie nouvelleVoie=new Voie();
				//récupérer id du secteur associé
				//
				String descripteurSecteur=request.getParameter("secteurattache");
				descripteurSecteur=descripteurSecteur.split(":")[0];//le descripteur secteur deviens un idSecteur
				//
				nouvelleVoie.setIdSecteur(Integer.valueOf(descripteurSecteur));
				nouvelleVoie.setNomVoie(request.getParameter("nomVoie"));
				nouvelleVoie.setDescriptionVoie(request.getParameter("descriptionVoie"));
				//
				String [] listCotation=new String[8];
				listCotation[0]=request.getParameter("cotationVoie1").toLowerCase(new Locale("FRENCH"));
				listCotation[1]=request.getParameter("cotationVoie2").toLowerCase(new Locale("FRENCH"));
				listCotation[2]=request.getParameter("cotationVoie3").toLowerCase(new Locale("FRENCH"));
				listCotation[3]=request.getParameter("cotationVoie4").toLowerCase(new Locale("FRENCH"));
				listCotation[4]=request.getParameter("cotationVoie5").toLowerCase(new Locale("FRENCH"));
				listCotation[5]=request.getParameter("cotationVoie6").toLowerCase(new Locale("FRENCH"));
				listCotation[6]=request.getParameter("cotationVoie7").toLowerCase(new Locale("FRENCH"));
				listCotation[7]=request.getParameter("cotationVoie8").toLowerCase(new Locale("FRENCH"));
				nouvelleVoie.setQuotationVoie(getCotation(listCotation)); //on récupère les cotations mises en forme
				//
				if(request.getParameter("equipementVoie").equals("Voie équipée")) {
					nouvelleVoie.setEquipementVoie(true);
				}else {
					nouvelleVoie.setEquipementVoie(false);
				}
				accesBddVoie.ajouter(nouvelleVoie);
				//trouver l'id du dernier ajout
				int idDeLaVoie=accesBddVoie.trouverLastIDVoie();
				//Ajout de la photo
				if (request.getPart("fichier").getSize()!=0) {
					String nomFichier=Photo.ajout(request.getPart("fichier"), getServletContext().getInitParameter("filePath"), idDeLaVoie);
					accesBddPhotoVoie.ajoutPhoto(idDeLaVoie, nomFichier);
				}
				//
				response.sendRedirect(request.getContextPath() + "/ajoutadministrateur#ModalValidation");
			}
		}catch(Exception e) {
			response.sendRedirect(request.getContextPath() + "/ajoutadministrateur#ModalErreur");
		}
		
		//
		
	}
	
	//
	private String getCotation(String[] inputValeurs) {
		String Cotations="";
		for(String cot:inputValeurs) {
			if(cot.equals("")==false) {
				Cotations=Cotations+cot+"/";
			}
		}
		Cotations=Cotations.substring(0, Cotations.length()-1);
		return Cotations;
	}
	//
}
