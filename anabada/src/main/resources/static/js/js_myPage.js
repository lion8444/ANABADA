// myPage 관련 자바스크립트 커스텀 파일

$(document).ready(function() {

    let inquirelist = $('.inquirelist');
    let inq_answer = $('.inq_answer');
    
    inquirelist.on('click', function() {
        $(this).next('.inq_answer').css('display', 'block');
    });

    inq_answer.on('click', function() {
        $(this).css('display', 'none');
    });

    // inquirelist.on('click', function() {
    //     $('.inq_answer').toggle('show');
    // });

   
});