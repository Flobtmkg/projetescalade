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
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/secteur.css" rel="stylesheet">
    <!-- système de scrollbar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	 <!-- système de scrollbar -->
	<title>Descriptif de ${secteurfound.nomSecteur}</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="ModalCommentaire" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Poster un commentaire</h2>
	    </header>
	    <form action="secteur" method="post">
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
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url(${pageContext.request.contextPath}/resources/img/secteur4.jpg) center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			</c:if>
			<c:if test="${!empty imgPath}">
				<div id="cadrephoto" class="col-sm-offset-1" style="background: url('${imgPath}') center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			</c:if>
			<div id="blockTransparent"></div>
			<div id="presentation" class="col-md-5 col-md-offset-0 col-sm-offset-1 col-sm-10 col-xs-12 col-xs-offset-0">
				<legend class="label1"><a id="liensDuHaut" href="site?site=${secteurfound.idSite}">Site</a> > Secteur</legend>
				<h2 id="infossurnoir">${secteurfound.nomSecteur}</h2>
				<h5 id="infossurnoir">Secteur appartenant au site: <a target="_blank" href="site?site=${secteurfound.idSite}">${secteurfound.nomSite}</a></h5>
				<h5 id="infossurnoir">${secteurfound.paysSite}</h5>
				<h4 id="infossurnoir">${secteurfound.villeSite}</h4>
				<h6 id="infossurnoir">Latitude: ${secteurfound.latitudeSite}</h6>
				<h6 id="infossurnoir">Longitude: ${secteurfound.longitudeSite}</h6>
				<form id="blockdescription2" class="panel">
					<label id="infossurnoirheader">Liste des voies:</label>
					<div id="secteurscroll">
							<c:forEach items="${voies}" var="voie">
								<p id="aafficherenligne"><a target="_blank" href="voie?voie=${voie.idVoie}">${voie.nomVoie}</a>  </p>
							</c:forEach>
					</div>
				</form>
				<h4 id="infossurnoir">Cotations: ${minMax[0]} / ${minMax[1]}</h4>
				<h5 id="infossurnoir">Hauteur: ${secteurfound.hauteurSecteur} M</h5>
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
					<legend class="label1">Description du Secteur:</legend>
					<div class="input-group col-xs-12">
						<textarea readonly name="description" rows="5" id="textDescription0" class="form-control" maxlength="1000" placeholder="...">${secteurfound.descriptionSecteur}</textarea>
					</div>
				</div>
			</form>
			<!--  -->
<!--  -->
			<!--  -->
			
				<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
					<div id="paneldescription" class="panel-body">
						<legend class="label1">Commentaires:</legend>
						<c:if test="${!empty commentaireDuSecteur}">
							<div id="ligneheader" class="col-xs-12">
								<div id="contenuheader" class="row">
									<label id="infossurnoirheader" class="col-xs-4">Nom</label>
									<label id="infossurnoirheader" class="col-xs-2">Date</label>
									<label id="infossurnoirheader" class="col-xs-6">Contenu</label>
								</div>
							</div>
							<!-- <div class="col-xs-12"> -->
								<div id="table-scroll" class="col-xs-12 mCustomScrollbar" data-mcs-theme="light-thin">
									<c:forEach items="${commentaireDuSecteur}" var="commentaire">
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
						<c:if test="${empty commentaireDuSecteur}">
							<h5 id="infossurnoir">Aucun commentaire n'a été posté pour le moment sur ce secteur.</h5>
						</c:if>
					</div>
				</form>
	</div>
</body>
</html>