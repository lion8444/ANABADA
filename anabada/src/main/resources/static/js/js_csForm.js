// csForm 관련 자바스크립트 커스텀 파일

let email_check = false;
$(document).ready(function() {
    reportedValidation();
   $('#report_reported_write').on('keyup', reportedCheck);
});
function reportedCheck() {
    let report_reported = $('#report_reported_write');
    let reported_ok = $('.reported_ok');

    $.ajax({
        url: 'reportedValidation'
        , type: 'post'
        , data: {reported: report_reported.val()}
        , dataType: 'json'
        , success: function(result) {
            if(result == 0) {
                email_check = false;
                reported_ok.html('해당 이메일은 등록되지 않은 사용자 입니다.');
                reported_ok.css('color', 'red');
                console.log($('.reported_ok').html());
                return false;
            }

            if(result == 1) {
                email_check = true;
                reported_ok.html('사용자가 확인 되었습니다.');
                reported_ok.css('color', 'green');
                console.log($('.reported_ok').html());
                return true
            }
        }
        , error: function(e) {
            alert(JSON.stringify(e));
            return false;
        }                                                       
    })
}
function reportedValidation() {
    let report_reported = $('#report_reported');
    let reported_ok = $('.reported_ok');

    $.ajax({
        url: 'reportedValidation'
        , type: 'post'
        , data: {reported: report_reported.val()}
        , dataType: 'json'
        , success: function(result) {
            if(result == 0) {
                email_check = false;
                reported_ok.html('해당 이메일은 등록되지 않은 사용자 입니다.');
                reported_ok.css('color', 'red');
                console.log($('.reported_ok').html());
                return false;
            }

            if(result == 1) {
                email_check = true;
                reported_ok.html('사용자가 확인 되었습니다.');
                reported_ok.css('color', 'green');
                console.log($('.reported_ok').html());
                return true;
            }
        }
        , error: function(e) {
            alert(JSON.stringify(e));
            return false;
        }                                                       
    })
}

// 신고하기 양식 검증
function reportValidation() {
    let category = $('#report_category').val();
    let report_reported = $('#report_reported').val();
    let report_comment = $('#report_comment').val();
    let report_url = $('#report_url').val();
    let report_ok = $('.report_ok').text();

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

    if(!email_check) {
        alert('등록되지 않은 사용자를 입력하셨습니다.');

        return false;
    }
    
    if(confirm('신고 하시겠습니까?')) {
		alert('신고가 완료되었습니다.');

		return true;
	} else {
		alert('신고가 취소 되었습니다.');


		return false;
	}

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

    if(confirm('문의 하시겠습니까?')) {
		alert('문의가 완료되었습니다.')
		return true;
	} else {
		alert('문의가 취소 되었습니다.')
		return false;
	}
};

// 후기작성 양식 검증
function reviewValidation() {
    let review_comment = $('#review_comment').val();

    if(review_comment.trim() == '' || review_comment.trim().length < 10) {
        alert('10자 이상 입력해 주세요.');

        return false;
    }

    if(confirm('후기를 등록 하시겠습니까?')) {
		alert('후기가 등록 되었습니다.');
		return true;
    } else {
        alert('후기등록이 취소 되었습니다.');
        return false;
    }
}

// 별점 처리 JQuery
const drawStar = (target) => {
    $(`.star span`).css({ width: `${target.value * 10}%` });
  }

// 이미지 처리
// var sel_files = [];
 
// $(document).ready(function() {
//     $("#btnAtt").on("change", handleImgFileSelect);
// }); 

// function fileUploadAction() {
//     console.log("fileUploadAction");
//     $("#btnAtt").trigger('click');
// }

// function handleImgFileSelect(e) {

//     // 이미지 정보들을 초기화
//     sel_files = [];
//     $(".imageZone").empty();

//     var files = e.target.files;
//     var filesArr = Array.prototype.slice.call(files);

