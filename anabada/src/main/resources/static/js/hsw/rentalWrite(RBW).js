$(document).ready(function(){
	$('#write').submit(submitcheck);
});	

function submitcheck(){
	if($('#rental_title').val().trim() == ""){
		$('#rental_title ~ div').css("display" ,"block");
		return false;
	}else{
		$('#rental_title ~ div').css("display" ,"none");
	}
	
	if($('#rental_price').val() == "" || isNaN($('#rental_price').val()) || $('#rental_price').val() <= 0){
		$('tr.hidden:first').css("display" ,"block");
		return false;
	}else{
		$('tr.hidden:first').css("display" ,"none");
	}
	
	if(!$('#rental_sDate').val() || !$('#rental_eDate').val()){
		$('tr.hidden:last').css("display" ,"block");
		return false;
	} else if($('#rental_sDate').val() < getToday()){
		$('tr.hidden:last').css("display" ,"block");
		return false;
	} else if($('#rental_sDate').val() >= $('#rental_eDate').val()){
		$('tr.hidden:last').css("display" ,"block");
		return false;
	} else{
		$('tr.hidden:last').css("display" ,"none");
	}			
	if(!$('#uploadOne').val()){
		$('#uploadOne ~ div').css("display" ,"block");
		return false;
	}else{
		$('#uploadOne ~ div').css("display" ,"none");
	}	
	
	if($('#rental_content').val().trim().length <= 50){
		$('#rental_content ~ div').css("display" ,"block");
		return false;
	}else{
		$('#rental_content ~ div').css("display" ,"none");
	}
}
function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

