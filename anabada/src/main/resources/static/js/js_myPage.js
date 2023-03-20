// myPage 관련 자바스크립트 커스텀 파일

$(document).ready(function() {

    let inquirelist = $('#inquirebox .inquirelist');
    let inq_answer = $('#inquire .inq_answer');

	// $('.inquirelist').on('click', showReply);

    // $('.inq_answer').on('click', hideReply);
});

function showReply() {
    $(this).slideDown();
    // $('.inq_answer').slideDown();
}

function hideReply() {
    $(this).slideUp();
}