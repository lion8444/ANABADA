// // 총 읽지 않은 갯수
// let countAll = 0;

// $(document).on("click", "#goChatting", function () {
//     alert($("#target").text());
//     $.ajax({
//         type: "post",
//         url: "/makeChatRoom",
//         data: { target: $("#target").text(), board_no: $("#used_id").val() },
//         success: function (data) {
//             alert(data);
//             if (data == "error") {
//                 alert("채팅 방 개설에 실패하였습니다. 다시 시도해주세요.")
//                 return;
//             } else {
//                 getRoomList();
//                 $(".chatbox-open").trigger("click");
//                 console.log(data);
//             }
//         }
//     });
// });

// $(document).on("click", ".top-bar .menu", function () {                 // - 버튼 클릭 시,
//     $(".messages").css({ 'top': '16.5%', "left": "585px" });
//     $(".profile").css({ 'top': '22%', "left": "88%" });
//     $(".people").css({ 'top': '43.7%', "left": "74%" });
//     $('.chatbox').css('display', 'none');           // 채팅방을 닫고,
//     websocket.close();                                            // socket 연결 종료
// });

// $(document).on("click", ".chatbox-close", function () {                 // - 버튼 클릭 시,
//     $(".messages").css({ 'top': '16.5%', "left": "585px" });
//     $(".profile").css({ 'top': '22%', "left": "88%" });
//     $(".people").css({ 'top': '43.7%', "left": "74%" });
//     $('.chatbox').css('display', 'none');
//     websocket.close();                                            // socket 연결 종료
// });

// // <!-- 채팅 방 관련 -->

// let roomId;
// // enter 키 이벤트
// $(document).on('keydown', '.bottom-bar .chat .chat-input', function (e) {
//     if (e.keyCode == 13 && !e.shiftKey) {
//         e.preventDefault(); // 엔터키가 입력되는 것을 막아준다.
//         const message = $(this).val();  // 현재 입력된 메세지를 담는다.

//         let search3 = $('.bottom-bar .chat .chat-input').val();

//         if (search3.replace(/\s|  /gi, "").length == 0) {
//             $('.bottom-bar .chat .chat-input').focus();
//             return false;
//         }

//         sendMessage(message);
//         // textarea 비우기
//         clearTextarea();
//     }
// });
// // 채팅 방 클릭 시 방번호 배정 후 웹소켓 연결
// function enterRoom(obj) {
//     // 현재 html에 추가되었던 동적 태그 전부 지우기
//     $('div.middle:not(.format) div.voldemort').html("");
//     // obj(this)로 들어온 태그에서 id에 담긴 방번호 추출
//     roomId = obj.getAttribute("id");
//     // 해당 채팅 방의 메세지 목록 불러오기
//     console.log("roomId"+roomId);
//     $.ajax({
//         url: '/messageList',
//         type: 'get',
//         data: {
//             roomId: roomId
//         },
//         async: false,
//         dataType: "json",
//         success: function (data) {
//             for (var i = 0; i < data.length; i++) {
//                 // 채팅 목록 동적 추가
//                 CheckLR(data[i]);
//             }
//         }
//     });
//     // 웹소켓 연결
//     connect();
//     console.log("enterRoom");
// }

// // 채팅 방 열어주기
// $(document).on("click", ".person", function () {
//     $('.chatbox').css('display', 'block');
//     $(".messages").css({ 'top': '2.5%', "left": "0%" });
//     $(".profile").css({ 'top': '7%', "left": "10%" });
//     $(".people").css({ 'top': '27.7%', "left": "-4%" });
//     $(".top-bar .name").text($(this).children('span.title').text());
//     $(".top-bar .avatar").attr("src", $(this).children('img').attr('src'));
//     $('div.middle').scrollTop($('div.middle').prop('scrollHeight'));
//     // 로그인 상태 일 때 
//     // if ($(this).hasClass('chatList_box')) {
//     //     // 점 표시
//     //     $('#loginOn').addClass('profile_img_Container');
//     // }
//     // // 로그아웃 상태 일 때
//     // else {
//     //     // 점 빼기
//     //     $('#loginOn').removeClass('profile_img_Container');
//     // }
// });

