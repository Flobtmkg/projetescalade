	<nav id="barre" class="form-inline navbar navbar-default container-fluid">
		<div class="navbar-header">
				<a href="./accueil" ><img class="navbar-btn rotatelogo" src="${pageContext.request.contextPath}/resources/img/testlogo_mediumdark.png" width="49" height="33"></a>
				<img id="barreseparation" class="navbar-btn" src="${pageContext.request.contextPath}/resources/img/barreverticale.png">
			</div>
		<div id="liensmenu" class="col-xs-offset-1">
	    	<ul class="nav navbar-nav">
				<li><a href="./sites">Parcourir les sites</a></li>
				<li><a href="./topos">Parcourir les topos</a></li>
				<li><a href="./recherche">Rechercher par critères</a></li>
				<li><a href="./inscription">Inscrivez vous ici !</a></li>
			</ul>
		</div>
		<div id="connexion" class="navbar-right">
					<c:choose>
						<c:when test="${sessionScope.utilisateurencours.id!=0 && !empty sessionScope.utilisateurencours.id}">
							<div id="blockconnexion" class="form-group navbar-btn">
								<label id="connecte">Bonjour ${sessionScope.utilisateurencours.prenom} </label>
								<a href="./espaceutilisateur"><button id="boutonhome" onmouseover="affichmenu();" class="btn btn-xs btn-primary"><span class="glyphicon glyphicon-user"></span><c:if test="${sessionScope.nouvellesNotifications>0}"><span class="badge">${sessionScope.nouvellesNotifications}</span></c:if></button></a>
							</div>
						</c:when>
						<c:when test="${sessionScope.utilisateurencours.id==0||empty sessionScope.utilisateurencours.id}">
							<form id="blockconnexion" class="form-group" action="connexion" method="post">
								<input type="text" class="form-control navbar-btn input-sm" placeholder="e-mail" name="email" required>
								<input type="password" class="form-control navbar-btn input-sm" placeholder="mot de passe" name="motDePasse" required>
								<button type="submit" class="btn btn-primary navbar-btn btn-sm">connexion <span class="glyphicon glyphicon-off"></span></button>
							</form>
						</c:when>
					</c:choose>
		</div>
	</nav>
	<ul id="listutilisateur" class="list-group col-lg-2 navbar-right sousmenuutilisateur">
		<li class="list-group-item" onmouseout="cachemenu();" onmouseover="affichmenu();"><a href="./espaceutilisateur"><span class="glyphicon glyphicon-user"></span> Espace utilisateur </a><c:if test="${sessionScope.nouvellesNotifications>0}"><span class="badge">${sessionScope.nouvellesNotifications}</span></c:if></li>
		<li class="list-group-item" onmouseout="cachemenu();" onmouseover="affichmenu();"><a href="./deconnexion"><span class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
	</ul>
	
<div id="Modal" class="cModal">
  <div>
    <header>
    		<h2 id="oups">Oups!</h2>
    </header>
    	<p id="texterreur"><br>Votre adresse e-mail ou votre mot de passe n'ont pas été reconnus.<br>Veuillez réessayer.</p>
    <footer>
    	<a href="#fermer" id="btnFermer" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-remove"></span> Fermer</a>
    </footer>
  </div>
</div>