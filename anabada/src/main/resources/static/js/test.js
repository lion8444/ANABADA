let ncheck = false;
let nncheck = false;
let echeck = false;
let pcheck = false;
let re_pcheck = false;
let phonecheck = false;
let postcheck = false;
let addrcheck = false;


$(document).ready(function () {
    //$("#joinFormSubmit").prop('disabled', true);

    $("#title").on("keyup", nameRegCheck);
    $("#nickname").on("keyup", nickNameRegCheck);
    $("#email").on("keyup", emailRegCheck);
    $("#password").on("keyup", pwdRegCheck);
    $("#re-password").on("keyup", pwdSameCheck);
    $("#user_phone").on("keyup", phoneRegCheck);

    $("#postCodeBtn").on("click", postCode);
    $("#user_addr").on("keyup", addrUser);

    $("#joinFormSubmit").on("click", joinFormSubmit);



});

function nameRegCheck() {
    const regex = /^[a-zA-Z가-힇ぁ-ゔァ-ヴー々〆〤一-龥]{2,15}$/;
    let content = $(this);
    if (!regex.test(content.val())) { // 닉네임 양식 체크
        ncheck = false;
        content.next(".check_html")
            .text("이름을 정확히 적어주세요.")
            .css({
                "color": "#FA3E3E",
                "font-weight": "bold",
                "font-size": "10px"
            });
    } else {
        ncheck = true;
        content.next(".check_html")
            .text("");
        return;
    }
}

function nickNameRegCheck() {
    $.ajax({
        url: 'title/write'      //Controller에서 요청 받을 주소
        , type: 'post'          //POST 방식으로 전달
        , data: { title: $(this).val() }
        , success: function () {             //컨트롤러에서 넘어온 availAble값을 받는다 
        }
        , error: function () {
            alert("에러입니다");
        }
    });
    return;
}


function emailRegCheck() {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let content = $(this);
    if (!regex.test(content.val())) { // email 양식 체크
        echeck = false;
        content.next(".check_html")
            .text("사용할 수 없는 이메일 양식입니다.")
            .css({
                "color": "#FA3E3E",
                "font-weight": "bold",
                "font-size": "10px"
            });
        // $('input[type=button]').prop('disabled', true); // 양식에 맞지 않을 경우 중복체크 버튼 비활성화
    } else { // 공백아니면 초기화 중복체크 가능
        $.ajax({
            url: 'check/email'      //Controller에서 요청 받을 주소
            , type: 'post'          //POST 방식으로 전달
            , data: { user_email: content.val() }
            , dataType: 'json'
            , success: function (availAble) {             //컨트롤러에서 넘어온 availAble값을 받는다 
                console.log(availAble);
                if (availAble == '0') {         //availAble가 1이 아니면 사용 가능
                    echeck = true;
                    content.next(".check_html")
                        .text("사용 가능한 이메일입니다.")
                        .css({
                            "color": "green",
                            "font-weight": "bold",
                            "font-size": "10px"
                        });
                } else {
                    echeck = false;
                    content.next(".check_html")
                        .text("중복된 이메일입니다.")
                        .css({
                            "color": "#FA3E3E",
                            "font-weight": "bold",
                            "font-size": "10px"
                        });
                }
            }
            , error: function () {
                alert("에러입니다");
            }
        });
        return;
    }
}

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
        oncomplete: function (data) {
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
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
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
            let user_addr = data.zonecode + "," + addr;
            $("#user_addr").val(user_addr);
            // 커서를 상세주소 필드로 이동한다.
            postcheck = true;
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

function joinFormSubmit() {
    let check = nncheck && ncheck && echeck && pcheck && re_pcheck && phonecheck && postcheck && $("#policy").is(':checked');
    if (check) {
        $.ajax({
            url: 'signup'
            , type: 'post'
            , data: $('#joinForm').serialize()      // serialize() : form 안에 name 붙은 것들을 모으는 함수
            , dataType: 'json'
            , success: (result) => {
                console.log("result : '" + result + "'");
                if (result != 1) {
                    loginError;
                    location.replace("/");
                }
                else {
                    loginSuccess;
                    location.replace("/login");
                }
            }
            , error: () => {
                console.log("error");
            }
        });
    }
    else {
        if (!$("#policy").is(':checked')) {
            alert("회원 정책에 동의해주세요.");
            $("#username").focus();
        }
        else {
            alert("입력란을 정확히 전부 입력해주세요.");
        }
    }

}

