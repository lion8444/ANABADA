// markers = 거래 위치 선택 마커

let map, infoWindow;

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: parseFloat($("#latitude").val()), lng: parseFloat($("#longitude").val()) },
        zoom: 17,
        minZoom: 17,
        maxZoom: 17,
        disableDefaultUI: true,
        gestureHandling: "none"
    });
    infoWindow = new google.maps.InfoWindow();
    let marker = new google.maps.Marker({
        position: map.center,
        map: map,
        title: $("#loc_name").val()
    });
    infoWindow.setContent($("#loc_name").val());
    infoWindow.open(map, marker);
}

function handleMapError(error) {
    // 구글 맵 로드 에러를 처리하는 함수입니다.
    var mapDiv = document.getElementById('map');

    if (error instanceof ReferenceError && error.message.includes('initMap is not defined')) {
        // initMap 함수를 찾을 수 없는 경우 처리합니다.
        mapDiv.innerHTML = '맵을 로드하는 동안 에러가 발생했습니다. 새로고침해주세요.';
    }
}
// 에러 이벤트 핸들러를 등록합니다.
window.addEventListener('error', handleMapError);

window.initMap = initMap;