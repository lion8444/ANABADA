// csForm 관련 자바스크립트 커스텀 파일


$(document).ready(function() {
   

});

// 신고하기 양식 검증
function reportValidation() {
    let category = $('#report_category').val();
    let report_reported = $('#report_reported').val();
    let report_comment = $('#report_comment').val();
    let report_url = $('#report_url').val();

    console.log('asdasd');

    if(category == '') {
        alert('카테고리를 설정해 주세요.')

        return false;
    }

    if(report_reported.trim() == '' || !report_reported.includes('@')) {
        alert('신고대상의 이메일을 정확히 적어주세요.')

        return false;
    }

    if(report_comment.trim() == '' || report_comment.trim().length < 10) {
        alert('빠른 답변을 위해 10자 이상 입력해 주세요.')

        return false;
    }

    if(report_url.trim() == '' || report_url == null) {
        alert('신고할 게시물의 URL을 입력해 주세요.')

        return false;
    }

    return true;
};

// 문의하기 양식 검증
function inquiryValidation() {
    let inq_category = $('#inq_category').val();
    let inq_title = $('#inq_title').val();
    let inq_content = $('#inq_content').val();
    
    if(inq_category == '') {
        alert('카테고리를 설정해 주세요.')

        return false;
    }

    if(inq_title == null || inq_title.trim() == '') {
        alert('제목을 입력해 주세요.');

        return false;
    }

    if(inq_content.trim() == '' || inq_content.trim().length < 10) {
        alert('빠른 답변을 위해 10자 이상 입력해 주세요.');

        return false;
    }

    return true;
};

// 후기작성 양식 검증
function reviewValidation() {
    let review_comment = $('#review_comment').val();

    if(review_comment.trim() == '' || review_comment.trim().length < 10) {
        alert('10자 이상 입력해 주세요.');

        return false;
    }

    return true;
}

// 별점 처리 JQuery
const drawStar = (target) => {
    $(`.star span`).css({ width: `${target.value * 10}%` });
  }

// 이미지 처리
var sel_files = [];
 
$(document).ready(function() {
    $("#btnAtt").on("change", handleImgFileSelect);
}); 

function fileUploadAction() {
    console.log("fileUploadAction");
    $("#btnAtt").trigger('click');
}

function handleImgFileSelect(e) {

    // 이미지 정보들을 초기화
    sel_files = [];
    $(".imageZone").empty();

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    var index = 0;
    filesArr.forEach(function(f) {
        if(!f.type.match("image.*")) {
            alert("확장자는 이미지 확장자만 가능합니다.");
            return;
        }

        sel_files.push(f);

        var reader = new FileReader();
        reader.onload = function(e) {
            var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction("+index+")\" id=\"img_id_"+index+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile' title='Click to remove'></a>";
            $(".imageZone").append(html);
            index++;

        }
        reader.readAsDataURL(f);
        
    });
}

function deleteImageAction(index) {            
    console.log("index : "+index);
    sel_files.splice(index, 1);

    var img_id = "#btnAtt"+index;
    $(img_id).remove();

    console.log(sel_files);
}        