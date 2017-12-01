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
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/utilisateur.css" rel="stylesheet">
    <!-- système de scrollbar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	 <!-- système de scrollbar -->
	<title>${utilisateurfound.prenom} ${utilisateurfound.nom}</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
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
	<div class="container-fluid">
			<div id="cadrephoto" class="col-sm-offset-1" style="background: url(${pageContext.request.contextPath}/resources/img/user.jpg) center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			<div id="blockTransparent"></div>
			<div id="presentation">
				<h2 id="infossurnoir">${utilisateurfound.prenom} ${utilisateurfound.nom}</h2>
				<c:if test="${age!='Indéterminé'}">
					<h4 id="infossurnoir">${age} ans</h4>
				</c:if>
				<c:if test="${age=='Indéterminé'}">
					<h4 id="infossurnoir">Âge ${age}</h4>
				</c:if>
				<h5 id="infossurnoir">${utilisateurfound.pays}</h5>
				<h5 id="infossurnoir">${utilisateurfound.ville}</h5>
			</div>
			<form id="blockdescription" action="espaceutilisateur" method="post" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Description:</legend>
					<div class="input-group col-xs-12">
						<textarea readonly name="description" rows="2" id="textDescription0" class="form-control" maxlength="1000" placeholder="...">${utilisateurfound.description}</textarea>
					</div>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Topos:</legend>
					<c:if test="${!empty toposfound}">
						<div id="ligneheader" class="col-xs-12">
							<div id="contenuheader" class="row">
								<label id="infossurnoirheader" class="col-xs-6">Titre des topos</label>
								<label id="infossurnoirheader" class="col-xs-6">Disponibilité</label>
							</div>
						</div>
							<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
								<c:forEach items="${toposfound}" var="topo">
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
					<c:if test="${empty toposfound}">
							<h5 id="infossurnoir">${utilisateurfound.prenom} ${utilisateurfound.nom} ne possède pas de topo pour le moment.</h5>
					</c:if>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Commentaires:</legend>
					<div id="ligneheader" class="col-xs-12">
						<div id="contenuheader" class="row">
							<label id="infossurnoirheader" class="col-xs-4">Objet du commentaire</label>
							<label id="infossurnoirheader" class="col-xs-3">Date</label>
							<label id="infossurnoirheader" class="col-xs-5">Contenu</label>
						</div>
					</div>
						<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
							<c:forEach items="${reservationsfound}" var="reservation">
							    <c:if test="${!empty reservation.commentaireReservation}">
							    		<div id="ligne" class="row">
									        	<p id="petittextsurnoir"  class="col-xs-4">Réservation de <a target="_blank" href="topo?topo=${reservation.topoAssocié.idTopo}" >${reservation.topoAssocié.nomTopo}</a></p>
									        	<p id="petittextsurnoir"  class="col-xs-3">de ${reservation.datedebutReservationFR} à ${reservation.datefinReservationFR}</p>
									        	<textarea readonly rows="2" id="textDescription1" maxlength="1000" >${reservation.commentaireReservation}</textarea>
									    </div>
								</c:if>
							</c:forEach>
							<c:forEach items="${commentairesfound}" var="commentaire">
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