// // 웹소켓
// let websocket;

// //입장 버튼을 눌렀을 때 호출되는 함수
// function connect() {
//     // 웹소켓 주소
    
//     // wifi 7
//     // var wsUri = "ws://10.10.7.77:80/ws/chat";
//     // wifi 8
//     // var wsUri = "ws://10.10.8.90:80/ws/chat";
//     // wifi 17
//     // var wsUri = "ws://10.10.17.155:80/ws/chat";
//     var wsUri = "ws://localhost:80/ws/chat";
//     // 소켓 객체 생성
//     websocket = new WebSocket(wsUri);
//     //웹 소켓에 이벤트가 발생했을 때 호출될 함수 등록
//     websocket.onopen = onOpen;
//     websocket.onmessage = onMessage;
// }

// // 웹 소켓에 연결되었을 때 호출될 함수
// function onOpen() {
//     // ENTER-CHAT 이라는 메세지를 보내어, Java Map에 session 추가
//     countAll = 0;
//     const data = {
//         "roomId": roomId,
//         "sender": $("#user_nick").val(),
//         "message": "ENTER-CHAT"
//     };
//     let jsonData = JSON.stringify(data);
//     websocket.send(jsonData);
// }

// // * 1 메시지 전송
// function sendMessage(message) {
//     let today = new Date();
//     let apm = "AM";
//     if (today.getHours() >= 12) {
//         apm = "PM"
//     }
//     let chattime = today.getHours() + ":" + today.getMinutes() + " " + apm;
//     console.log(chattime);

//     const data = {
//         "roomId": roomId,
//         "sender": $("#user_nick").val(),
//         "message": message,
//         "chattime": chattime
//     };

//     CheckLR(data);

//     let jsonData = JSON.stringify(data);

//     websocket.send(jsonData);
// }

// // * 2 메세지 수신
// function onMessage(evt) {

//     console.log(evt);
//     let receive = evt.data.split(",");

//     const data = {
//         "roomId": receive[0],
//         "sender": receive[1],
//         "message": receive[2],
//         "chattime": receive[3]
//     };

//     if (data.sender != $("#user_nick").val()) {
//         CheckLR(data);
//         // previewMessage(data);
//     }
// }

// // * 2-1 추가 된 것이 내가 보낸 것인지, 상대방이 보낸 것인지 확인하기
// function CheckLR(data) {
//     // userid이 loginSession의 userid과 다르면 왼쪽, 같으면 오른쪽
//     const LR = (data.sender != $("#user_nick").val()) ? "left" : "right";
//     // console.log(data.sender);
//     // console.log($("#user_nick").val());
//     // 메세지 추가
//     console.log(data.sender);
//     console.log(data.message);
//     console.log(data.chattime);
//     if (!isEmpty(data.message) || !isEmpty(data.chattime)) {
//         appendMessageTag(LR, data.message, data.chattime);
//     }
// }

// // * 3 메세지 태그 append
// function appendMessageTag(LR_className, message, chattime) {

//     const chatLi = createMessageTag(LR_className, message, chattime);

//     $('div.middle:not(.format) div.voldemort').append(chatLi);

//     // 스크롤바 아래 고정
//     $('div.middle').scrollTop($('div.middle').prop('scrollHeight'));
// }

// // * 4 메세지 태그 생성
// function createMessageTag(LR_className, message, chattime) {

//     // 형식 가져오기
//     let chatLi = $('div.middle.format div.voldemort div.messageText').clone();

//     chatLi.addClass(LR_className);              // left : right 클래스 추가
//     // find() : chatLi의 하위 요소 찾기
//     // chatLi.find('.sender span').text(sender);      // 이름 추가
//     chatLi.find('.bubble').text(message); // 메세지 추가
//     chatLi.find('.chattime').text(chattime); // 메세지 추가

//     return chatLi;
// };

// // * 5 - 채팅창 비우기
// function clearTextarea() {
//     $('.bottom-bar .chat .chat-input').val('');
//     return false;
// };

