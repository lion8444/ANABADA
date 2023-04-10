$(document).ready(function(){
	subadd();
	$('.main').change(subadd);
	$('#write').submit(submitcheck);
	$('.tempadd').click(tempadd);
});	

function submitcheck(){
	if($('#rental_title').val().trim() == ""){
		$('#rental_title ~ div').css("display" ,"block");
		return false;
	}else{
		$('#rental_title ~ div').css("display" ,"none");
	}
	
	if($('#rental_price').val() == "" || isNaN($('#rental_price').val()) || $('#rental_price').val() <= 0){
		$('#rental_price ~ div').css("display" ,"block");
		return false;
	}else{
		$('#rental_price ~ div').css("display" ,"none");
	}
	
	if(!$('#rental_sDate').val() || !$('#rental_eDate').val()){
		$('#rental_eDate ~ div').css("display" ,"block");
		return false;
	} else if($('#rental_sDate').val() < getToday()){
		$('#rental_eDate ~ div').css("display" ,"block");
		return false;
	} else if($('#rental_sDate').val() >= $('#rental_eDate').val()){
		$('#rental_eDate ~ div').css("display" ,"block");
		return false;
	} else{
		$('#rental_eDate ~ div').css("display" ,"none");
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

function tempadd(){
		let formdata = $("form#write").serialize();
		if($('#rental_price').val() != ""){
		if(isNaN($('#rental_price').val()) || $('#rental_price').val() <= 0){
			$('#rental_price ~ div').css("display" ,"block");
			return false;
		}else{
			$('#rental_price ~ div').css("display" ,"none");
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

function subadd(){
	 $.ajax({
                url: 'subcate'
                , type: 'get'
                , data: { 'main': $('.main').val() }
                , dataType: 'json'
                , success: function (category_sub) {
					let raw = '';
					for (let i = 0; i < category_sub.length; ++i){
						raw += '<option value="';
						raw += category_sub[i].category_id
						raw += '">'
						raw += category_sub[i].category_sub + '</option>';
						}
					$('.subcate').html(raw);
					}
                , error: function () {
                    alert("실패");
                }
            });
        }