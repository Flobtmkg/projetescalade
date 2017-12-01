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
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/sites.css" rel="stylesheet">
    <!-- système de scrollbar -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jqueryscrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	 <!-- système de scrollbar -->
	<title>Parcourir les sites</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
	</div>
	<div id="barreDeRecherche" class="container-fluid">
			<!--  -->
<!--  Formulaire de recherche-->
			<!--  -->
			<form action="topos" method="post" id="blockdescription3" class="panel col-sm-offset-0 col-xs-12 form-group">
					<div class="col-sm-offset-0 col-xs-12">
						<div class="form-group col-sm-10 col-sm-offset-1 col-xs-12 col-xs-offset-0">
							<legend class="label1">Recherchez:</legend>
							<div id="aligneAGauche" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Rechercher dans topos: </label>
								<div class="input-group col-xs-12">
									<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
								    <input name="search" type="text" class="form-control" placeholder="Vous recherchez..." value="${rechsearch}">
								</div>
							</div>
							<div id="aligneADroite" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Sites: </label>
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
									<select name="site" class="form-control">
										<c:if test="${empty rechsite}">
											<option selected="selected">Tout les sites</option>
										</c:if>
										<c:if test="${!empty rechsite}">
											<option>Tout les sites</option>
										</c:if>
										<c:forEach items="${listeDesSites}" var="site">
											<c:if test="${rechsite==site.nomSite}">
												<option selected="selected">${site.nomSite}</option>
											</c:if>
											<c:if test="${rechsite!=site.nomSite}">
												<option>${site.nomSite}</option>
											</c:if>
											
										</c:forEach>
									</select>
								</div>
							</div>
							<div id="aligneAGauche" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Disponibilité à la réservation: </label>
								<div class="input-group col-xs-12">
									<span class="input-group-addon"><i class=" 	glyphicon glyphicon-book"></i></span>
									<select name="dispoTopo" class="form-control">
										<option>Disponible ou indisponible</option>
										<c:if test="${dispo=='Disponible'}">
											<option selected="selected">Disponible uniquement</option>
										</c:if>
										<c:if test="${dispo!='Disponible'}">
											<option>Disponible uniquement</option>
										</c:if>
									</select>
								</div>
							</div>
						</div>
						<div id="butonRecherche" class="form-group col-sm-10 col-sm-offset-1 col-xs-12 col-xs-offset-0">
							<button type="submit" class="btn btn-success col-xs-12"><span class="glyphicon glyphicon-search"></span> Rechercher</button>
						</div>	
					</div>
			</form>
			<!--  -->
<!--  Formulaire de recherche-->
			<!--  -->
			<c:if test="${!empty rech}">
				 <div id="conteneurPager" class="col-xs-12">
				 	<ul id="pager" class="pagination">
					<c:forEach var = "i" begin = "1" end = "${nbPages}">
						<c:if test="${rech==i}">
							<li class="active"><a href="topos?rech=${i}">${i}</a></li>
						</c:if>
						<c:if test="${rech!=i}">
							<li><a href="topos?rech=${i}">${i}</a></li>
						</c:if>
					</c:forEach>
					</ul>
				 </div>
			</c:if> 
			<!--  -->
			<c:forEach items="${toposAAfficher}" var="topo" begin = "${empty rech?0:rech*10-10}" end = "${empty rech?0:rech*10-1}">
				<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
					<div id="paneldescription" class="panel-body">
						<legend class="label1"><a id="liensDuHaut" target="_blank" href="topo?topo=${topo.idTopo}">${topo.nomTopo}</a>:</legend>
						<!-- imgPath -->
						<c:if test="${empty topo.photopath}">
							<div id="cadrephoto" class="card">
								<a target="_blank" href="topo?topo=${topo.idTopo}"><img id="cadrephoto" class="photo" src="${pageContext.request.contextPath}/resources/img/previewtopo5.jpg"></a>
							</div>
						</c:if>
						<c:if test="${!empty topo.photopath}">
							<div id="cadrephoto" class="card">
								<a target="_blank" href="topo?topo=${topo.idTopo}"><img id="cadrephoto" src="${topo.photopath}" class="photo"></a>
							</div>
						</c:if>
						<!-- imgPath -->
						<div id="infosDroiteImage" class="input-group col-xs-8">
							<p id="petittextsurnoir">Decrit les sites:
							<c:forEach items="${topo.sitesAssocies}" var="site">
								<a target="_blank" href="site?site=${site.idSite}">${site.nomSite} </a>
							</c:forEach>
							</p>
							<p id="petittextsurnoir">Propriétaire: <a target="_blank" href="utilisateur?user=${topo.proprietaire.id}">${topo.proprietaire.prenom} ${topo.proprietaire.nom}</a></p>
						</div>
						<div class="input-group col-xs-12">
							<label id="infossurnoir">Description:</label>
							<textarea readonly name="description" rows="3" id="textDescription0" class="form-control" maxlength="1000" placeholder="...">${topo.descriptionTopo}</textarea>
						</div>
					</div>
				</form>
			</c:forEach>
		<c:if test="${empty toposAAfficher}">
			<div class="col-sm-6 col-sm-offset-3 hidden-xs" id="presentation">
				<h1>Avant de grimper...</h1>
				<h1 id="textADroite"><br>laissez nous vous faire un petit topo!</h1>
			</div>
		</c:if>
	</div>
</body>
<!-- 										<option>1A</option><option>1A+</option><option>1B</option><option>1B+</option><option>1C</option><option>1C+</option>
										<option>2A</option><option>2A+</option><option>2B</option><option>2B+</option><option>2C</option><option>2C+</option>									
										<option>3A</option><option>3A+</option><option>3B</option><option>3B+</option><option>3C</option><option>3C+</option>									
										<option>4A</option><option>4A+</option><option>4B</option><option>4B+</option><option>4C</option><option>4C+</option>									
										<option>5A</option><option>5A+</option><option>5B</option><option>5B+</option><option>5C</option><option>5C+</option>									
										<option>6A</option><option>6A+</option><option>6B</option><option>6B+</option><option>6C</option><option>6C+</option>									
										<option>7A</option><option>7A+</option><option>7B</option><option>7B+</option><option>7C</option><option>7C+</option>									
										<option>8A</option><option>8A+</option><option>8B</option><option>8B+</option><option>8C</option><option>8C+</option>									
										<option>9A</option><option>9A+</option><option>9B</option><option>9B+</option><option>9C</option><option>9C+</option>	 -->
</html>