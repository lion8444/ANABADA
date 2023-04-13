function goreview(board_id) {
    window.open("/csform/review?used_id=" + board_id, 'win', 'top=100,left=100,width=600,height=550,location=no,scrollbar=no');
}
function reviewCancle() {
    window.close();
}