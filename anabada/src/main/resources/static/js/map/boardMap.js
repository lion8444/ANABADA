// markers = 거래 위치 선택 마커

let map, infoWindow_contents = [], markers = [];
let info_cnt = 0;
let infoWindow;

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 37.574187, lng: 126.976882 },
        zoom: 17,
        minZoom: 17,
        maxZoom: 17,
        disableDefaultUI: true,
    });
    infoWindow = new google.maps.InfoWindow();

    autocomplete();

    geolocation();
    map.addListener("click", (event) => {
        addMarker(event.latLng);
        codeCoordinate(event.latLng);
    });
    $("#search-button").on("click", searchMap);

}

// 자동완성
function autocomplete() {
    const input = document.getElementById("search");
    const searchBox = new google.maps.places.SearchBox(input);

    map.addListener("bounds_changed", () => {
        searchBox.setBounds(map.getBounds());
    });

    searchBox.addListener("places_changed", () => {
        const places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }
        // For each place, get the icon, name and location.
        const bounds = new google.maps.LatLngBounds();
        places.forEach((place) => {
            if (!place.geometry || !place.geometry.location) {
                console.log("Returned place contains no geometry");
                return;
            }

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }
        });
        map.fitBounds(bounds);
    });
}

// 검색
function searchMap() {
    var query = document.getElementById('search').value;
    if (!query) {
        alert('검색어를 입력해주세요.');
        return;
    }
    service = new google.maps.places.PlacesService(map);
    service.textSearch({ query: query }, function (results, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {
            const place = results.find(result => result.name === query);
            if (place) {
                createMarker(place);
            } else {
                alert("일치하는 검색 결과가 없습니다. 검색창에서 Enter키를 입력하여 비슷한 위치 결과를 찾아주세요.");
            }
        }
    });
}

function createMarker(place) {
    deleteMaker(null);
    console.log(place); // 위치 정보 출력
    var marker = new google.maps.Marker({
        position: place.geometry.location,
        map: map,
        title: place.name
    });
    map.panTo(marker.position);
    markers.push(marker);
    infoWindow.setContent(place.name);
    infoWindow.open(map, marker);
    let geocoder = new google.maps.Geocoder();
    // 좌표를 받아 reverse geocoding(좌표를 주소로 바꾸기)를 실행
    geocoder.geocode({ 'latLng': place.geometry.location }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                latLngSubmitReady(place.geometry.location, results[1].formatted_address);
            }
        }
    });

}
// 검색 끝

function geolocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                };

                map.setCenter(pos);
                addMarker(pos);
                codeCoordinate(pos);

            },
            () => {
                handleLocationError(true, infoWindow, map.getCenter());
            }
        );
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(
        browserHasGeolocation
            ? "Error: The Geolocation service failed."
            : "Error: Your browser doesn't support geolocation."
    );
    infoWindow.open(map);
}


function addMarker(position) {
    deleteMaker(null);
    const marker = new google.maps.Marker({
        position,
        map,
    });
    map.panTo(marker.position);
    markers.push(marker);
}
function deleteMaker(map) {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
    markers = [];
}

//클릭 이벤트 발생 시 그 좌표를 주소로 변환하는 함수
function codeCoordinate(latLng) {
    let geocoder = new google.maps.Geocoder();
    // 좌표를 받아 reverse geocoding(좌표를 주소로 바꾸기)를 실행
    geocoder.geocode({ 'latLng': latLng }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                console.log(results[1]);
                infoWindow.setContent(results[1].formatted_address);     //infowindow로 주소를 표시
                infoWindow.open(map, markers[0]);
                latLngSubmitReady(latLng, results[1].formatted_address);
            }
        }
    });
}

function latLngSubmitReady(latLng, addr) {
    // let name = JSON.stringify(addr);
    let xy = JSON.stringify(latLng);
    let location = JSON.parse(xy);
    $("#latitude").val(location.lat);
    $("#longitude").val(location.lng);
    $("#loc_name").val(addr);
}
function handleMapError(error) {
    // 구글 맵 로드 에러를 처리하는 함수입니다.
    var mapDiv = document.getElementById('map');

    if (error instanceof ReferenceError && error.message.includes('initMap is not defined')) {
        // initMap 함수를 찾을 수 없는 경우 처리합니다.
        mapDiv.innerHTML = '맵을 로드하는 동안 에러가 발생했습니다. 새로고침해주세요.';
        $("#search").css("display", "none");
        $("#search-button").css("display", "none");
    }
}
// 에러 이벤트 핸들러를 등록합니다.
window.addEventListener('error', handleMapError);

window.initMap = initMap;