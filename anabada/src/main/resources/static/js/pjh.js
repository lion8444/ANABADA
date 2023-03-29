
// 페이지 이동 스크립트 -->

function pagingFormSubmit(currentPage){
    let form = document.getElementById('pagingForm');
    let page = document.getElementById('page');
    page.value = currentPage;
    form.submit();
}

// 사는 글 삭제 스크립트

    function usedSellBoardDelete(n){
        if(confirm('삭제하시겠습니까?')){
        location.href='usedSellBoardDelete?used_id='+n;
        }
    }

//체크박스 하나만 선택되게
function rental_quality(chk){
    var obj = document.getElementsByName("aaa");
    for(var i=0; i<obj.length; i++){
        if(obj[i] != chk){
            obj[i].checked = false;
        }
    }
}

//체크박스 하나만 선택되게
function auction_quality(chk){
    var obj = document.getElementsByName("aaa");
    for(var i=0; i<obj.length; i++){
        if(obj[i] != chk){
            obj[i].checked = false;
        }
    }
}

//현재날짜

  document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);;

    

