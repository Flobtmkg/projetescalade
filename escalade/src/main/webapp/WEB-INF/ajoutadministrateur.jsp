<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="${pageContext.request.contextPath}/resources/img/testlogo_icon2.png"/>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/ajoutadministrateur.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fichierupload.js"></script>
<title>Espace Administrateur</title>
</head>
<body>
<div id="divdefond">
</div>
<!--  -->
<!--  -->
	<div id="ModalValidation" class="cModal">
		  <div>
		    <header>
		    		<h2 id="infossurblanc">Confirmation</h2>
		    </header>
		    	<p id="textvalidation"><br>Element ajouté</p>
		    <footer>
		    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
		    </footer>
		  </div>
	</div>
	<div id="ModalErreur" class="cModal">
		  <div>
		    <header>
		    		<h2 id="infossurblanc">Oups!</h2>
		    </header>
		    	<p id="textvalidation"><br>Une erreur est survenue, veuillez réessayer plus tard.</p>
		    <footer>
		    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
		    </footer>
		  </div>
	</div>
<!--  -->
<!--  -->
	<div class="container-fluid">
		<div class="col-sm-10 col-sm-offset-1">
			<h3><br>Espace Administrateur: ajoutez les sites, les secteurs, les voies.</h3>
		</div>
			<!--  -->
			<!-- Nouveau Site -->
			<!--  -->
			<div id="blockinscription" class="panel panel-default col-sm-10 col-sm-offset-1">
				<div class="panel-body">
					<form class="form-group" action="ajoutadministrateur" method="post" enctype="multipart/form-data">
					<br>
							<div class="col-xs-12 paspadding">
								<legend>Insérez un nouveau site:</legend>
							</div>
							<label class="col-xs-12 paspadding">Nom du site*</label>
							<input type="text" class="form-control" placeholder="Nom de site*" name="nomSite" pattern="{1,50}$" required>
							<label class="col-xs-12 paspadding">Pays du site*</label>
							<input type="text" class="form-control" placeholder="Pays*" name="paysSite" pattern="{1,50}$" required>
							<label class="col-xs-12 paspadding">Ville du site*</label>
							<input type="text" class="form-control" placeholder="Ville*" name="villeSite" pattern="{1,50}$" required>
							<label class="col-xs-12 paspadding">Latitude* (format décimal)</label>
							<input type="text" class="form-control" placeholder="ex: 48.862725*" name="latitudeSite" pattern="-?\d{1,2}\.\d{1,6}" required>
							<label class="col-xs-12 paspadding">Longitude* (format décimal)</label>
							<input type="text" class="form-control" placeholder="ex: 2.287592*" name="longitudeSite"  pattern="-?\d{1,3}\.\d{1,6}" required>
							<label class="col-xs-12 paspadding">Description du site*</label>
							<textarea rows="4" id="textDescription" class="form-control col-xs-12" placeholder="Décrivez le site ici*" name="descriptionSite" required></textarea>
							<div id="buttonsupp2" class="col-xs-12">
								<label id="buttonFile" class="btn btn-default btn-sm btn-file glyphicon glyphicon-picture"><input type="file" id="fileOpen" name="fichier" style="display: none;" onchange="change1();" accept="image/*"></label>
								<label id="labelparametre2" class="label1"> Ajouter une image</label>
								<input id="fichierSelectonne" placeholder="Aucun fichier sélectionné" type="text" class="form-control input-sm" readonly>
							</div>
							<div id="buttonAjout" class="col-xs-12 paspadding">
								<div class="col-sm-2 paspadding">
									<input id="Ajouter" type="submit" class="form-control btn btn-success" name="typedAjout" value="Ajouter site">
								</div>
								<div class="col-sm-10">
									<p id="infoobligatoire">Les champs marqués d'une astérisque* sont obligatoires</p>
								</div>
							</div>
							<br>
					</form>
				</div>
			</div>
			<!--  -->
			<!-- Nouveau Secteur -->
			<!--  -->
			<div id="blockinscription" class="panel panel-default col-sm-10 col-sm-offset-1">
				<div class="panel-body">
					<form class="form-group" action="ajoutadministrateur" method="post" enctype="multipart/form-data">
					<br>
							<div class="col-xs-12 paspadding">
								<legend>Insérez un nouveau secteur:</legend>
							</div>
							<label class="col-xs-12 paspadding">Site rattaché* (format id:nom)</label>
							<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
									<select name="siteattache" class="form-control" required>
										<c:forEach items="${Sites}" var="site">
												<option>${site.idSite}: ${site.nomSite}</option>
										</c:forEach>
									</select>
							</div>
							<label class="col-xs-12 paspadding">Nom du Secteur*</label>
							<input type="text" class="form-control" placeholder="Nom de secteur*" name="nomSecteur" pattern="{1,50}$" required>
							<label class="col-xs-12 paspadding">Hauteur du secteur en mètres*</label>
							<input type="text" class="form-control" placeholder="ex 20.5*" name="hauteurSecteur" pattern="-?\d{1,3}\.\d{1,2}" required>
							<label class="col-xs-12 paspadding">Description du secteur*</label>
							<textarea rows="4" id="textDescription" class="form-control col-xs-12" placeholder="Décrivez le secteur ici*" name="descriptionSecteur" required></textarea>
							<div id="buttonsupp2" class="col-xs-12">
								<label id="buttonFile" class="btn btn-default btn-sm btn-file glyphicon glyphicon-picture"><input type="file" id="fileOpen2" name="fichier" style="display: none;" onchange="change2();" accept="image/*"></label>
								<label id="labelparametre2" class="label1"> Ajouter une image</label>
								<input id="fichierSelectonne2" placeholder="Aucun fichier sélectionné" type="text" class="form-control input-sm" readonly>
							</div>
							<div id="buttonAjout" class="col-xs-12 paspadding">
								<div class="col-sm-2 paspadding">
									<input id="Ajouter" type="submit" class="form-control btn btn-success" name="typedAjout" value="Ajouter secteur">
								</div>
								<div class="col-sm-10">
									<p id="infoobligatoire">Les champs marqués d'une astérisque* sont obligatoires</p>
								</div>
							</div>
							<br>
					</form>
				</div>
			</div>
			<!--  -->
			<!-- Nouvelle voie -->
			<!--  -->
			<div id="blockinscription" class="panel panel-default col-sm-10 col-sm-offset-1">
				<div class="panel-body">
					<form class="form-group" action="ajoutadministrateur" method="post" enctype="multipart/form-data">
					<br>
							<div class="col-xs-12 paspadding">
								<legend>Insérez une nouvelle voie:</legend>
							</div>
							<label class="col-xs-12 paspadding">Secteur rattaché* (format id:nom)</label>
							<div class="input-group">
									<span class="input-group-addon"><i class="glyphicon glyphicon-map-marker"></i></span>
									<select name="secteurattache" class="form-control" required>
										<c:forEach items="${Secteurs}" var="secteur">
												<option>${secteur.idSecteur}: ${secteur.nomSecteur}</option>
										</c:forEach>
									</select>
							</div>
							<label class="col-xs-12 paspadding">Nom de la voie*</label>
							<input type="text" class="form-control" placeholder="Nom de voie*" name="nomVoie" pattern="{1,50}$" required>
							<label class="col-xs-12 paspadding">Cotation jusqu'a 8 longueurs par voie* (si moins laisser vide)</label>
							<div class="form-inline">
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select id="lesSelect" name="cotationVoie1" class="form-control" required>
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select name="cotationVoie2" class="form-control">
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select name="cotationVoie3" class="form-control">
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select name="cotationVoie4" class="form-control">
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
							</div>
							<div class="form-inline">
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select id="lesSelect" name="cotationVoie5" class="form-control" required>
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select name="cotationVoie6" class="form-control">
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select name="cotationVoie7" class="form-control">
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
								<div id="lesSelect" class="input-group col-sm-3">
										<span class="input-group-addon"><i class="glyphicon glyphicon-stats"></i></span>
										<select name="cotationVoie8" class="form-control">
											<option></option>
											<c:forEach var = "i" begin = "1" end = "9">
													<option>${i}A</option>
													<option>${i}A+</option>
													<option>${i}B</option>
													<option>${i}B+</option>
													<option>${i}C</option>
													<option>${i}C+</option>
										    </c:forEach>								
										</select>
								</div>
							</div>
							<label class="col-xs-12 paspadding">Equipement de la voie*</label>
							<select name="equipementVoie" class="form-control">
								<option>Voie équipée</option>
								<option>Voie non-équipée</option>			
							</select>
							<label class="col-xs-12 paspadding">Description de la voie*</label>
							<textarea rows="4" id="textDescription" class="form-control col-xs-12" placeholder="Décrivez la voie ici*" name="descriptionVoie" required></textarea>
							<div id="buttonsupp2" class="col-xs-12">
								<label id="buttonFile" class="btn btn-default btn-sm btn-file glyphicon glyphicon-picture"><input type="file" id="fileOpen3" name="fichier" style="display: none;" onchange="change3();" accept="image/*"></label>
								<label id="labelparametre2" class="label1"> Ajouter une image</label>
								<input id="fichierSelectonne3" placeholder="Aucun fichier sélectionné" type="text" class="form-control input-sm" readonly>
							</div>
							<div id="buttonAjout" class="col-xs-12 paspadding">
								<div class="col-sm-2 paspadding">
									<input id="Ajouter" type="submit" class="form-control btn btn-success" name="typedAjout" value="Ajouter voie">
								</div>
								<div class="col-sm-10">
									<p id="infoobligatoire">Les champs marqués d'une astérisque* sont obligatoires</p>
								</div>
							</div>
							<br>
					</form>
				</div>
			</div>
		</div>
</body>
</html>