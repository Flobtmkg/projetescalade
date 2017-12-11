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
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/suggestions.css" rel="stylesheet">
<title>Suggestions et contact</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
	</div>
	<div id="ModalValidation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="validation">Confirmation</h2>
	    </header>
	    	<p id="textvalidation"><br>Votre suggestion a bien été envoyée et sera étudée par l'administrateur.</p>
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
			<h3><br>Vous avez une suggestion à nous faire? proposer l'ajout d'un site? Contactez nous ici.</h3>
		</div>
			<div id="blockinscription" class="panel panel-default col-sm-10 col-sm-offset-1">
				<div class="panel-body">
					<form class="form-group" action="suggestions" method="post">
					<br>
					<div class="row">
							<div class="col-sm-5">
								<legend>Editez votre message</legend>
							</div>
					</div>
					<br>
					<div class="col-sm-6 col-xs-12">
						<p id="textavertissement">Utilisez ce formulaire pour nous contacter ou nous faire parvenir vos suggestions. <br> Vous pouvez notamment nous envoyer vos suggestions de sites, de secteurs, de voies à intégrer à la base de donnée de la plateforme. Cette opération ne peut être réalisée par les utilisateurs eux-mêmes, chaque site devant être vérifié afin de garantir à nos membres des conditions suffisantes de sécurité.</p>
					</div>
						<div class="row">
							<div class="form-group form-inline col-xs-12">
								<div id="adroite" class="col-md-4 col-sm-6 col-xs-8 form-group">
									<input id="lesInputs" type="text" class="form-control input-sm" placeholder="Prenom*" name="prenom" value="${sessionScope.utilisateurencours.prenom}" pattern="{1,50}" required>
								</div>
								<div id="adroite" class="input-group col-md-8 col-sm-6 col-xs-4">
									<label id="leslabels">Prénom*:</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group form-inline col-xs-12">
								<div id="adroite" class="col-md-4 col-sm-6 col-xs-8 form-group">
									<input id="lesInputs" type="text" class="form-control input-sm" placeholder="Nom*" name="nom" value="${sessionScope.utilisateurencours.nom}" pattern="{1,50}" required>
								</div>
								<div id="adroite" class="input-group col-md-8 col-sm-6 col-xs-4">
									<label id="leslabels">Nom*:</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group form-inline col-xs-12">
								<div id="adroite" class="col-md-4 col-sm-6 col-xs-8 form-group">
									<input id="lesInputs" type="text" class="form-control input-sm" placeholder="e-mail*" name="email" value="${sessionScope.utilisateurencours.email}" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required>
								</div>
								<div id="adroite" class="input-group col-md-8 col-sm-6 col-xs-4">
									<label id="leslabels">E-mail*:</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-2">
								<label>Objet:</label>
							</div>	
						</div>
						<div class="row">
							<div class="form-group col-xs-12">
								<select name="objet" class="form-control input-sm" required>
									<option>Proposer ajout site/secteur/voie</option>
									<option>Prise de contact</option>
								</select>
							</div>
						</div>
						<div class="input-group col-xs-12">
									<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
									<textarea maxlength="2000" name="message" rows="8" id="textDescription" class="form-control" placeholder="Votre message ici..." required></textarea>
						</div>
						<br>
						<div class="row">
								<button id="valider" type="submit" class="btn btn-success">Envoyer <span class="glyphicon glyphicon-send"></span></button>
								<p id="infoobligatoire">Les champs marqués d'une astérisque* sont obligatoires</p>
						</div>
						<br>
					</form>
				</div>
			</div>
		</div>
			
</body>
</html>