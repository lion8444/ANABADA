$(document).ready(function () {
    $("#locales").change(function () {  // 스프링 번역 값 지정
        var selectedOption = $('#locales').val();
        if (selectedOption != '') {
            // alert("location.href : "+ location.href);
            // alert("url : "+$(location).attr('href'));
            // alert("url : "+$(location).attr('protocol')+"//"+$(location).attr('host')+""+$(location).attr('pathname')+""+$(location).attr('search'));
            // alert("host: " + $(location).attr('host'));
            // alert("pathname : " + $(location).attr('pathname'));
            // alert("search : + " + $(location).attr('search'));
            let url = $(location).attr('pathname');
            let search = $(location).attr('search');
            window.location.replace(url + '?lang=' + selectedOption);
        }
    });

    // 파파고 api실행
    $("#papagoSubmit").on("click", papagoTrans);
});

// 파파고 api실행 로직
function papagoTrans() {
    let str = $(".papago");

    str.each(function (e) {
        let test = $("#papago"+e);
        console.log(test.text());

        $.ajax({
            type: "post",
            url: "/api/translate",
            data: { listid: e, source: "ko", target: "ja", text: $("#papago"+e).text() },
            dataType: "json",
            success: function (data) {
                for(var i = 0; i < str.length; i++) {
                    if($("#papago"+e).attr('papagoCount') == data.listid) {
                        $("#papago"+e).text(data.transmsg);
                    }
                }
            }
        });
    });
}


