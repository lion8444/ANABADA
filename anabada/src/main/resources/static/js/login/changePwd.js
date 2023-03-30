let pcheck = false;
let re_pcheck = false;


$(document).ready(function () {
    //$("#joinFormSubmit").prop('disabled', true);
    alertValue();
    $("#password").on("keyup", pwdRegCheck);
    $("#re-password").on("keyup", pwdSameCheck);

    $("#updatePwdBtn").on("click", updatePwdForm);
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
        $("#result-pwd").val($(this).val());
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

function updatePwdForm() {
    let check = pcheck&&re_pcheck;
    if(!check) {
        alert("입력란을 정확히 전부 입력해주세요.");
        $("#password").focus();
    } else {
        opener.$("#password").val($("#re-password").val());
        window.close();
    }
}

// function updatePwdForm() {
//     let check = pcheck&&re_pcheck;
//     if(!check) {
//         alert("입력란을 정확히 전부 입력해주세요.");
//         $("#password").focus();
//     } else {
//        $.ajax({
//             url: 'updatePwd'
//             ,type: 'post'
//             ,success: () => {
//                 opener.$("#password").val($("#re-password").val());
//                 window.close();
//             }
//             ,error: () => {
//                 console.log("error");
//             }
//         });
//     }
// }