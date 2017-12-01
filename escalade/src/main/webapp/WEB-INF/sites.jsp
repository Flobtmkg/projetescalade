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
			<form action="sites" method="post" id="blockdescription3" class="panel col-sm-offset-0 col-xs-12 form-group">
					<div class="col-sm-offset-0 col-xs-12">
						<div class="form-group col-sm-10 col-sm-offset-1 col-xs-12 col-xs-offset-0">
							<legend class="label1">Recherchez:</legend>
							<div id="aligneAGauche" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Site/Secteur/Voie: </label>
								<div class="input-group col-xs-12">
									<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
								    <input name="search" type="text" class="form-control" placeholder="Vous recherchez..." value="${rechsearch}">
								</div>
							</div>
							<div id="aligneADroite" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Ville: </label>
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
									<select name="ville" class="form-control">
										<c:if test="${empty rechville}">
											<option selected="selected">Toutes villes</option>
										</c:if>
										<c:if test="${!empty rechville}">
											<option>Toutes villes</option>
										</c:if>
										<c:forEach items="${paysVilles}" var="varPaysVille">
											<c:if test="${rechville==varPaysVille.villeSite}">
												<option selected="selected">${varPaysVille.villeSite}, ${varPaysVille.paysSite}</option>
											</c:if>
											<c:if test="${rechville!=varPaysVille.villeSite}">
												<option>${varPaysVille.villeSite}, ${varPaysVille.paysSite}</option>
											</c:if>
											
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group col-sm-10 col-sm-offset-1 col-xs-12 col-xs-offset-0">
							<div id="aligneAGauche" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Cotation min: </label>
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
									<select name="cotMin" class="form-control">
										<c:if test="${empty rechcotMin}">
											<option selected="selected">Toutes cotations</option>
										</c:if>
										<c:if test="${!empty rechcotMin}">
											<option>Toutes cotations</option>
										</c:if>
										<c:forEach var = "i" begin = "1" end = "9">
											<c:if test="${rechcotMin==i+='a'}">
												<option selected="selected">${i}A</option>
											</c:if>
											<c:if test="${rechcotMin!=i+='a'}">
												<option>${i}A</option>
											</c:if>
											<c:if test="${rechcotMin==i+='a+'}">
												<option selected="selected">${i}A+</option>
											</c:if>
											<c:if test="${rechcotMin!=i+='a+'}">
												<option>${i}A+</option>
											</c:if>
											<c:if test="${rechcotMin==i+='b'}">
												<option selected="selected">${i}B</option>
											</c:if>
											<c:if test="${rechcotMin!=i+='b'}">
												<option>${i}B</option>
											</c:if>
											<c:if test="${rechcotMin==i+='b+'}">
												<option selected="selected">${i}B+</option>
											</c:if>
											<c:if test="${rechcotMin!=i+='b+'}">
												<option>${i}B+</option>
											</c:if>
											<c:if test="${rechcotMin==i+='c'}">
												<option selected="selected">${i}C</option>
											</c:if>
											<c:if test="${rechcotMin!=i+='c'}">
												<option>${i}C</option>
											</c:if>
											<c:if test="${rechcotMin==i+='c+'}">
												<option selected="selected">${i}C+</option>
											</c:if>
											<c:if test="${rechcotMin!=i+='c+'}">
												<option>${i}C+</option>
											</c:if>
									    </c:forEach>								
									</select>
								</div>
							</div>
							<div id="aligneADroite" class="form-group col-sm-5 col-xs-12">
								<label id="infossurnoir">Cotation max: </label>
								<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
									<select name="cotMax" class="form-control">
										<c:if test="${empty rechcotMax}">
											<option selected="selected">Toutes cotations</option>
										</c:if>
										<c:if test="${!empty rechcotMax}">
											<option>Toutes cotations</option>
										</c:if>
										<c:forEach var = "o" begin = "1" end = "9">
											<c:if test="${rechcotMax==o+='a'}">
												<option selected="selected">${o}A</option>
											</c:if>
											<c:if test="${rechcotMax!=o+='a'}">
												<option>${o}A</option>
											</c:if>
											<c:if test="${rechcotMax==o+='a+'}">
												<option selected="selected">${o}A+</option>
											</c:if>
											<c:if test="${rechcotMax!=o+='a+'}">
												<option>${o}A+</option>
											</c:if>
											<c:if test="${rechcotMax==o+='b'}">
												<option selected="selected">${o}B</option>
											</c:if>
											<c:if test="${rechcotMax!=o+='b'}">
												<option>${o}B</option>
											</c:if>
											<c:if test="${rechcotMax==o+='b+'}">
												<option selected="selected">${o}B+</option>
											</c:if>
											<c:if test="${rechcotMax!=o+='b+'}">
												<option>${o}B+</option>
											</c:if>
											<c:if test="${rechcotMax==o+='c'}">
												<option selected="selected">${o}C</option>
											</c:if>
											<c:if test="${rechcotMax!=o+='c'}">
												<option>${o}C</option>
											</c:if>
											<c:if test="${rechcotMax==o+='c+'}">
												<option selected="selected">${o}C+</option>
											</c:if>
											<c:if test="${rechcotMax!=o+='c+'}">
												<option>${o}C+</option>
											</c:if>
									    </c:forEach>		
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
							<li class="active"><a href="sites?rech=${i}">${i}</a></li>
						</c:if>
						<c:if test="${rech!=i}">
							<li><a href="sites?rech=${i}">${i}</a></li>
						</c:if>
					</c:forEach>
					</ul>
				 </div>
			</c:if> 
			<!--  -->
			<c:forEach items="${voiesAAfficher}" var="voie" begin = "${empty rech?0:rech*10-10}" end = "${empty rech?0:rech*10-1}">
				<form id="blockdescription" class="panel col-sm-offset-1 col-sm-10 col-xs-12 form-group">
					<div id="paneldescription" class="panel-body">
						<legend class="label1"><a id="liensDuHaut" target="_blank" href="voie?voie=${voie.idVoie}">${voie.nomVoie}</a>:</legend>
						<!-- imgPath -->
						<c:if test="${empty voie.photopath}">
							<div id="cadrephoto" class="card">
								<a target="_blank" href="voie?voie=${voie.idVoie}"><img id="cadrephoto" class="photo" src="${pageContext.request.contextPath}/resources/img/previewsite1.jpg"></a>
							</div>
						</c:if>
						<c:if test="${!empty voie.photopath}">
							<div id="cadrephoto" class="card">
								<a target="_blank" href="voie?voie=${voie.idVoie}"><img id="cadrephoto" src="${voie.photopath}" class="photo"></a>
							</div>
						</c:if>
						<!-- imgPath -->
						<div id="infosDroiteImage" class="input-group col-xs-8">
							<p id="petittextsurnoir">Appartient au secteur <a target="_blank" href="secteur?secteur=${voie.idSecteur}">${voie.nomSecteur}</a></p>
							<p id="petittextsurnoir">Sur le site <a target="_blank" href="site?site=${voie.idSite}">${voie.nomSite}</a></p>
							<p id="petittextsurnoir">${voie.villeSite}, ${voie.paysSite}</p>
							<p id="petittextsurnoir">Cotation: ${voie.quotationVoie}</p>
						</div>
						<div class="input-group col-xs-12">
							<label id="infossurnoir">Description:</label>
							<textarea readonly name="description" rows="3" id="textDescription0" class="form-control" maxlength="1000" placeholder="...">${voie.descriptionVoie}</textarea>
						</div>
					</div>
				</form>
			</c:forEach>
		<c:if test="${empty voiesAAfficher}">
			<div class="col-sm-6 col-sm-offset-3 hidden-xs" id="presentation">
				<h1>Cherchez dès maintenant...</h1>
				<h1 id="textADroite"><br>et trouvez votre voie!</h1>
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