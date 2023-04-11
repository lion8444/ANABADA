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
	insertdate();
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
  
function pagingFormSubmit(currentPage, check) {
		let type = $('.sstatus').html();
		let searchWord = document.getElementById('searchWord').value;
		let page = document.getElementById('page');
		let fsdate = $('.fsdate').val();
		let fedate = $('.fedate').val();
		if(fsdate == "" || fedate == ""){
			alert("렌탈 날짜를 입력해 주세요.");
	    	return false;
		}
		page.value = currentPage;
		if(check == null){
			check = $('.dcheck').html();
		}  		
  		location.href = "rentalBoard?type=" + type + "&searchWord=" + searchWord +"&page=" 
  						+ currentPage +"&check=" + check + "&fsdate=" + fsdate + "&fedate=" + fedate;
	}
	
function statuscheck(vcheck){
	$('.sstatus').html(vcheck);
	}

function insertdate(){
	var today = new Date();
	var endday = new Date(today);
	endday.setDate(today.getDate() + 6);

	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);
	var lastday = ('0' + endday.getDate()).slice(-2);

	var dateString = year + '-' + month  + '-' + day;
	var enddateString = year + '-' + month  + '-' + lastday;

	if($('.fsdate').val() =="" | $('.fedate').val() ==""){
			$('.fsdate').val(dateString);
			$('.fedate').val(enddateString);
			$(".findbutton").trigger('click');
	}
}
