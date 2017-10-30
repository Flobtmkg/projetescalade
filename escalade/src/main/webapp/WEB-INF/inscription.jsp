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
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/inscription.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/inscription.css" rel="stylesheet">
<title>Inscription</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
	</div>
	<div id="ModalValidation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="validation">Vous faites désormais parti de nos membres!</h2>
	    </header>
	    	<p id="textvalidation"><br>Vous pouvez dès maintenant vous connecter à votre espace membre et découvrir toutes les fonctionnalités du site.</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
		<div class="container-fluid">
		<div class="col-sm-10 col-sm-offset-1">
			<h3><br>Vous souhaitez rejoindre notre communauté, explorer les spots et trouver votre voie?</h3>
		</div>
			<div id="blockinscription" class="panel panel-default col-sm-10 col-sm-offset-1">
				<div class="panel-body">
					<form class="form-group" action="inscription" method="post">
					<br>
					<div class="row">
							<div class="col-sm-5">
								<legend>Inscription</legend>
							</div>
					</div>
						<div class="row">
							<div class="col-sm-6">
								<label>E-mail:</label>
							</div>
							<div class="col-sm-6">
								<label>Mot de passe:</label>
							</div>
						</div>
						<div class="row form-inline">
							<div class="col-sm-6">
								<div class="input-group col-sm-12">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<input type="text" class="form-control" placeholder="e-mail*" name="email" required>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="input-group col-sm-12">
									 <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
									<input type="password" class="form-control" placeholder="mot de passe*" name="motDePasse" required>
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6">
								<label>Prénom:</label>
							</div>
							<div class="col-sm-6">
								<label>Nom:</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="prénom*" name="prenom" required>
							</div>
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="nom*" name="nom" required>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6">
								<label>Pays:</label>
							</div>
							<div class="col-sm-6">
								<label>Ville:</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="Pays" name="pays">
							</div>
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="ville" name="ville">
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6">
								<label>Date de naissance: (format JJ/MM/AAAA)</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								 <input type="text" id="valeur" onKeyUp="verif();" class="form-control" placeholder="${maintenant.theDay}/${maintenant.theMonth}/${maintenant.theYear}" name="dateNaissance"> 
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-2">
								<input id="valider" type="submit" class="form-control btn btn-success" value="Valider">
							</div>
							<div class="col-sm-10">
								<p id="infoobligatoire">Les champs marqués d'une astérisque* sont obligatoires</p>
							</div>
						</div>
						<br>
					</form>
				</div>
			</div>
			<div class="col-sm-10 col-sm-offset-1">
				<p>Pour nous contacter directement, ajouter des spots vérifiés, ou toute autre demande envoyez un e-mail a: escalade@gmail.com</p>
			</div>
		</div>
			
</body>
</html>