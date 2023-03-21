
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
    

