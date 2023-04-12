// myPage 관련 자바스크립트 커스텀 파일

$(document).ready(function() {

    let inquirelist = $('.inquirelist');
    let inq_answer = $('.inq_answer');

    let reportlist = $('.reportlist');
    let report_answer = $('.report_answer');
    
    // 나의 문의 내역 버튼 활성화
    inquirelist.on('click', function() {
        $(this).next('.inq_answer').css('display', 'block');
    });

    inq_answer.on('click', function() {
        $(this).css('display', 'none');
    });
    // inquirelist.on('click', function() {
    //     $('.inq_answer').toggle('show');
    // });


	// 나의 신고 내역 버튼 활성화
    reportlist.on('click', function() {
        $(this).next('.report_answer').css('display', 'block');
    });

    report_answer.on('click', function() {
        $(this).css('display', 'none');
    });
   
   
    // 내 정보 수정 관련 js
    let pcheck = false;
    let re_pcheck = false;
    let phonecheck = false;
    let postcheck = false;
    let nowPwChk = false;

    $("#password").on("keyup", pwdRegCheck);
    $("#re-password").on("keyup", pwdSameCheck);
    $("#user_phone").on("keyup", phoneRegCheck);

    $("#postCodeBtn").on("click", postCode);
    $("#user_addr").on("keyup", addrUser);

    $("#modifyFormSubmit").on("click", modifyFormSubmit);

    // 현재 패스워드 일치하는지 확인
    $('#now_password').on('keyup', function() {
        $.ajax({
            url: 'modify_nowPw'
            , type: 'post'
            , data: {user_email: $('#user_email').val() , nowPw: $('#now_password').val()}
            , dataType: 'json'
            , success: function(result) {
                if(result == 0) {
                    nowPwChk = false;
                    $('#now_password').next('.check_html')
                    .html('현재 비밀번호와 일치하지 않습니다.')
                    .css({
                        'color': 'red'
                    });
                }
    
                if(result == 1) {
                    nowPwChk = true;
                    $('#now_password').next('.check_html')
                    .html('현재 비밀번호와 일치 합니다.')
                    .css({
                        'color': 'green'
                    });
                }
            }
            , error: function(e) {
                alert(JSON.stringify(e));
            }                                                       
        })
    });

    function pwdRegCheck() {
        const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,15}$/;
        if (!regex.test($(this).val())) { // 비밀번호 양식 체크
            pcheck = false;
            $(this).next(".check_html")
                .text("영어 대소문자, 숫자, 특수문자(@$!%*?&)를 반드시 하나 이상 포함하여 8자리 이상, 15자리 이하여야 합니다.")
                .css({
                    "color": "#FA3E3E",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
        } else { // 정규표현식과 일치하면 초기화
            pcheck = true;
            $(this).next(".check_html")
                .text("사용 가능한 비밀번호입니다.")
                .css({
                    "color": "green",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
            return;
        }
    }

    function pwdSameCheck() {
        let pwd = $("#password").val();
        console.log($(this).html());
        if ($(this).val() == "") {
            re_pcheck = false;
            $(this).next(".check_html")
                .text("");
        }
        if (pwd != $(this).val()) { // 비밀번호 같은지 체크
            re_pcheck = false;
            $(this).next(".check_html")
                .text("비밀번호가 일치하지 않습니다.")
                .css({
                    "color": "#FA3E3E",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
        } else { // 같으면 초기화
            re_pcheck = true;
            $(this).next(".check_html")
                .text("비밀번호가 일치합니다.")
                .css({
                    "color": "green",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
            return;
        }
    }
    
    function phoneRegCheck() { 
        const regex = /^[0-9]{11}$/;
        if (!regex.test($(this).val())) { // 비밀번호 양식 체크
            phonecheck = false;
            $(this).next(".check_html")
                .text("잘못된 양식입니다. '-' 없이 숫자로만 입력해주세요")
                .css({
                    "color": "#FA3E3E",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
        } else { // 정규표현식과 일치하면 초기화
            phonecheck = true;
            $(this).next(".check_html")
                .text("사용 가능한 전화번호입니다.")
                .css({
                    "color": "green",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
            return;
        }
    }
    
    function postCode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
    
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                
                let addr = ''; // 주소 변수
                let extraAddr = ''; // 참고항목 변수
    
                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }
                console.log(addr);
                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    console.log(extraAddr);
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    addr = addr + " " + extraAddr;
                }
                console.log(addr);
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $('#postCode').val(data.zonecode);
                $("#address").val(addr);
                
                // 커서를 상세주소 필드로 이동한다.
                postcheck = true;
                $("#addrDetail").val('');
                $("#addrDetail").focus();
            }
        }).open();
    }
    
    function addrUser() {
        let user_addr = $("#user_addr").val();
        let addr_detail = $(this).val();
        user_addr = user_addr + "," + addr_detail;
    
        $("#user_addr").val(user_addr);
    
    }

    function modifyFormSubmit() {
        let check = pcheck  && re_pcheck  && phonecheck  && postcheck  && nowPwChk;
        if (!check) {
            alert("입력란을 정확히 전부 입력해주세요.");

        } else if (!policy) {
            alert("회원 정책에 동의해주세요.");
            $("#policy").focus();
        }
        else {
            $.ajax({
                url: 'signup'
                , type: 'post'
                , data: $('#joinForm').serialize()      // serialize() : form 안에 name 붙은 것들을 모으는 함수
                , dataType: 'json'
                , success: (result) => {
                    console.log("result : '" + result + "'");
                    if (result != 1) {
                        centerNotify.fire({
                            icon: 'error',
                            title: '회원 가입 실패',
                            text: '메인페이지로 이동합니다.'
                        });
                        location.replace("/");
                    }
                    else {
                        joinNotify();
                    }
                }
                , error: () => {
                    console.log("error");
                    centerNotify.fire({
                        icon: 'error',
                        title: '회원 가입 실패',
                        text: '메인페이지로 이동합니다.'
                    });
                    location.replace("/");
                }
            });
        }
    }

}); // window.onload 끝

$(document).ready(function() {
    

});

// 중고 거래 취소 alert 확인
function usedCancleChk() {
    const regex = /[가-힣ぁ-ゔァ-ヴー々〆〤一-龥]/;
    let cancle = $("#cancle-text");
    // console.log(cancle.val());
    // console.log(regex.test(cancle.val()));
    if(regex.test(cancle.val())) {
        if(confirm(cancle.val())) {
            return true;
        }   
    } else {
        // console.log(cancle.attr('alertText'));
        if(confirm(cancle.attr('alertText'))) {
            return true;
        }
    }
    

    return false;
}

// function extendRental(rental_id, rDetail_sDate, rDetail_eDate) {
    
//     alert(rental_id);

//     window.open('test', 'test', 'top=100,left=500,width=500,height=300');

//     document.form.action

// }

// 렌탈 반납 확인 alert 확인
function returnCheck() {
    if(confirm('정말로 렌탈 반납을 확인 하시겠습니까?')) {
        return true;
    }

    return false;
}

// 다마고치 캐릭터설정 alert 확인
function damaCheck() {
    if(confirm('대표 다마고치는 최종 레벨에 맞는 다마고치로 적용됩니다.\n변경 하시겠습니까?')) {
        return true;
    }

    return false;
}

function charge(user_nick){
	window.open('charge', '충전 페이지', "width=1000, height=500");
}

// 중고 거래 완료 alert 확인
function usedComfirmChk() {
    if(confirm('거래를 완료 하시겠습니까?')) {
        return true;
    }

    return false;
}

// 렌탈 취소 alert 확인
function cancleRentalChk() {
    if(confirm('정말로 렌탈을 취소 하시겠습니까?')) {
        return true;
    }

    return false;
}

// 경매 취소 alert 확인
function cancleAuctionChk() {
    if(confirm('정말로 경매를 취소 하시겠습니까?')) {
        return true;
    }

    return false;
}

// 입찰 취소 alert 확인
function cancleBidChk() {
    if(confirm('정말로 입찰을 취소 하시겠습니까?')) {
        return true;
    }

    return false;
}

