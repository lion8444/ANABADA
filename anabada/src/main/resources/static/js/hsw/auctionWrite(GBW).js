$(document).ready(function(){
	$('#write').submit(submitcheck);
	$('.tempadd').click(tempadd);
});	

function submitcheck(){
	if($('#auction_title').val().trim() == ""){
		$('#auction_title ~ div').css("display" ,"block");
		return false;
	}else{
		$('#auction_title ~ div').css("display" ,"none");
	}
	
	if($('#auction_price').val() == "" || isNaN($('#auction_price').val()) || $('#auction_price').val() <= 0){
		$('#auction_price ~ div').css("display" ,"block");
		return false;
	}else{
		$('#auction_price ~ div').css("display" ,"none");
	}
	
	if(!$('#auction_finish').val()){
		$('#auction_finish ~ div').css("display" ,"block");
		return false;
	} else if($('#auction_finish').val() < getToday()){
		$('#auction_finish ~ div').css("display" ,"block");
		return false;
	} else{
		$('#auction_finish ~ div').css("display" ,"none");
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

function tempadd(){
		let formdata = $("form#write").serialize();
		if($('#auction_price').val() != ""){
		if(isNaN($('#auction_price').val()) || $('#auction_price').val() <= 0){
			$('#auction_price ~ div').css("display" ,"block");
			return false;
		}else{
			$('#auction_price ~ div').css("display" ,"none");
		}
		}
		
			$.ajax({
			url: 'tempadd'
			, type: 'post'
			, data : formdata
			, success: function(){
				alert("임시 저장 되었습니다.")
			}
			, error: function(){
				alert("실패");
			}
			
		});
}