// // <!-- 채팅 목록 관련 -->
// function getRoomList() {
//     // 채팅 방 목록 가져오기
//     $.ajax({
//         url: "/chatList",
//         type: 'post',
//         dataType: "json",
//         async: false, // async : false를 줌으로써 비동기를 동기로 처리 할 수 있다.
//         success: function (data) {
//             console.log(data);
//             $.each(data, function (i, n) {
//                 console.log(n);
//                 console.log(i);
//             });
//             // 현재 로그인 된 User들
//             let loginList = "";

//             // 로그인 된 User들을 가져온다.
//             $.ajax({
//                 url: "/chatSession",
//                 type: 'post',
//                 async: false,
//                 dataType: "json",
//                 success: function (data) {
//                     console.log(data);
//                     for (var i = 0; i < data.length; i++) {
//                         loginList += data[i];
//                     }

//                 }
//             });
//             $chatWrap = $(".people");
//             $chatWrap.html("");

//             var $div;     // 1단계
//             var $img;     // 2단계
//             var $name;     // 2단계
//             var $read;    // 2단계

//             if (data.length > 0) {
//                 // 읽지 않은 메세지 초기화
//                 countAll = 0;

//                 // 태그 동적 추가
//                 for (var i in data) {

//                     // 자신이 구매자 입장일 때
//                     if (data[i].opener == $("#user_nick").val()) {
//                         // 현재 판매자가 로그인 상태 일 때
//                         if (loginList.indexOf(data[i].target) != -1) {
//                             // $div = $("<div class='chatList_box enterRoomList' onclick='enterRoom(this);'>").attr("id", data[i].roomId).attr("userid", data[i].target);
//                             $div = $("<li class='chatList_box person' onclick='enterRoom(this);'>").attr("id", data[i].roomId).attr("target", data[i].target);
//                         }
//                         // 현재 판매자가 로그아웃 상태 일 때
//                         else {
//                             $div = $("<li class='chatList_box2 person' onclick='enterRoom(this);'>").attr("id", data[i].roomId).attr("target", data[i].target);
//                         }
//                         $img = $("<img class='avatar'>").attr("src", "/img/352479_language_icon.png");
//                         $name = $("<span class='title'>").text(data[i].target);
//                     }
//                     // 자신이 판매자 입장일 때
//                     else {
//                         // 현재 구매자가 로그인 상태 일 때
//                         if (loginList.indexOf(data[i].userid) != -1) {
//                             $div = $("<li class='chatList_box person' onclick='enterRoom(this);'>").attr("id", data[i].roomId).attr("target", data[i].opener);
//                         }
//                         // 현재 구매자가 로그아웃 상태 일 때
//                         else {
//                             $div = $("<li class='chatList_box2 person' onclick='enterRoom(this);'>").attr("id", data[i].roomId).attr("target", data[i].opener);
//                         }
//                         $img = $("<img class='profile_img'>").attr("src", "/img/352479_language_icon.png");
//                         $name = $("<span class='title'>").text(data[i].name);
//                     }

//                     $read = $("<span class='preview'>").text(data[i].target);

//                     $div.append($img);
//                     $div.append($name);
//                     $div.append($read);

//                     $chatWrap.append($div);

//                     // String을 int로 바꿔주고 더해준다.
//                     countAll += parseInt(data[i].unReadCount);
//                 }
//             }
//         }
//     });
// }

// // 화면 로딩 된 후
// $(window).on('load', function () {

//     // 2초에 한번씩 채팅 목록 불러오기(실시간 알림 전용)
//     // setInterval(function () {
//     // 방 목록 불러오기
//     getRoomList();

//     // 읽지 않은 메세지 총 갯수가 0개가 아니면
//     if (countAll != 0) {
//         // 채팅 icon 깜빡거리기
//         $('.chatIcon').addClass('iconBlink');
//         // play();
//     } else {
//         // 깜빡거림 없애기
//         $('.chatIcon').removeClass('iconBlink');
//     }
//     // }, 1000);
// });

// function isEmpty(str) {

//     if (typeof str == "undefined" || str == null || str == "")
//         return true;
//     else
//         return false;
// }