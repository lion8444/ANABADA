/**
 * 위시리스트 JS파일
 */

$(document).ready(function() {
    // 찜 추가 클릭
    $('#insertWish').on('click', insertWishGo);

    // 찜 삭제 클릭
    $('#deleteWish').on('click', deleteWishGo)

});

function insertWishGo() {

    let insertWish = $('#insertWish');

    let boardno = insertWish.attr('boardNo');
    let email = insertWish.attr('email');

    $.ajax({
        type: "post",
        url: "/wish/insertWish",
        data: {boardno: boardno, email: email},
        success: function () {
            alert("찜 목록에 추가 되었습니다!");
            location.reload();
        },
        error: function(e) {
            alert("찜 목록 추가에 실패하였습니다!\n다시 이용해 주세요!");
        }
    });
}

function deleteWishGo() {
    let deleteWish = $('#deleteWish');

    let boardno = deleteWish.attr('boardNo');
    let email = deleteWish.attr('email');

    $.ajax({
        type: "post",
        url: "/wish/deleteWish",
        data: {boardno: boardno, email: email},
        success: function () {
            alert("찜 목록에서 삭제 되었습니다!");
            location.reload();
        },
        error: function(e) {
            alert("찜 목록 삭제에 실패하였습니다!\n다시 이용해 주세요!");
        }
    });
}