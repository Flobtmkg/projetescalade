    
function verif(){
    var date_pas_sure = document.getElementById('valeur').value;
    var format = /^(\d{1,2}\/){2}\d{4}$/;
    	if(!format.test(date_pas_sure)){
        	document.getElementById('valeur').style.color='#FF0000';
        }
        else{
        	
            var date_temp = date_pas_sure.split('/');
            date_temp[1] -=1;        // On rectifie le mois !!!
            var ma_date = new Date();
            ma_date.setFullYear(date_temp[2]);
            ma_date.setMonth(date_temp[1]);
            ma_date.setDate(date_temp[0]);
            if(ma_date.getFullYear()==date_temp[2] && ma_date.getMonth()==date_temp[1] && ma_date.getDate()==date_temp[0]){
            	document.getElementById('valeur').style.color='#00FF00';
            }
            else{
            	if(ma_date.getMonth()!=date_temp[1]){
            		date_temp[1]=ma_date.getMonth()+1;
            	}
            	document.getElementById('valeur').value=ma_date.getDate()+"/"+date_temp[1]+"/"+ma_date.getFullYear()
            	document.getElementById('valeur').style.color='#00FF00';
            }
        } 
}