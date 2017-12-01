<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/img/testlogo_icon2.png"/>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/menu.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/menuutilisateur.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fichierupload.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/espaceutilisateur.css" rel="stylesheet">
    <!-- système de scrollbar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	 <!-- système de scrollbar -->
	<title>Espace de ${sessionScope.utilisateurencours.prenom}</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
	</div>
	<div id="ModalValidation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Confirmation</h2>
	    </header>
	    	<p id="textvalidation"><br>Les changements ont été effectués</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="ModalNotification" class="cModal">
	  <div>
	    <header>
	    	<c:if test="${notificationClick.typeNotification!='Bilan de réservation'}">
	    		<h2 id="infossurblanc">${notificationClick.typeNotification} de la part de ${notificationClick.utilisateurExpediteur.prenom} ${notificationClick.utilisateurExpediteur.nom}</h2>
	    	</c:if>
	    	<c:if test="${notificationClick.typeNotification=='Bilan de réservation'}">
	    		<h2 id="infossurblanc">${notificationClick.typeNotification} de ${notificationClick.topoNotification.nomTopo}</h2>
	    	</c:if>
	    </header>
<!--  -->  	<c:if test="${notificationClick.typeNotification=='Demande de réservation'}">
	    		<p id="textvalidation"><br><a target="_blank" href="utilisateur?user=${notificationClick.utilisateurExpediteur.id}">${notificationClick.utilisateurExpediteur.prenom} ${notificationClick.utilisateurExpediteur.nom}</a> souhaiterait vous emprunter votre topo ${notificationClick.topoNotification.nomTopo}.
	    		<br>${notificationClick.utilisateurExpediteur.prenom} ${notificationClick.utilisateurExpediteur.nom} aurait besoin de cet ouvrage sur la période du
							<!--  -->
							<!-- Test pour récupérer les dates depuis les paramètres -->
	    		<c:forTokens items = "${notificationClick.parametreNotification}" delims = "&" var = "date">
	    			<c:if test="${!empty date1}">
	    				<c:set value="${date}" var="date2"/>
	    			</c:if>
	    			<c:if test="${empty date1}">
	    				<c:set value="${date}" var="date1"/>
	    			</c:if>
	    		</c:forTokens>
							<!-- Test pour récupérer les dates depuis les paramètres -->
							<!--  -->
	    		${date1} au ${date2}.<br>En acceptant cette demande, une notification de confirmation contenant votre adresse e-mail sera transmise à ${notificationClick.utilisateurExpediteur.prenom} ${notificationClick.utilisateurExpediteur.nom} pour qu'il puisse vous contacter et procéder à l'échange.</p>
	    		<c:if test="${notificationClick.traitementNotification=='true'}">
			    	<p><br>Vous avez accepté cette demande.</p>
			    </c:if>
			    <c:if test="${notificationClick.traitementNotification=='false'}">
			    	<p><br>Vous avez décliné cette demande.</p>
			    </c:if>
	    	</c:if>
<!--  -->  	<c:if test="${notificationClick.typeNotification=='Bilan de réservation'}">
	    		<p id="textvalidation"><br>Votre réservation de ${notificationClick.topoNotification.nomTopo} est arrivée à son terme.
	    		<br>Nous vous invitons à laisser un commentaire sur cette réservation si vous le souhaitez:</p>
	    	</c:if>
<!--  -->  	<c:if test="${notificationClick.typeNotification=='Confirmation de réservation'}">
	    		<p id="textvalidation"><br><a target="_blank" href="utilisateur?user=${notificationClick.utilisateurExpediteur.id}">${notificationClick.utilisateurExpediteur.prenom} ${notificationClick.utilisateurExpediteur.nom}</a> a accepté votre demande de réservation de ${notificationClick.topoNotification.nomTopo}.
	    		<br>Vous pouvez dès à présent le contacter à l'adresse <a href="mailto:${notificationClick.parametreNotification}">${notificationClick.parametreNotification}</a> pour réaliser l'échange.
	    		<br>Vous recevrez à la fin de la période de réservation une notification de bilan vous offrant la possibilité de laisser un commentaire.
	    	</c:if>
