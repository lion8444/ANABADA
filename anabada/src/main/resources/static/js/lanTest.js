$(document).ready(function () {
    $("#locales").on('click', springLauguageTest);  // 스프링 다국어 처리 셀렉트박스

    // 파파고 api실행 - 반복문에 하나씩 넣기
    $("#papagoSubmit").on("click", papagoTrans);
    $("#papagoSubmit").on("click", papagoTrans2);

    // 파파고 api실행 - 전체 한번에
    $("#papagoSubmit2").on("click", papagoTransAll);

    // 파파고 api 실행 - 게시판 글 읽을 때
    $("#papagoSubmit3").on("click", papagoBoardDetail);
});


// 스프링 다국어 처리 selectBox - test용
function springLauguageTest() {
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
}

// 파파고 api실행 - 반복문에 하나씩 넣기
function springLanguage(data) {

    console.log('spring2 진입');

    if(data == 'ko') {
        console.log('kokoko');
        let url = $(location).attr('pathname');
        let search = $(location).attr('search');
        window.location.replace(url + '?lang=' + data);
    }

    if(data == 'ja-JP') {
        console.log('jpjpjpjpjp');
        let url = $(location).attr('pathname');
        let search = $(location).attr('search');
        window.location.replace(url + '?lang=' + data);
    }
   
    // let selectedOption = $('.locales').val();
    // console.log(selectedOption);
}

// 파파고 api실행 - 반복문에 하나씩 넣기
function papagoTrans() {
    let str = $(".papago");

    console.log("papagoTrans 진입");

    str.each(function (e) {
        let test = $("#papago"+e);
        console.log("처음 찍는 거 : " + test.text());

        if(test.text() != '') {

            $.ajax({
                type: "post",
                url: "/api/translate",
                data: { listid: e, source: "ko", target: "ja", text: $("#papago"+e).text() },
                dataType: "json",
                success: function (data) {
                    for(var i = 0; i < str.length; i++) {
                        console.log("ajax안에서 " + data.transmsg);
                        if($("#papago"+e).attr('papagoCount') == data.listid) {
                            $("#papago"+e).text(data.transmsg);
                        }
                    }
                },
                error: function(e) {
                    alert(JSON.stringify(e));
                }
            });
        }
    });
}

// 파파고 api실행 - 전체 한번에
function papagoTransAll() {
    let str = $("#contentText");

    $.ajax({
        type: "post",
        url: "/api/translateAll",
        data: {source: "ko", target: "ja", text: str.text()},
        dataType: "json",
        success: function (data) {
            console.log(data);
            str.text(data.translatedText);
        }
    });
}

// 파파고 api 실행 - 게시판 글 읽을 때
function papagoBoardDetail() {
    let str = $('.papago');

    console.log("papagoBoardDetail 진입");

    str.each(function (e) {
        let test = $("#papago"+e);
        console.log("처음 찍는 거 : " + test.text());

        if(test.text() != '') {

            $.ajax({
                type: "post",
                url: "/api/translate",
                data: { listid: e, source: "ko", target: "ja", text: $("#papago"+e).text() },
                dataType: "json",
                success: function (data) {
                    for(var i = 0; i < str.length; i++) {
                        console.log("ajax안에서 " + data.transmsg);
                        if($("#papago"+e).attr('papagoCount') == data.listid) {
                            $("#papago"+e).text(data.transmsg);
                        }
                    }
                },
                error: function(e) {
                    alert(JSON.stringify(e));
                }
            });
        }
    });
}


// 테스트
function papagoTrans2() {
    let str = $(".papago2");

    console.log("papagoTrans2 진입");

    str.each(function (e) {
        let test = $("#papagoD"+e);
        console.log("처음 찍는 거 : " + test.text());

        if(test.text() != '') {

            $.ajax({
                type: "post",
                url: "/api/translate",
                data: { listid: e, source: "ko", target: "ja", text: $("#papagoD"+e).text() },
                dataType: "json",
                success: function (data) {
                    for(var i = 0; i < str.length; i++) {
                        console.log("ajax안에서 " + data.transmsg);
                        if($("#papagoD"+e).attr('papagoCount') == data.listid) {
                            $("#papagoD"+e).text(data.transmsg);
                        }
                    }
                },
                error: function(e) {
                    alert(JSON.stringify(e));
                }
            });
        }
    });
}