<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/img/testlogo_icon2.png"/>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/menu.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/menuutilisateur.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/topo.css" rel="stylesheet">
    <!-- système de scrollbar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	 <!-- système de scrollbar -->
	<title>Descriptif de ${topofound.nomTopo}</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
	</div>
	<div id="Modalreservation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Réservation</h2>
	    </header>
	    		<p id="textvalidation">
	    			<br>Vous souhaitez réserver ${topofound.nomTopo} dont le propriétaire est ${proprietairefound.prenom} ${proprietairefound.nom}. Veuillez proposer des dates de réservation.</p>
	    		<div>
	    		<Form action="topo" method="post">
	    			<label>Début de réservation: (format JJ/MM/AAAA)</label>
	    			<input type="text" class="form-control" placeholder="${maintenant.theDay}/${maintenant.theMonth}/${maintenant.theYear}" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])(/0[1-9]|/1[012])/[0-9]{4}" name="dateDebut" required>
	    			<label>Fin de réservation: (format JJ/MM/AAAA)</label>
	    			<input type="text" class="form-control" placeholder="${maintenant.theDay}/${maintenant.theMonth}/${maintenant.theYear}" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])(/0[1-9]|/1[012])/[0-9]{4}" name="dateFin" required>
				    <footer>
				    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Annuler</a>
				    	<input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider">
				    </footer>
				 </Form>
				</div>
	  </div>
	</div>
	<div id="Modalerreur" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Oups!</h2>
	    </header>
	    		<c:choose>
	    			<c:when test="${err=='ChronologyError'}">
	    				<p id="textvalidation"><br>Une erreur à été détectée dans la chronologie des dates que vous avez proposé.</p>
	    			</c:when>
	    			<c:when test="${err=='ConversionError'}">
	    				<p id="textvalidation"><br>Une erreur à été détectée lors de la vérification du calendrier des dates que vous avez indiqué.</p>
	    			</c:when>
	    			<c:when test="${err=='DatePeriodError'}">
	    				<p id="textvalidation"><br>Une réservation effectuée par un autre utilisateur à déjà été programmé sur toute ou partie de la période que vous avez proposé. <br>Réesayez en proposant un autre créneau.</p>
	    			</c:when>
	    		</c:choose>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="ModalValidation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Confirmation</h2>
	    </header>
	    		<p id="textvalidation"><br>Une notification de réservation à été envoyé à ${proprietairefound.prenom} ${proprietairefound.nom}.
	    		<br>Si celui-ci accepte, vous recevrez en retour une notification incluant les coordonnées de ${proprietairefound.prenom} ${proprietairefound.nom} pour réaliser l'échange.</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div class="container-fluid">
			<c:if test="${empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url(${pageContext.request.contextPath}/resources/img/topo5.jpg) center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			</c:if>
			<c:if test="${!empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url('${imgPath}') center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			</c:if>
			<div id="blockTransparent"></div>
			<div id="presentation" class="col-md-5 col-md-offset-0 col-sm-offset-1 col-sm-10 col-xs-12 col-xs-offset-0">
				<h2 id="infossurnoir">${topofound.nomTopo}</h2>
				<h6 id="infossoftsurnoir">ID n°${topofound.idTopo}</h6>
				<c:if test="${topofound.dispoTopo==true}">
					<h4 id="infossurnoir">Disponible à la réservation</h4>
				</c:if>
				<c:if test="${topofound.dispoTopo==false}">
					<h4 id="infoimportante">Actuellement indisponible à la réservation</h4>
				</c:if>
				<h5 id="infossurnoir">A été réservé: ${nbrReservations} fois</h5>
				<h5 id="infossurnoir">Propriétaire: <a href="utilisateur?user=${proprietairefound.id}" target="_blank">${proprietairefound.prenom} ${proprietairefound.nom}</a></h5>
				<h5 id="infossurnoir">Localisation: ${proprietairefound.ville}, ${proprietairefound.pays}</h5>
					<c:if test="${sessionScope.utilisateurencours.id!=0 && !empty sessionScope.utilisateurencours.id}">
						<c:if test="${topofound.dispoTopo==true}">
							<div id="buttonsupp">
									<a id="reglage" class="btn btn-default btn-sm" title="Demande de réservation" href="#Modalreservation"><span class="glyphicon glyphicon-book"></span></a>
									<label id="labelparametre" class="label1"> Faire une demande de réservation</label>
							</div>
						</c:if>
						<c:if test="${topofound.dispoTopo==false}">
							<div id="buttonsupp">
									<a id="reglage" class="btn btn-default btn-sm disabled" title="Demande de réservation" href=""><span class="glyphicon glyphicon-book"></span></a>
									<label id="labelparametre" class="label1"> Faire une demande de réservation</label>
							</div>
						</c:if>
				</c:if>
				<c:if test="${sessionScope.utilisateurencours.id==0 || empty sessionScope.utilisateurencours.id}">
					<div id="buttonsupp">
							<a id="reglage" class="btn btn-default btn-sm disabled" title="Demande de réservation" href=""><span class="glyphicon glyphicon-book"></span></a>
							<label id="labelparametre" class="label1"> Faire une demande de réservation</label>
					</div>
					<h6 id="infoimportante">(Pour réserver vous devez être connecté à votre compte)</h6>
				</c:if>
				<c:if test="${!empty listeNonDisponible}">
					<form id="blockdescription2" class="panel">
						<div id="paneldescription2" class="panel-body">
							<label id="infossurnoirheader">Périodes d'indisponibilité:</label>
							<div id="indisponiblescroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
								<c:forEach items="${listeNonDisponible}" var="reservation">
									<p id="petittextsurnoir">${textNonDispo}Réservation programmée entre le ${reservation.datedebutReservationFR} et le ${reservation.datefinReservationFR}</p>
								</c:forEach>
							</div>
						</div>
					</form>
				</c:if>
			</div>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Sites décrits par cet ouvrage:</legend>
					<c:if test="${!empty sitesconcernes}">
						<div id="ligneheader" class="col-xs-12">
							<div id="contenuheader" class="row">
								<label id="infossurnoirheader" class="col-xs-4">Nom du site</label>
								<label id="infossurnoirheader" class="col-xs-4">Pays</label>
								<label id="infossurnoirheader" class="col-xs-4">Ville</label>
							</div>
						</div>
							<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
								<c:forEach items="${sitesconcernes}" var="site">
								    		<div id="ligne" class="row">
										        	<p id="petittextsurnoir"  class="col-xs-4"><a target="_blank" href="site?site=${site.idSite}" >${site.nomSite}</a></p>
										        	<p id="petittextsurnoir"  class="col-xs-4">${site.paysSite}</p>
										        	<p id="petittextsurnoir"  class="col-xs-4">${site.villeSite}</p>
										    </div>
								</c:forEach>
							</div>
					</c:if>
					<c:if test="${empty sitesconcernes}">
							<h5 id="infossurnoir">La liste des sites décrit par ce topo n'est pas renseignée pour le moment.</h5>
					</c:if>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" action="espaceutilisateur" method="post" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Description de l'ouvrage:</legend>
					<div class="input-group col-xs-12">
						<textarea readonly name="description" rows="5" id="textDescription0" class="form-control" maxlength="1000" placeholder="...">${topofound.descriptionTopo}</textarea>
					</div>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			
				<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
					<div id="paneldescription" class="panel-body">
						<legend class="label1">Commentaires:</legend>
						<c:if test="${!empty reservationsdutopo}">
							<div id="ligneheader" class="col-xs-12">
								<div id="contenuheader" class="row">
									<label id="infossurnoirheader" class="col-xs-4">Nom</label>
									<label id="infossurnoirheader" class="col-xs-3">Date</label>
									<label id="infossurnoirheader" class="col-xs-5">Contenu</label>
								</div>
							</div>
								<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
									<c:forEach items="${reservationsdutopo}" var="reservation">
									    <c:if test="${!empty reservation.commentaireReservation}">
									    		<div id="ligne" class="row">
									    				<p id="petittextsurnoir"  class="col-xs-4"><a target="_blank" href="utilisateur?user=${reservation.utilisateurQuiReserve.id}" >${reservation.utilisateurQuiReserve.prenom} ${reservation.utilisateurQuiReserve.nom}</a></p>
											        	<p id="petittextsurnoir"  class="col-xs-3">de ${reservation.datedebutReservationFR} à ${reservation.datefinReservationFR}</p>
											        	<textarea readonly rows="2" id="textDescription1" maxlength="1000" >${reservation.commentaireReservation}</textarea>
											    </div>
										</c:if>
									</c:forEach>
								</div>
						</c:if>
						<c:if test="${empty reservationsdutopo}">
							<h5 id="infossurnoir">Aucun commentaire de réservation n'a été posté pour le moment.</h5>
						</c:if>
					</div>
				</form>
	</div>
</body>
</html>