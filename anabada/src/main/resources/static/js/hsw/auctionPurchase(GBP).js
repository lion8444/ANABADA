        $(document).ready(function () {
            nowprice();
            setInterval(nowprice, 1000);
            pricecal();
            setInterval(pricecal, 1000);
            $('.myaddr').change(myaddr);
            $('.newaddr').change(newaddr);
            $(".showbid_price").change(pricechangecom);
            $(".showbid_price").keydown(write);
            $(".showbid_price").change(writecom);
            $('.post').click(sample6_execDaumPostcode);
            $('#purchaseform').submit(submitcheck);
        });

        /**
            현재 최고 입찰금액 가져와서 해당 자리에 넣기
        */
        // function nowprice() {
        //     $.ajax({
        //         url: 'nowprice'
        //         , type: 'get'
        //         , data: { 'auction_id': $('.auction_id').val() }
        //         , success: function (price) {
        //             $('.nowprice').html('₩ '+ comma(price));
        //             $('.renowprice').val(price);
                    
        //         }
        //         , error: function () {
        //             alert("입찰금액?");
        //         }
        //     });
        // }

        function nowprice() {
            $.ajax({
              url: 'nowprice',
              type: 'get',
              data: { 'auction_id': $('.auction_id').val() },
              success: function (price) {
                // H3 태그에 현재 가격을 출력합니다.
                $('.nowprice').html('₩ '+ comma(price));
                
                // INPUT 태그에 현재 가격을 출력합니다.
                $('.nowprice-input').val('₩ '+ comma(price));
                // Span 태그에 현재 가격을 출력합니다.
                $('.nowprice-span').html('₩ ' + comma(price));
                // 현재 가격을 저장합니다.
                $('.renowprice').val(price);
              },
            //   error: function () {
            //     alert("입찰금액?");
            //   }
            });
          }

        /**
            입찰 남은 기간 계산해서 해당 자리에 넣기
        */
        function pricecal() {
            let today = new Date();

            let year = today.getFullYear();
            let month = ('0' + (today.getMonth() + 1)).slice(-2);
            let day = ('0' + today.getDate()).slice(-2);
            let hours = ('0' + today.getHours()).slice(-2);
            let minutes = ('0' + today.getMinutes()).slice(-2);
            let seconds = ('0' + today.getSeconds()).slice(-2);

            let timeString = year + '-' + month + '-' + day + " " + hours + ':' + minutes + ':' + seconds;

            $('.auction_date').val(timeString);

            let edate = new Date($('.auction_finish').val()).getTime()
            let sdate = new Date($('.auction_date').val()).getTime()
            let count = edate - sdate;

            let countsec = (count / (1000)) % 60;
            let countmin = Math.floor(count / (1000 * 60) % 60);
            let counthour = Math.floor(count / (1000 * 60 * 60) % 24);
            let countday = Math.floor(count / (1000 * 60 * 60 * 24) % 30);

            $('.restdate').val(countday + "일 " + counthour + "시간 " + countmin + "분 " + countsec + "초 ");
        }


        /**
            숫자 천자리 마다 , 넣기
        */
        function comma(str) {
            str = String(str);
            return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
        }

        /**
            내 주소 선택시 해당 내용 db에서 불러와서 넣기
        */
        function myaddr() {
            $.ajax({
                url: 'myaddr'
                , type: 'get'
                , dataType: 'json'
                , success: function (userone) {
                    $('.inname').val(userone.user_name);
                    $('.phone').val(userone.user_phone);
                    $('.post').val(userone.user_post);
                    $('.addr1').val(userone.user_addr1);	
                    $('.addr2').val(userone.user_addr2);

                }
                , error: function () {
                    alert("실패");
                }

            });
        }
        /**
            신규 배송지 선택시 해당 내용 자리 비우기
        */
        function newaddr() {
            $('.inname').val("");
            $('.phone').val("");
            $('.post').val("");
            $('.addr1').val("");
            $('.addr2').val("");
        }

        /**
            입찰 금액에 작성한 내용에 , 붙이기
        */
        function pricechangecom() {
            $('.showbid_price').val($(this).val().replace(/,/g, ''));
            $('.bid_price').val($(this).val().replace(/,/g, ''));
            $('.showbid_price').val(comma($(this).val()));

        }

        /**
            입찰 금액에 작성한 내용을 사용 금액에 자동으로 넣기
        */
        function write() {
            $('.money').val($(this).val().replace(/,/g, ''));
            $('.showmoney').val(comma($(this).val()));
        }

        /**
            입찰 금액에 작성한 내용을 사용 금액에 자동으로 넣기
        */
        function writecom() {
            $('.money').val($(this).val().replace(/,/g, ''));
            $('.showmoney').val(comma($(this).val()));

        }

        /**
            submit 클릭 시 데이터 무결성 확인
        */
        function submitcheck() {
            let check = /[0-9]{11,11}$/;
            let numcheck = /[0-9]$/;

            if ((!numcheck.test($('.bid_price').val())) || Number($('.renowprice').val()) >= Number($('.bid_price').val())) {
                $('.bid_price ~ div').css("display", "block");
                return false;
            } else {
                $('.bid_price ~ div').css("display", "none");
            }


            if ($('.inname').val().trim() == "") {
                $('.inname ~ div').css("display", "block");
                return false;
            } else {
                $('.inname ~ div').css("display", "none");
            }

            if ($('.phone').val() == "" || !check.test($('.phone').val())) {
                $('.phone ~ div').css("display", "block");
                return false;
            } else {
                $('.phone ~ div').css("display", "none");
            }

            if ($('.post').val() == "") {
                $('.post ~ div').css("display", "block");
                return false;
            } else {
                $('.post ~ div').css("display", "none");
            }

            if (Number($('.money').val()) > Number($('.much').val())) {
                $('.money ~ div').css("display", "block");
                return false;
            } else {
                $('.money ~ div').css("display", "none");
            }


            if (!$('.agree').is(":checked")) {
                $('.agree ~ div').css("display", "block");
                return false;
            } else {
                $('.agree ~ div').css("display", "none");
            }

        }


        /*<!-- 다음 우편번호 검색 -->*/

        function sample6_execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function (data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }

                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                    if (data.userSelectedType === 'R') {
                        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                        if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                            extraAddr += data.bname;
                        }
                        // 건물명이 있고, 공동주택일 경우 추가한다.
                        if (data.buildingName !== '' && data.apartment === 'Y') {
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                        if (extraAddr !== '') {
                            extraAddr = ' (' + extraAddr + ')';
                        }
                        // 조합된 참고항목을 해당 필드에 넣는다.

                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('sample6_postcode').value = data.zonecode;
                    document.getElementById("sample6_address").value = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("sample6_detailAddress").focus();
                }
            }).open();
        }
            
    function charge(){
	window.open('charge', '충전 페이지', "width=1000, height=500");
	}