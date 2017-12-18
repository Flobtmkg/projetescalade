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
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/voie.css" rel="stylesheet">
    <!-- système de scrollbar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	 <!-- système de scrollbar -->
	<title>Descriptif de ${voiefound.nomVoie}</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="ModalCommentaire" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Poster un commentaire</h2>
	    </header>
	    <form action="voie" method="post">
	    	<div class="input-group col-xs-12">
	    		<c:if test="${!empty sessionScope.utilisateurencours && sessionScope.utilisateurencours.id!=0}">
	    			<h5 id="infossurblanc2">Vous commentez en tant que ${sessionScope.utilisateurencours.prenom} ${sessionScope.utilisateurencours.nom}.</h5>
	    		</c:if>
	    		<c:if test="${empty sessionScope.utilisateurencours.id || sessionScope.utilisateurencours.id==0}">
	    			<h5 id="infoimportante2">Vous commentez en tant qu'annonyme.</h5>
	    		</c:if>
	    	</div>
	    	<div class="input-group col-xs-12">
	    		<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
	    		<textarea name="commentaire" rows="4" id="textDescription" class="form-control" maxlength="1000" placeholder="Commentez ici..." required></textarea>
	    	</div>
	    	<footer>
				<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Annuler</a>
				<input id="btnvalider" type="submit" class="btn btn-success btn-sm" value="Valider">
			</footer>
	    </form>
	  </div>
	</div>
	<div id="divdefond">
	</div>
	<div class="container-fluid">
			<c:if test="${empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url(${pageContext.request.contextPath}/resources/img/voie.jpg) center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			</c:if>
			<c:if test="${!empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url('${imgPath}') center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			</c:if>
			<div id="blockTransparent"></div>
			<div id="presentation" class="col-md-5 col-md-offset-0 col-sm-offset-1 col-sm-10 col-xs-12 col-xs-offset-0">
				<legend class="label1"><a id="liensDuHaut" href="site?site=${voiefound.idSite}">Site</a> > <a id="liensDuHaut" href="secteur?secteur=${voiefound.idSecteur}">Secteur</a> > Voie</legend>
				<h2 id="infossurnoir">${voiefound.nomVoie}</h2>
				<c:if test="${voiefound.equipementVoie==true}">
					<h6 id="infossurnoir2">Voie équipée</h6>
				</c:if>
				<c:if test="${voiefound.equipementVoie==false}">
					<h6 id="infossurnoir2">Voie non-équipée</h6>
				</c:if>
				<h5 id="infossurnoir">Appartenant au secteur: <a target="_blank" href="secteur?secteur=${voiefound.idSecteur}">${voiefound.nomSecteur}</a></h5>
				<h5 id="infossurnoir">Sur le site: <a target="_blank" href="site?site=${voiefound.idSite}">${voiefound.nomSite}</a></h5>
				<h4 id="infossurnoir">${voiefound.villeSite}, ${voiefound.paysSite}</h4>
				<h6 id="infossurnoir">Latitude: ${voiefound.latitudeSite}</h6>
				<h6 id="infossurnoir">Longitude: ${voiefound.longitudeSite}</h6>
				<!-- 
				<form id="blockdescription2" class="panel">
					<label id="infossurnoirheader">Liste des voies:</label>
					<div id="secteurscroll">
							<c:forEach items="${voies}" var="voie">
								<p id="aafficherenligne"><a target="_blank" href="voie?voie=${voie.idVoie}">${voie.nomVoie}</a>  </p>
							</c:forEach>
					</div>
				</form>
				-->
				<c:if test="${minMax[0]!=minMax[1]}">
					<h4 id="infossurnoir">Cotations: ${minMax[0]} / ${minMax[1]}</h4>
				</c:if>
				<c:if test="${minMax[0]==minMax[1]}">
					<h4 id="infossurnoir">Cotation: ${minMax[0]}</h4>
				</c:if>
				<form id="blockdescription2" class="panel">
					<label id="infossurnoirheader">Longueurs:</label>
					<div id="secteurscroll">
						<!-- listeDesCotations -->
						<p id="aafficherenligne">Cette voie est composée </p>
						<c:forEach items="${listeDesCotations}" var="longueur">
							<p id="aafficherenligne">d'une longueur notée ${longueur}</p>
							<c:if test="${longueur!=derniereCotation}"><p id="aafficherenligne">, </p></c:if>
							<c:if test="${longueur==derniereCotation}"><p id="aafficherenligne">.</p></c:if>
						</c:forEach>
					</div>
				</form>
				<br>
				<a id="reglage" class="btn btn-default btn-sm" title="Ajouter un commentaire" href="#ModalCommentaire"><span class="glyphicon glyphicon-comment"></span></a>
				<label id="labelparametre" class="label1"> Poster un commentaire</label>
			</div>
			<div class="col-sm-offset-1 col-sm-10 col-xs-12">
				<br><a href="suggestions" >Pour nous faire des suggestions de sites à ajouter cliquez ici</a>
			</div>
			<!--  -->
<!--  -->
			<!--  -->
			<form id="blockdescription" action="espaceutilisateur" method="post" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Description de la voie:</legend>
					<div class="input-group col-xs-12">
						<textarea readonly name="description" rows="5" id="textDescription0" class="form-control" maxlength="1000" placeholder="...">${voiefound.descriptionVoie}</textarea>
					</div>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			
				<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
					<div id="paneldescription" class="panel-body">
						<legend class="label1">Commentaires:</legend>
						<c:if test="${!empty commentaireVoie}">
							<div id="ligneheader" class="col-xs-12">
								<div id="contenuheader" class="row">
									<label id="infossurnoirheader" class="col-xs-4">Nom</label>
									<label id="infossurnoirheader" class="col-xs-2">Date</label>
									<label id="infossurnoirheader" class="col-xs-6">Contenu</label>
								</div>
							</div>
							<!-- <div class="col-xs-12"> -->
								<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
									<c:forEach items="${commentaireVoie}" var="commentaire">
									    <c:if test="${!empty commentaire.contenuCommentaire}">
									    		<div id="ligne" class="row">
									    				<c:if test="${commentaire.commentateur.id==0}">
									    					<p id="petittextsurnoir"  class="col-xs-4">${commentaire.commentateur.prenom} ${commentaire.commentateur.nom}</p>
									    				</c:if>
									    				<c:if test="${commentaire.commentateur.id!=0}">
									    					<p id="petittextsurnoir"  class="col-xs-4"><a target="_blank" href="utilisateur?user=${commentaire.commentateur.id}">${commentaire.commentateur.prenom} ${commentaire.commentateur.nom}</a></p>
									    				</c:if>
											        	<p id="petittextsurnoir"  class="col-xs-2">${commentaire.dateCommentaireFR}</p>
											        	<textarea readonly rows="2" id="textDescription1" maxlength="1000" >${commentaire.contenuCommentaire}</textarea>
											    </div>
										</c:if>
									</c:forEach>
								</div>
							<!--</div> -->
						</c:if>
						<c:if test="${empty commentaireVoie}">
							<h5 id="infossurnoir">Aucun commentaire n'a été posté pour le moment sur cette voie.</h5>
						</c:if>
					</div>
				</form>
	</div>
</body>
</html>