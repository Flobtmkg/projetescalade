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
    <link type="text/css" href="${pageContext.request.contextPath}/resources/css/espaceutilisateur.css" rel="stylesheet">
	<title>Espace de ${sessionScope.utilisateurencours.prenom}</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	<div id="divdefond">
	</div>
	<div id="ModalValidation" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Confirmation</h2>
	    </header>
	    	<p id="textvalidation"><br>Les changements ont été effectués</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div id="Modalerreur" class="cModal">
	  <div>
	    <header>
	    		<h2 id="infossurblanc">Oups!</h2>
	    </header>
	    		<p id="textvalidation"><br>Une erreur est survenue dans la communication avec la base de données. Réessayez plus tard.</p>
	    <footer>
	    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
	    </footer>
	  </div>
	</div>
	<div class="container-fluid">
			<div id="cadrephoto" class="col-sm-offset-1" style="background: url(${pageContext.request.contextPath}/resources/img/user.jpg) center no-repeat; background-size: 100% auto; background-color:rgba(0, 0, 0, 0.6)"></div>
			<div id="blockTransparent"></div>
			<div id="presentation">
				<h2 id="infossurnoir">${sessionScope.utilisateurencours.prenom} ${sessionScope.utilisateurencours.nom}</h2>
				<c:if test="${age!=Indéterminé}">
					<h4 id="infossurnoir">${age} ans</h4>
				</c:if>
				<c:if test="${age=Indéterminé}">
					<h4 id="infossurnoir">Âge ${age}</h4>
				</c:if>
				<h5 id="infossurnoir">${sessionScope.utilisateurencours.email}</h5>
				<h5 id="infossurnoir">${sessionScope.utilisateurencours.pays}</h5>
				<h5 id="infossurnoir">${sessionScope.utilisateurencours.ville}</h5>
				<button id="reglage" class="btn btn-sm" title="Paramètres"><span class="glyphicon glyphicon-cog"></span></button>
			</div>
			<form id="blockdescription" action="espaceutilisateur" method="post" class="col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Votre description:</legend>
					<div class="input-group col-xs-12">
						<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
						<textarea name="description" rows="2" id="textDescription" class="form-control" placeholder="Ajoutez ici quelques mots pour vous décrire...">${sessionScope.utilisateurencours.description}</textarea>
					</div>
					<div id="divdebtndescription" class="">
						<input type="submit" class="form-control btn btn-info btn-sm" value="Enregistrer la description">
					</div>
				</div>
			</form>
			<form id="blockdescription" class="col-sm-offset-1 col-sm-10 col-xs-12 form-group">
				<div id="paneldescription" class="panel-body">
					<legend class="label1">Topos:</legend>
					<div class="input-group col-xs-12">
						<span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
						<textarea rows="2" id="textDescription" class="form-control" placeholder="à virer pour mettre les infos sur les topos"></textarea>
					</div>
				</div>
			</form>
	</div>
</body>
</html>