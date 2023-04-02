// 로그인관련 드랍다운 액션 재활용용
// window.onload = function() {
//     var dropdownMenuLink = document.getElementById("dropdownMenuLink");
//     var dropdownMenu = document.getElementById("dropdownMenu");
//     var timeoutId;

//     dropdownMenuLink.addEventListener("mouseover", function(event) {
//         clearTimeout(timeoutId);
//         dropdownMenu.style.display = "block";
//     });

//     dropdownMenuLink.addEventListener("mouseout", function(event) {
//         timeoutId = setTimeout(function() {
//             dropdownMenu.style.display = "none";
//         }, 300); // 0.3초 대기 후에 메뉴 닫기
//     });

//     dropdownMenu.addEventListener("mouseover", function(event) {
//         clearTimeout(timeoutId);
//         dropdownMenu.style.display = "block";
//     });

//     dropdownMenu.addEventListener("mouseout", function(event) {
//         timeoutId = setTimeout(function() {
//             dropdownMenu.style.display = "none";
//         }, 300); // 0.3초 대기 후에 메뉴 닫기
//     });
// };


//used 메뉴 드랍다운 재활용용
document.addEventListener("DOMContentLoaded", function() {
    const dropdown = document.querySelector(".nav-item.dropdown");
  
    dropdown.addEventListener("mouseover", function() {
      this.classList.add("show");
      const dropdownMenu = this.querySelector(".dropdown-menu");
      dropdownMenu.classList.add("show");
    });
  
    dropdown.addEventListener("mouseout", function() {
      this.classList.remove("show");
      const dropdownMenu = this.querySelector(".dropdown-menu");
      dropdownMenu.classList.remove("show");
    });
  });



//이 코드는 다 되긴하는데 used가 마우스오버가 아니라 클릭해야 열림 위에거랑 같이 쓰면 되긴됨 재활용용
// window.onload = function() {
//     const usedDropdown = document.getElementById('usedDropdown');
//     const usedMenu = document.querySelector('#usedDropdown + .dropdown-menu');
//     const langDropdown = document.getElementById('dropdownMenuButton-lang');
//     const langMenu = document.querySelector('#dropdownMenuButton-lang + .dropdown-menu');
  
//     usedDropdown.addEventListener('click', function(event) {
//       event.preventDefault();
//       usedMenu.classList.toggle('show');
//     });
  
//     langDropdown.addEventListener('click', function(event) {
//       event.preventDefault();
//       langMenu.classList.toggle('show');
//     });
  
//     const dropdownMenuLink = document.getElementById("dropdownMenuLink");
//     const dropdownMenu = document.getElementById("dropdownMenu");
//     let timeoutId;
  
//     dropdownMenuLink.addEventListener("mouseover", function(event) {
//       clearTimeout(timeoutId);
//       dropdownMenu.style.display = "block";
//     });
  
//     dropdownMenuLink.addEventListener("mouseout", function(event) {
//       timeoutId = setTimeout(function() {
//         dropdownMenu.style.display = "none";
//       }, 300); // 0.3초 대기 후에 메뉴 닫기
//     });
  
//     dropdownMenu.addEventListener("mouseover", function(event) {
//       clearTimeout(timeoutId);
//       dropdownMenu.style.display = "block";
//     });
  
//     dropdownMenu.addEventListener("mouseout", function(event) {
//       timeoutId = setTimeout(function() {
//         dropdownMenu.style.display = "none";
//       }, 300); // 0.3초 대기 후에 메뉴 닫기
//     });
//   };