<!--  -->  	<c:if test="${notificationClick.typeNotification=='Annulation de réservation'}">
	    		<p id="textvalidation"><br><a target="_blank" href="utilisateur?user=${notificationClick.utilisateurExpediteur.id}">${notificationClick.utilisateurExpediteur.prenom} ${notificationClick.utilisateurExpediteur.nom}</a> n'a pas pu accepter votre demande de réservation de ${notificationClick.topoNotification.nomTopo}.
	    		<br>Proposez une autre période réservation ou contactez un autre utilisateur possédant cette topo.
	    	</c:if>
	    <footer>
	    	<form action="espaceutilisateur" method="post">
	    		<c:if test="${notificationClick.typeNotification=='Bilan de réservation'}">
	    			<c:if test="${!empty notificationClick.traitementNotification}">
			    		<p><br>Vous avez déjà posté un commentaire pour cette réservation.</p>
			   		</c:if>
			   		<c:if test="${empty notificationClick.traitementNotification}">
			   			<div>
							<textarea name="commentaire" rows="3" id="textDescription2" class="form-control" maxlength="1000" placeholder="Commentez ici..." required></textarea>
						</div>
			   		</c:if> 
				</c:if>
	    		<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    		<c:if test="${notificationClick.typeNotification=='Demande de réservation'}">
	    			<c:if test="${empty notificationClick.traitementNotification}">
			    		<input id="btnvalider" name="choix" type="submit" class="btn btn-success btn-sm" value="Accepter">
			    		<input id="btnvalider" name="choix" type="submit" class="btn btn-success btn-sm" value="Décliner">
		    		</c:if>
		    		<c:if test="${!empty notificationClick.traitementNotification}">
			    		<input id="btnvalider" name="choix" type="submit" class="btn btn-success btn-sm" value="Accepter" disabled>
			    		<input id="btnvalider" name="choix" type="submit" class="btn btn-success btn-sm" value="Décliner" disabled>
		    		</c:if>
		    	</c:if>
		    	<c:if test="${notificationClick.typeNotification=='Bilan de réservation'}">
	    			<c:if test="${empty notificationClick.traitementNotification}">
			    		<input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider">
		    		</c:if>
		    		<c:if test="${!empty notificationClick.traitementNotification}">
			    		<input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider" disabled>
		    		</c:if>
		    	</c:if>
	    	</form>
	    </footer>
	  </div>
	</div>
	<div id="Modalerreur" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Oups!</h2>
	    </header>
	    		<p id="textvalidation"><br>Une erreur est survenue. Réessayez plus tard.</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="ModalValidationAjoutTopo" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Confirmation</h2>
	    </header>
	    		<p id="textvalidation"><br>La topo a bien été ajoutée.</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="ModalerreurConfirmation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Oups!</h2>
	    </header>
	    		<p id="textvalidation"><br>La plage de date proposée est occupée par une autre réservation, la réservation sera donc annulée.</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="ModalAjoutTopo" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Ajoutez et validez vos informations</h2>
	    </header>
	    	<div>
	    		<Form class="col-xs-12" action="ajouttopo" method="post" enctype="multipart/form-data">
	    			<label>Titre topo:</label>
	    			<input type="text" class="form-control" placeholder="Titre*" name="titre" pattern="{1,50}" required>
	    			<label>Description:</label>
	    			<div class="input-group col-xs-12">
						<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
						<textarea maxlength="1000" name="descriptionTopo" rows="4" id="textDescription" class="form-control" placeholder="Ajoutez ici une description de l'ouvrage*" required></textarea>
					</div>
					<div id="buttonsupp2" class="col-xs-12">
						<label id="buttonFile" class="btn btn-default btn-sm btn-file glyphicon glyphicon-picture"><input type="file" id="fileOpen" name="fichier" style="display: none;" onchange="change1();" accept="image/*"></label>
						<label id="labelparametre2" class="label1"> Ajouter une image</label>
						<input id="fichierSelectonne" placeholder="Aucun fichier sélectionné" type="text" class="form-control" readonly>
					</div>
					<label id="labelPlusBas">Ajoutez les sites décrits par l'ouvrage:</label>
	    			<div id="table-scroll2" class="col-xs-12 mCustomScrollbar" data-mcs-theme="dark-thin">
		    			<c:forEach items="${sessionScope.allSites}" var="spot">
		    				<div id="blockCheck" class="checkbox">
								<label><input type="checkbox" name="spot${spot.idSite}"><a target="_blank" href="site?site=${spot.idSite}">${spot.nomSite}</a></label>
							</div>
		    			</c:forEach>
	    			</div>
	    			<div id="lien" class="col-xs-12">
	    				<a href="suggestions" >Pour nous faire des suggestions de sites à ajouter cliquez ici</a>
	    			</div>
				    <footer class="col-xs-12">
				    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Annuler</a>
				    	<input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider">
				    </footer>
				 </Form>
			</div>
		</div>
	  </div>
	  <div id="ModalModifImage" class="cModal">
		  <div>
		    <header>
		    		<h2 id="infossurblanc">Choisissez votre image de profil</h2>
		    </header>
		    <div>
		    		<Form class="col-xs-12" action="espaceutilisateur" method="post" enctype="multipart/form-data">
						<div id="buttonsupp2" class="col-xs-12">
							<label id="buttonFile" class="btn btn-default btn-sm btn-file glyphicon glyphicon-picture"><input type="file" id="fileOpen2" name="fichier" style="display: none;" onchange="change2();" accept="image/*"></label>
							<label id="labelparametre2" class="label1"> Ajouter une image</label>
							<input id="fichierSelectonne2" placeholder="Aucun fichier sélectionné" type="text" class="form-control" readonly>
						</div>
					    <footer class="col-xs-12">
					    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Annuler</a>
					    	<input id="btnvalider" type="submit" name="enregImage" class="btn btn-success btn-sm" value="Valider">
					    </footer>
					</Form>
			</div>
		  </div>
	</div>
	<div id="Modalmodif" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Modifez et validez vos informations</h2>
	    </header>
	    <div>
	    		<Form action="espaceutilisateur" method="post">
	    			<label>Prenom:</label>
	    			<input type="text" class="form-control" value="${sessionScope.utilisateurencours.prenom}" placeholder="Prenom*" name="prenom" pattern="[a-zA-Z-]{1,50}" required>
	    			<label>Nom:</label>
	    			<input type="text" class="form-control" value="${sessionScope.utilisateurencours.nom}" placeholder="Nom*" name="nom" pattern="[a-zA-Z-]{1,50}" required>
	    			<label>Votre description:</label>
	    			<div class="input-group col-xs-12">
						<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
						<textarea maxlength="1000" name="description" rows="2" id="textDescription" class="form-control" placeholder="Ajoutez ici quelques mots pour vous décrire...">${sessionScope.utilisateurencours.description}</textarea>
					</div>
	    			<label>Date de naissance:</label>
	    			<input type="text" class="form-control" value="${datefr}" placeholder="${maintenant.theDay}/${maintenant.theMonth}/${maintenant.theYear}" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])(/0[1-9]|/1[012])/[0-9]{4}" name="dateNaissance" required>
	    			<label>E-mail:</label>
	    			<input type="text" class="form-control" value="${sessionScope.utilisateurencours.email}" placeholder="e-mail*" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
	    			<label>Pays:</label>
	    			<input type="text" class="form-control" value="${sessionScope.utilisateurencours.pays}" placeholder="Pays" name="pays" pattern="[a-zA-Z-]{1,50}">
	    			<label>Ville:</label>
	    			<input type="text" class="form-control" value="${sessionScope.utilisateurencours.ville}" placeholder="Ville" name="ville" pattern="[a-zA-Z-]{1,50}">
				    <a id="lienMdp" href="#Modalmodifmdp" >Pour modifier votre mot de passe cliquez ici</a>
				    <footer>
				    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Annuler</a>
				    	<input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider">
				    </footer>
				 </Form>
		</div>
	  </div>
	</div>
	<div id="Modalmodifmdp" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Modifez et validez vos informations</h2>
	    </header>
	    <div>
	    	<Form action="espaceutilisateur" method="post">
	    		<label>Mot de passe actuel:</label>
	    		<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					<input type="password" class="form-control" placeholder="mot de passe actuel*" name="motDePasseA"  pattern="[a-zA-Z0-9._%+-]{7,20}" required>
				</div>
	    		<label>Nouveau mot de passe: (de 7 à 20 caractères)</label>
	    		<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					<input type="password" class="form-control" placeholder="nouveau mot de passe*" name="motDePasseN"  pattern="[a-zA-Z0-9._%+-]{7,20}" required>
				</div>
	    		<label>Confirmez le nouveau mot de passe:</label>
	    		<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					<input type="password" class="form-control" placeholder="nouveau mot de passe*" name="motDePasseN2"  pattern="[a-zA-Z0-9._%+-]{7,20}" required>
				</div>
				<footer>
				    <a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Annuler</a>
				    <input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider">
				</footer>
			</Form>
		</div>
	  </div>
	</div>
	<div class="container-fluid">
			<!-- imgPath -->
			<c:if test="${empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url(${pageContext.request.contextPath}/resources/img/user2.jpg) center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)">
					<a href="#ModalModifImage"><img id="cadrephoto" class="photo" style="background: center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0)"></a>
				</div>
			</c:if>
			<c:if test="${!empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url('${imgPath}') center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)">
					<a href="#ModalModifImage"><img id="cadrephoto" class="photo" style="background: center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0)"></a>
				</div>
			</c:if>
			<!-- imgPath -->
			<div id="blockTransparent"></div>
			<div id="presentation">
				<h2 id="infossurnoir">${sessionScope.utilisateurencours.prenom} ${sessionScope.utilisateurencours.nom}</h2>
				<c:if test="${age!='Indéterminé'}">
					<h4 id="infossurnoir">${age} ans</h4>
				</c:if>
				<c:if test="${age=='Indéterminé'}">
					<h4 id="infossurnoir">Âge ${age}</h4>
				</c:if>
				<h5 id="infossurnoir">${sessionScope.utilisateurencours.email}</h5>
				<h5 id="infossurnoir">${sessionScope.utilisateurencours.pays}</h5>
				<h5 id="infossurnoir">${sessionScope.utilisateurencours.ville}</h5>
				<a id="reglage" class="btn btn-default btn-sm" title="Paramètres" href="#Modalmodif"><span class="glyphicon glyphicon-cog"></span></a>
				<label id="labelparametre" class="label1"> Modifier les infos</label>
			</div>
			<form id="blockdescription" action="espaceutilisateur" method="post" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Votre description:</legend>
					<div class="input-group col-xs-12">
						<textarea readonly name="description" rows="2" id="textDescription0" class="form-control" maxlength="1000" placeholder="Quelques mots pour vous décrire...">${sessionScope.utilisateurencours.description}</textarea>
					</div>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Vos notifications: <c:if test="${sessionScope.nouvellesNotifications>0}"><span class="badge">${sessionScope.nouvellesNotifications}</span></c:if></legend>
						<c:if test="${!empty sessionScope.notificationDelUtilisateur}">
							<div id="ligneheader" class="col-xs-12">
								<div id="contenuheader" class="row">
									<label id="infossurnoirheader" class="col-xs-6">De la part de</label>
									<label id="infossurnoirheader" class="col-xs-6">Sujet</label>
								</div>
							</div>
								<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
										<c:forEach items="${sessionScope.notificationDelUtilisateur}" var="notification">
									    		<div id="ligne" class="row">
									    				<c:if test="${empty notification.utilisateurExpediteur.prenom||empty notification.utilisateurExpediteur.nom}">
									    					<p id="petittextsurnoir"  class="col-xs-6">Automatique</p>
									    				</c:if>
									    				<c:if test="${!empty notification.utilisateurExpediteur.prenom&&!empty notification.utilisateurExpediteur.nom}">
									    					<p id="petittextsurnoir"  class="col-xs-6"><a target="_blank" href="utilisateur?user=${notification.utilisateurExpediteur.id}" >${notification.utilisateurExpediteur.prenom} ${notification.utilisateurExpediteur.nom}</a></p>
									    				</c:if>
											        	<p id="petittextsurnoir"  class="col-xs-6"><a href="espaceutilisateur?mess=${notification.idNotification}#ModalNotification" >${notification.typeNotification} </a><c:if test="${empty notification.traitementNotification}"><span class="badge">1</span></c:if></p>
											    </div>
										</c:forEach>
								</div>
						</c:if>
						<c:if test="${empty sessionScope.notificationDelUtilisateur}">
							<h5 id="infossurnoir">Vous n'avez aucune notification pour le moment.</h5>
						</c:if>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Vos Topos:</legend>
					<c:if test="${!empty sessionScope.topoDelUtilisateur}">
						<div id="ligneheader" class="col-xs-12">
							<div id="contenuheader" class="row">
								<label id="infossurnoirheader" class="col-xs-6">Titre des topos</label>
								<label id="infossurnoirheader" class="col-xs-6">Disponibilité</label>
							</div>
						</div>
							<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
								<c:forEach items="${sessionScope.topoDelUtilisateur}" var="topo">
								    		<div id="ligne" class="row">
										        	<p id="petittextsurnoir"  class="col-xs-6"><a target="_blank" href="topo?topo=${topo.idTopo}" >${topo.nomTopo}</a></p>
										        	<c:if test="${topo.dispoTopo==true}">
										        		<p id="petittextsurnoir"  class="col-xs-6">Disponible</p>
										        	</c:if>
										        	<c:if test="${topo.dispoTopo==false}">
										        		<p id="petittextsurnoir"  class="col-xs-6">Non-disponible</p>
										        	</c:if>
										    </div>
								</c:forEach>
							</div>
					</c:if>
					<c:if test="${empty sessionScope.topoDelUtilisateur}">
							<h5 id="infossurnoir">Vous ne possédez pas de topo pour le moment.</h5>
					</c:if>
					<div id="buttonsupp" class="col-xs-12">
						<a id="reglage" class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/ajouttopo"><span class="glyphicon glyphicon-plus"></span></a>
						<label id="labelparametre" class="label1"> Ajouter un topo</label>
					</div>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Vos réservations:</legend>
					<c:if test="${!empty sessionScope.reservationDelUtilisateur}">
						<div id="ligneheader" class="col-xs-12">
							<div id="contenuheader" class="row">
								<label id="infossurnoirheader" class="col-xs-3">Titre des topos</label>
								<label id="infossurnoirheader" class="col-xs-3">Date de début</label>
								<label id="infossurnoirheader" class="col-xs-3">Date de fin</label>
								<label id="infossurnoirheader" class="col-xs-3">Propriétaire</label>
							</div>
						</div>
							<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
								<c:forEach items="${sessionScope.reservationDelUtilisateur}" var="reservation">
								    		<div id="ligne" class="row">
										        	<p id="petittextsurnoir"  class="col-xs-3"><a target="_blank" href="topo?topo=${reservation.topoAssocié.idTopo}" >${reservation.topoAssocié.nomTopo}</a></p>
										        	<p id="petittextsurnoir"  class="col-xs-3">${reservation.datedebutReservationFR}</p>
										        	<p id="petittextsurnoir"  class="col-xs-3">${reservation.datefinReservationFR}</p>
													<p id="petittextsurnoir"  class="col-xs-3"><a target="_blank" href="utilisateur?user=${reservation.proprietaireAssocié.id}" >${reservation.proprietaireAssocié.prenom} ${reservation.proprietaireAssocié.nom}</a></p>
										    </div>
								</c:forEach>
							</div>
					</c:if>
					<c:if test="${empty sessionScope.reservationDelUtilisateur}">
							<h5 id="infossurnoir">Vous n'avez fait aucune réservation pour le moment.</h5>
					</c:if>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Vos commentaires:</legend>
					<div id="ligneheader" class="col-xs-12">
						<div id="contenuheader" class="row">
							<label id="infossurnoirheader" class="col-xs-4">Objet du commentaire</label>
							<label id="infossurnoirheader" class="col-xs-3">Date</label>
							<label id="infossurnoirheader" class="col-xs-5">Contenu</label>
						</div>
					</div>
						<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
							<c:forEach items="${sessionScope.reservationDelUtilisateur}" var="reservation">
							    <c:if test="${!empty reservation.commentaireReservation}">
							    		<div id="ligne" class="row">
									        	<p id="petittextsurnoir"  class="col-xs-4">Réservation de <a target="_blank" href="topo?topo=${reservation.topoAssocié.idTopo}" >${reservation.topoAssocié.nomTopo}</a></p>
									        	<p id="petittextsurnoir"  class="col-xs-3">de ${reservation.datedebutReservationFR} à ${reservation.datefinReservationFR}</p>
									        	<textarea readonly rows="2" id="textDescription1" maxlength="1000" >${reservation.commentaireReservation}</textarea>
									    </div>
								</c:if>
							</c:forEach>
							<c:forEach items="${sessionScope.commentairesDelUtilisateur}" var="commentaire">
								    	<div id="ligne" class="row">
								        	<p  class="col-xs-4" id="petittextsurnoir"><a target="_blank" href="site?site=${commentaire.siteCommentaire.idSite}" >${commentaire.siteCommentaire.nomSite}</a></p>
								        	<p  class="col-xs-3" id="petittextsurnoir">${commentaire.dateCommentaireFR}</p>
								        	<textarea readonly rows="2" id="textDescription1" maxlength="1000" >${commentaire.contenuCommentaire}</textarea>
								      	</div>
							</c:forEach>
						</div>
				</div>
			</form>
	</div>
</body>
</html>