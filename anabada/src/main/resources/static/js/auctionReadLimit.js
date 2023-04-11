$(document).ready(function(){
    let boardDate = $('#auction_finish').data('time'); // data-time 값을 가져옴

    var today = new Date();
    var year = today.getFullYear();
    var month = ('0' + (today.getMonth() + 1)).slice(-2);
    var day = ('0' + today.getDate()).slice(-2);
    var hours = ('0' + today.getHours()).slice(-2);
    var minutes = ('0' + today.getMinutes()).slice(-2);
    var seconds = ('0' + today.getSeconds()).slice(-2);
    var nowDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;

    console.log("데이터 타임: " + boardDate);
    console.log("현재 타임: " + nowDate);
  
    if (boardDate < nowDate) {
      alert("해당 게시물은 이미 경매 완료된 게시물 입니다.\n게시물 이용이 불가능합니다.");
      window.location.href = "auctionBoard"; // 이동할 수 없는 페이지로 이동
    }
  });   