//     var index = 0;
//     filesArr.forEach(function(f) {
//         if(!f.type.match("image.*")) {
//             alert("확장자는 이미지 확장자만 가능합니다.");
//             return;
//         }

//         sel_files.push(f);

//         var reader = new FileReader();
//         reader.onload = function(e) {
//             var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction("+index+")\" id=\"img_id_"+index+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile' title='Click to remove'></a>";
//             $(".imageZone").append(html);
//             index++;

//         }
//         reader.readAsDataURL(f);
        
//     });
// }

// function deleteImageAction(index) {            
//     console.log("index : "+index);
//     sel_files.splice(index, 1);

//     var img_id = "#btnAtt"+index;
//     $(img_id).remove();

//     console.log(sel_files);
// }        

/* 테스트 */
var fileArr;
var fileInfoArr=[];

//썸네일 클릭시 삭제.
function fileRemove(index) {
    console.log("index: "+index);
    fileInfoArr.splice(index,1);
 
    var imgId="#img_id_"+index;
    $(imgId).remove();
    console.log(fileInfoArr);
}

//썸네일 미리보기.
function previewImage(targetObj, View_area) {
    var files=targetObj.files;
    fileArr=Array.prototype.slice.call(files);
    
    var preview = document.getElementById(View_area); //div id
    var ua = window.navigator.userAgent;
 
    //ie일때(IE8 이하에서만 작동)
    if (ua.indexOf("MSIE") > -1) {
        targetObj.select();
        try {
            var src = document.selection.createRange().text; // get file full path(IE9, IE10에서 사용 불가)
            var ie_preview_error = document.getElementById("ie_preview_error_" + View_area);
 
 
            if (ie_preview_error) {
                preview.removeChild(ie_preview_error); //error가 있으면 delete
            }
 
            var img = document.getElementById(View_area); //이미지가 뿌려질 곳
 
            //이미지 로딩, sizingMethod는 div에 맞춰서 사이즈를 자동조절 하는 역할
            img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+src+"', sizingMethod='scale')";
        } catch (e) {
            if (!document.getElementById("ie_preview_error_" + View_area)) {
                var info = document.createElement("<p>");
                info.id = "ie_preview_error_" + View_area;
                info.innerHTML = e.name;
                preview.insertBefore(info, null);
            }
        }
        //ie가 아닐때(크롬, 사파리, FF)
    } else {
        var files = targetObj.files;
        for (var i = 0; i < 10; i++) {
            var file = files[i];
            fileInfoArr.push(file);
 
            var imageType = /image.*/; //이미지 파일일경우만.. 뿌려준다.
            if (!file.type.match(imageType))
                continue;
            // var prevImg = document.getElementById("prev_" + View_area); //이전에 미리보기가 있다면 삭제
            // if (prevImg) {
            //     preview.removeChild(prevImg);
            // }
 
            var span=document.createElement('span');
            span.id="img_id_" +i;
            span.style.width = '100px';
            span.style.height = '100px';
            preview.appendChild(span);
 
            var img = document.createElement("img");
            img.className="addImg";
            img.classList.add("obj");
            img.file = file;
            img.style.width='inherit';
            img.style.height='inherit';
            img.style.cursor='pointer';
            const idx=i;
            img.onclick=()=>fileRemove(idx);   //이미지를 클릭했을 때.
            span.appendChild(img);
 
            if (window.FileReader) { // FireFox, Chrome, Opera 확인.
                var reader = new FileReader();
                reader.onloadend = (function(aImg) {
                    return function(e) {
                        aImg.src = e.target.result;
                    };
                })(img);
                reader.readAsDataURL(file);
            } else { // safari is not supported FileReader
                //alert('not supported FileReader');
                if (!document.getElementById("sfr_preview_error_"
                    + View_area)) {
                    var info = document.createElement("p");
                    info.id = "sfr_preview_error_" + View_area;
                    info.innerHTML = "not supported FileReader";
                    preview.insertBefore(info, null);
                }
            }
        }
    }
}