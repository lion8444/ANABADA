$(document).ready(function(){
	$('#write').submit(submitcheck);

});	

function submitcheck(){
	if($('#used_title').val().trim() == ""){
		$('#used_title ~ div').css("display" ,"block");
		return false;
	}else{
		$('#used_title ~ div').css("display" ,"none");
	}
	if($('#used_price').val() == "" || isNaN($('#used_price').val()) || $('#used_price').val() <= 0){
		$('tr.hidden').css("display" ,"block");
		return false;
	}else{
		$('tr.hidden').css("display" ,"none");
	}
	if(!$('#uploadOne').val()){
		$('#uploadOne ~ div').css("display" ,"block");
		return false;
	}else{
		$('#uploadOne ~ div').css("display" ,"none");
	}	
	
	if($('#used_content').val().trim().length <= 50){
		$('#used_content ~ div').css("display" ,"block");
		return false;
	}else{
		$('#used_content ~ div').css("display" ,"none");
	}
	
}
