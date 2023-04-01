$(document).ready(function(){
	$('#write').submit(submitcheck);

});	

function submitcheck(){
	if($('#auction_title').val().trim() == ""){
		$('#auction_title ~ div').css("display" ,"block");
		return false;
	}else{
		$('#auction_title ~ div').css("display" ,"none");
	}
	
	if($('#auction_price').val() == "" || isNaN($('#auction_price').val()) || $('#auction_price').val() <= 0){
		$('tr.hidden:first').css("display" ,"block");
		return false;
	}else{
		$('tr.hidden:first').css("display" ,"none");
	}
	if(!$('#auction_finish').val() || !$('#auction_finish').val()){
		$('tr.hidden:last').css("display" ,"block");
		return false;
	} else if($('#auction_finish').val() < getToday()){
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
	
	if($('#auction_content').val().trim().length <= 50){
		$('#auction_content ~ div').css("display" ,"block");
		return false;
	}else{
		$('#auction_content ~ div').css("display" ,"none");
	}
}

function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

