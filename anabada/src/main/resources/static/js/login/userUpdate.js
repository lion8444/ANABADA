let nncheck = true;
let pcheck = true;
let re_pcheck = true;
let phonecheck = true;
let postcheck = true;

$(document).ready(function () {
    //$("#joinFormSubmit").prop('disabled', true);
    alertValue();

    $("#nickname").on("keyup", nickNameRegCheck);
    $("#change-pwd").on("click", changePwd);
    $("#user_phone").on("keyup", phoneRegCheck);

    $("#postCodeBtn").on("click", postCode);
    $("#addrDetail").on("keyup", addrUser);

    $("#updateUserBtn").on("click", updateUserFormSubmit);

    $("#withdraw").on("click", withdrawUser);
});

function nickNameRegCheck() {
    const regex = /^[a-zA-Z0-9가-힇ㄱ-ㅎㅏ-ㅣぁ-ゔァ-ヴー々〆〤一-龥]{2,10}$/;
    let content = $(this);
    if(content.val() == "") {
        nncheck = true;
        content.next(".check_html")
            .text("아무것도 입력하지 않으실 경우 기존 사용자 정보를 그대로 사용합니다.")
            .css({
                "color": "orange",
                "font-weight": "bold",
                "font-size": "10px"
            });
    } else if (!regex.test(content.val())) { // 닉네임 양식 체크
        nncheck = false;
        content.next(".check_html")
            .text("닉네임은 2글자 이상 10자 이하, 특수문자는 사용할 수 없습니다.")
            .css({
                "color": "#FA3E3E",
                "font-weight": "bold",
                "font-size": "10px"
            });
    } else {
        $.ajax({
            url: 'update/check/nick'      //Controller에서 요청 받을 주소
            , type: 'post'          //POST 방식으로 전달
            , data: {user_nick: content.val()}
            , dataType: 'json'
            , success: function(availAble) {             //컨트롤러에서 넘어온 availAble값을 받는다 
                console.log(availAble);
                if (availAble == '0') {         //availAble가 1이 아니면 사용 가능
                    nncheck = true;
                    console.log(content.val() + " " + content.next(".check_html").html());
                    content.next(".check_html")
                        .text("사용 가능한 닉네임입니다.")
                        .css({
                            "color": "green",
                            "font-weight": "bold",
                            "font-size": "10px"
                        });
                } else {                
                    nncheck = false;
                    content.next(".check_html")
                        .text("중복된 닉네임입니다.")
                        .css({
                            "color": "#FA3E3E",
                            "font-weight": "bold",
                            "font-size": "10px"
                        });
                }
            }
            , error: function() {
                alert("에러입니다");
            }
        });
        return;
    }
}

function changePwd() {
    window.open("re_pwd", 'win', 'top=100,left=100,width=400,height=400,location=no,scrollbar=no');
}

function phoneRegCheck() { 
    const regex = /^[0-9]{11}$/;
    let content = $(this);
    if($(this).val() == "") {
        phonecheck = true;
        $(this).next(".check_html")
            .text("아무것도 입력하지 않으실 경우 기존 사용자 정보를 그대로 사용합니다.")
            .css({
                "color": "orange",
                "font-weight": "bold",
                "font-size": "10px"
            });
    } else if (!regex.test($(this).val())) { // 비밀번호 양식 체크
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
        $.ajax({
            url: 'update/check/phone'      //Controller에서 요청 받을 주소
            , type: 'post'          //POST 방식으로 전달
            , data: {user_phone: content.val()}
            , dataType: 'json'
            , success: function(availAble) {             //컨트롤러에서 넘어온 availAble값을 받는다 
                console.log(availAble);
                if (availAble == '0') {         //availAble가 1이 아니면 사용 가능
                    phonecheck = true;
                    console.log(content.val() + " " + content.next(".check_html").html());
                    content.next(".check_html")
                        .text("사용 가능한 전화번호입니다.")
                        .css({
                            "color": "green",
                            "font-weight": "bold",
                            "font-size": "10px"
                        });
                } else {                
                    phonecheck = false;
                    content.next(".check_html")
                        .text("사용중인 번호입니다.")
                        .css({
                            "color": "#FA3E3E",
                            "font-weight": "bold",
                            "font-size": "10px"
                        });
                }
            }
            , error: function() {
                alert("에러입니다");
            }
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
            $("#addrDetail").focus();
        }
    }).open();
}

function addrUser() {
    // let user_addr = $("#user_addr").val();
    // let addr_detail = $(this).val();
    // user_addr = user_addr + "," + addr_detail;

    // $("#user_addr").val(user_addr);

}

function updateUserFormSubmit() {
    let check = nncheck&&pcheck&&re_pcheck&&phonecheck&&postcheck;
    let addr = $("#postCode").val() + "," + $("#address").val() + "," + $("#addrDetail").val();
    $("#user_addr").val(addr);
    if(!check) {
        alert("입력란을 정확히 전부 입력 또는 변경을 원하지 않을 경우 공란으로 해주세요.");
        $("#username").focus();
    } else {
       $.ajax({
            url: 'updateUser'
            ,type: 'post'
            ,data: $('#updateUserForm').serialize()      // serialize() : form 안에 name 붙은 것들을 모으는 함수
            ,dataType: 'json'
            ,success: (result) => {
                console.log("result : '" + result + "'");
                if(result != 1) {
                    updateError();
                }
                else {
                    updateNotify();
                }
            }
            ,error: () => {
                console.log("error");
                updateError();
            }
        });
    }
}

function withdrawUser() {
    
    let withdrawChk = $("#withdrawChk");

    if(!withdrawChk.is(":checked")) {
        alert('탈퇴 약관에 동의해 주세요.');
        withdrawChk.focus();
        return;
    }
    
    $.ajax({
        url: '/withdraw'
        ,type: 'post'
        ,dataType: 'json'
        ,success: (result) => {
            console.log("result : '" + result + "'");
            alert(result);
            if(result != 1) {
                
                withdrawError();
            }
            else {
                withdrawSuccess();
            }
        }
        ,error: (e) => {
            console.log("error");
            alert(JSON.stringify(e));
            withdrawError();
        }
    });
}