//세개의 드랍다운이 다 되게 해놓은것
window.onload = function() {
    const usedDropdown = document.getElementById('usedDropdown');
    const usedMenu = document.querySelector('#usedDropdown + .dropdown-menu');
    const langDropdown = document.getElementById('dropdownMenuButton-lang');
    const langMenu = document.querySelector('#dropdownMenuButton-lang + .dropdown-menu');
  
    usedDropdown.addEventListener('mouseenter', function(event) {
      event.preventDefault();
      usedMenu.classList.add('show');
    });
  
    usedDropdown.addEventListener('mouseleave', function(event) {
      event.preventDefault();
      timeoutId = setTimeout(function() { // timeoutId 변수 추가
        usedMenu.classList.remove('show'); // 메뉴 숨기기
      }, 200); // 0.2초 대기 후에 메뉴 닫기
    });
  
    usedMenu.addEventListener('mouseenter', function(event) {
      clearTimeout(timeoutId);
    });
  
    usedMenu.addEventListener('mouseleave', function(event) {
      timeoutId = setTimeout(function() { // timeoutId 변수 추가
        usedMenu.classList.remove('show'); // 메뉴 숨기기
      }, 200); // 0.2초 대기 후에 메뉴 닫기
    });
  
    langDropdown.addEventListener('click', function(event) {
      event.preventDefault();
      langMenu.classList.toggle('show');
    });
  
    const dropdownMenuLink = document.getElementById("dropdownMenuLink");
    const dropdownMenu = document.getElementById("dropdownMenu");
    let timeoutId;
  
    dropdownMenuLink.addEventListener("mouseover", function(event) {
      clearTimeout(timeoutId);
      dropdownMenu.style.display = "block";
    });
  
    dropdownMenuLink.addEventListener("mouseout", function(event) {
      timeoutId = setTimeout(function() {
        dropdownMenu.style.display = "none";
      }, 200);
    });
  
    dropdownMenu.addEventListener("mouseover", function(event) {
      clearTimeout(timeoutId);
      dropdownMenu.style.display = "block";
    });
  
    dropdownMenu.addEventListener("mouseout", function(event) {
      timeoutId = setTimeout(function() {
        dropdownMenu.style.display = "none";
      }, 200);
    });
  };

  




function pagingFormSubmit(currentPage) {
  let form = $('#pagingForm');
  let page = $('#page');
  page.val(currentPage);
  console.log(currentPage)

  location.href = "auctionBoard?page=" + currentPage;

}


function showDraftList() {
  // 로컬 스토리지에서 모든 임시저장 데이터를 가져옵니다.
  var drafts = Object.entries(localStorage).filter(([key, value]) => key.startsWith('blog_draft_'))

  // 임시저장 목록을 보여줄 리스트 엘리먼트를 생성합니다.
  var list = document.createElement("ul");

  // 각각의 임시저장 데이터를 처리합니다.
  drafts.forEach(function([key, value]) {
    // 임시저장 데이터에서 블로그 제목을 가져옵니다.
    var title = value.split("|||")[0];

    // 목록에 추가할 리스트 아이템 엘리먼트를 생성합니다.
    var listItem = document.createElement("li");

    // 리스트 아이템의 텍스트 내용을 설정합니다.
    var text = document.createTextNode(title);
    listItem.appendChild(text);

    // 리스트 아이템을 클릭할 때 해당 임시저장 데이터를 불러오는 이벤트 핸들러를 등록합니다.
    listItem.addEventListener("click", function() {
      // 해당 임시저장 데이터를 가져와서 입력폼에 채워줍니다.
      var content = value.split("|||")[1];
      document.getElementById("title").value = title;
      document.getElementById("content").value = content;
    });

    // 리스트 아이템을 리스트에 추가합니다.
    list.appendChild(listItem);
  });

  // 임시저장 목록을 보여줄 팝업을 생성합니다.
  var popup = document.createElement("div");
  popup.style.position = "absolute";
  popup.style.zIndex = "999";
  popup.style.top = "100%";
  popup.style.left = "0";
  popup.style.backgroundColor = "#f1f1f1";
  popup.style.border = "1px solid #d3d3d3";
  popup.style.padding = "10px";
  popup.appendChild(list);

  // 팝업을 body에 추가합니다.
  document.body.appendChild(popup);
}

function saveDraft() {
  // 제목과 내용을 가져옵니다.
  var title = document.getElementById("title").value;
  var content = document.getElementById("content").value;

  // 로컬 스토리지에 저장합니다.
  var timestamp = Date.now();
  var key = "blog_draft_" + timestamp;
  localStorage.setItem(key, title + "|||" + content);

  // 임시저장 목록을 보여줍니다.
  showDraftList();
}






  
  
  