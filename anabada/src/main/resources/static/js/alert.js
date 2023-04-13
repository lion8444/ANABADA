let bottom_endNotify = null;
let centerNotify = null;
let confirmNotify = null;

function sleep(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms))
}

function alertValue() {
    bottom_endNotify = Swal.mixin({
        toast: true,
        position: "bottom-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true
    });
    
    centerNotify = Swal.mixin({
        position: 'center',
        showConfirmButton: false,
        timer: 1500   
    });
    
    confirmNotify = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger'
          },
          buttonsStyling: false
    });
    
}

function loginSuccess() {
    bottom_endNotify.fire({
        icon: "success",
        title: "로그인 완료"
    });
}

function loginError() {
    bottom_endNotify.fire({
        icon: "error",
        title: "로그인 실패"
    });
}

async function joinError() {
    centerNotify.fire({
        icon: "error",
        title: "회원 가입 실패",
        text: "회원 가입에 실패하였습니다. 메인페이지로 이동합니다."
    });
    await sleep(1500);
    location.replace("/");
}

async function updateError() {
    centerNotify.fire({
        icon: "error",
        title: "회원 수정 실패",
        text: "메인 페이지로 이동합니다."
    });
    await sleep(1500);
    location.replace("/");
}

function auctionNotify() {
    confirmNotify.fire({
        title: '경매 입찰에 성공했습니다.',
        text: "경매 마감 1일 전 가입하신 메일로 알림을 보냅니다.",
        icon: 'info',
        showCancelButton: false,
        confirmButtonText: '네',
        // cancelButtonText: '아니오'
    }).then(async (result) => {
        if (result.isConfirmed) {
            centerNotify.fire(
                '수신 동의',
                // '입찰가 변동, 경매 마감 1일 전 알람을 보냅니다.',
                'success'
            )
            await sleep(1500);
            location.replace("/");
        } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
        ) {
            centerNotify.fire(
                '수신 거부',
                '수신을 거부하셨습니다. 언제든 MyPage 내 경매내역에서 설정하실 수 있습니다.',
                'error'
            )
        }
    });
}


function joinNotify() {
    confirmNotify.fire({
        title: '회원 가입 성공.',
        text: "로그인 페이지로 이동하시겠습니까?",
        icon: 'success',
        showCancelButton: true,
        confirmButtonText: '네',
        cancelButtonText: '아니오'
    }).then(async (result) => {
        if (result.isConfirmed) {
            centerNotify.fire({
                icon: 'info',
                title: '로그인 페이지로 이동합니다.'
            });
            await sleep(1500);
            location.href = "/login";
        } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
        ) {
            centerNotify.fire({
                icon: 'warning',
                title: '메인 페이지로 이동합니다.'
            });
            await sleep(1500);
            location.href = "/";
        }
    });
}

function updateNotify() {
    confirmNotify.fire({
        title: '회원 수정 성공.',
        text: "MyPage로 이동하시겠습니까?",
        icon: 'success',
        showCancelButton: true,
        confirmButtonText: '네',
        cancelButtonText: '아니오'
    }).then(async (result) => {
        if (result.isConfirmed) {
            centerNotify.fire({
                icon: 'info',
                title: 'MyPage로 이동합니다.'
            });
            await sleep(1500);
            location.href = "/mypage";
        } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
        ) {
            centerNotify.fire({
                icon: 'warning',
                title: '메인 페이지로 이동합니다.'
            });
            await sleep(1500);
            location.href = "/";
        }
    });
}

// export {bottom_endNotify,centerNotify,confirmNotify};
// export {loginError, loginSuccess, auctionNotify, joinNotify};