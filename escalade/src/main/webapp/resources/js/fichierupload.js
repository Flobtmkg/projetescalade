function change1(){
	var nomFichier=document.getElementById("fileOpen").value;
	var nomFichier1=nomFichier.substring((nomFichier.lastIndexOf("\\")));
	var nomFichier2=nomFichier.substring((nomFichier.lastIndexOf("/")));
	if(nomFichier1.length<nomFichier2.length){
		nomFichier1=nomFichier1.substring(1);
		document.getElementById("fichierSelectonne").value=nomFichier1;
	}else{
		nomFichier2=nomFichier2.substring(1);
		document.getElementById("fichierSelectonne").value=nomFichier2;
	}
	
}

function change2(){
	var nomFichier=document.getElementById("fileOpen2").value;
	var nomFichier1=nomFichier.substring((nomFichier.lastIndexOf("\\")));
	var nomFichier2=nomFichier.substring((nomFichier.lastIndexOf("/")));
	if(nomFichier1.length<nomFichier2.length){
		nomFichier1=nomFichier1.substring(1);
		document.getElementById("fichierSelectonne2").value=nomFichier1;
	}else{
		nomFichier2=nomFichier2.substring(1);
		document.getElementById("fichierSelectonne2").value=nomFichier2;
	}
	
}

function change3(){
	var nomFichier=document.getElementById("fileOpen3").value;
	var nomFichier1=nomFichier.substring((nomFichier.lastIndexOf("\\")));
	var nomFichier2=nomFichier.substring((nomFichier.lastIndexOf("/")));
	if(nomFichier1.length<nomFichier2.length){
		nomFichier1=nomFichier1.substring(1);
		document.getElementById("fichierSelectonne3").value=nomFichier1;
	}else{
		nomFichier2=nomFichier2.substring(1);
		document.getElementById("fichierSelectonne3").value=nomFichier2;
	}
	
}
