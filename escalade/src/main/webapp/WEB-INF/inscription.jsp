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
	<div id="ModalNonValidation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="validation">Oups!</h2>
	    </header>
	    		<p id="textvalidation"><br>Cette adresse email est déjà inscrite!</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="Modalerreur" class="cModal">
	  <div>
	    <header>
	    		<h2 id="validation">Oups!</h2>
	    </header>
	    		<p id="textvalidation"><br>Une erreur est survenue. Réessayez plus tard.</p>
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
							<div class="col-sm-6 hidden-xs">
								<label>E-mail*:</label>
							</div>
							<div class="col-sm-6 hidden-xs">
								<label>Mot de passe* (min 7 caractères):</label>
							</div>
							<div class="col-sm-6 hidden-sm hidden-md hidden-lg">
								<label>E-mail* et mot de passe*:</label>
							</div>
						</div>
						<div class="row form-inline">
							<div class="col-sm-6">
								<div class="input-group col-sm-12">
									<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
									<input type="text" class="form-control" placeholder="e-mail*" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="input-group col-sm-12">
									 <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
									<input type="password" class="form-control" placeholder="mot de passe*" name="motDePasse"  pattern="[a-zA-Z0-9._%+-]{7,20}" required>
								</div>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6 hidden-xs">
								<label>Prénom*:</label>
							</div>
							<div class="col-sm-6 hidden-xs">
								<label>Nom*:</label>
							</div>
							<div class="col-sm-6 hidden-sm hidden-md hidden-lg">
								<label>Prénom* et nom*:</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="prénom*" name="prenom" pattern="{1,50}" required>
							</div>
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="nom*" name="nom" pattern="{1,50}" required>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-6 hidden-xs">
								<label>Pays:</label>
							</div>
							<div class="col-sm-6 hidden-xs">
								<label>Ville:</label>
							</div>
							<div class="col-sm-6 hidden-sm hidden-md hidden-lg">
								<label>Pays et ville:</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="Pays" name="pays" pattern="{1,50}">
							</div>
							<div class="col-sm-6">
								<input type="text" class="form-control" placeholder="ville" name="ville" pattern="{1,50}">
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
								<input type="text" id="valeur" class="form-control" placeholder="${maintenant.theDay}/${maintenant.theMonth}/${maintenant.theYear}" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])(/0[1-9]|/1[012])/[0-9]{4}" name="dateNaissance"> 
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