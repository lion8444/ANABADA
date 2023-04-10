// markers = 거래 위치 선택 마커

let map, infoWindow, markers = [];
// , marker;
let locations = [];
let latitude, longitude, loc_name;
function initMap() {
    

    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 37.574187, lng: 126.976882 },
        zoom: 17,
        minZoom: 10,
        maxZoom: 20
    });
    infoWindow = new google.maps.InfoWindow();

    latitude = $("#latitude");
    longitude = $("#longitude");
    loc_name = $("#loc_name");

    deleteMaker(null);
    if (!latitude.length || latitude.val() == "" || longitude.val() == "" || loc_name.val() == "") {
        geolocation();
    }
    else {
        let latLng = { lat: parseFloat(latitude.val()), lng: parseFloat(longitude.val()) };
        map.setCenter(latLng);
        let marker = new google.maps.Marker({
            position: latLng,
            map: map,
            title: loc_name.val()
        });
        infoWindow.setContent(loc_name.val());
        infoWindow.open(map, marker);
        markers.push(marker);
    }
    

    autocomplete();
    // map.addListener("click", (event) => {
    //     addMarker(event.latLng);
    //     codeCoordinate(event.latLng);
    // });
    markerClickHandler(markers);

    $("#search-button").on("click", searchMap);
}
// 마커 클릭 이벤트
function markerClickHandler(markers) {
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    directionsRenderer.setMap(map);

    markers.forEach(function(marker) {
        marker.addListener('click', function() {
        if (markers.length != 0) {
            calculateAndDisplayRoute(directionsService, directionsRenderer, marker);
        } else {
            console.log("Marker is null.");
        }
    });
});
}

// 길 찾기
function calculateAndDisplayRoute(directionsService, directionsRenderer, marker) {
    // 경로를 계산할 출발지와 도착지를 지정합니다.
    navigator.geolocation.getCurrentPosition(function (position) {
        // 현재 위치를 출발지로 설정
        var start = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        // 마커 위치를 도착지로 설정
        var end = marker.getPosition();
        let startPoint = "현재 위치";
        let endPoint = marker.getTitle();
        // 길찾기 요청 생성
        var request = {
            origin: start,
            destination: end,
            travelMode: 'TRANSIT'
        };

        // DirectionsService 객체에서 경로 정보를 요청합니다.
        directionsService.route(request, function (result, status) {
            if (status == 'OK') {
                // 경로 정보를 DirectionsRenderer 객체에 설정합니다.
                directionsRenderer.setDirections(result);

                // 경로 정보를 표시할 요소를 가져옵니다.
                var directionsPanel = $('#directions-panel');
                directionsPanel.css("display", "block");

                // 경로 정보를 HTML로 변환하여 표시합니다.
                // directionsPanel.innerHTML = '<p>출발지: ' + startPoint + '</p>' +
                //     '<p>도착지: ' + endPoint + '</p>' +
                //     '<p>거리: ' + result.routes[0].legs[0].distance.text + '</p>' +
                //     '<p>소요 시간: ' + result.routes[0].legs[0].duration.text + '</p>';
                $("#startPoint").text(startPoint);
                $("#endPoint").text(endPoint);
                $("#distance").text(result.routes[0].legs[0].distance.text);
                $("#duration").text(result.routes[0].legs[0].duration.text);
            }
        });
    });
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
        deleteMaker(null);
        // For each place, get the icon, name and location.
        const bounds = new google.maps.LatLngBounds();
        places.forEach((place) => {
            if (!place.geometry || !place.geometry.location) {
                console.log("Returned place contains no geometry");
                return;
            }
            let geocoder = new google.maps.Geocoder();
            // 좌표를 받아 reverse geocoding(좌표를 주소로 바꾸기)를 실행
            geocoder.geocode({ 'latLng': place.geometry.location }, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[1]) {
                        console.log(results[1]);
                        // infoWindow.setContent(results[1].formatted_address);     //infowindow로 주소를 표시
                        // infoWindow.open(map, marker);
                        // gotoServerLatLng(place.geometry.location, results[1].formatted_address);
                    }
                }
            });


            // let marker = new google.maps.Marker({
            //     map,
            //     title: place.name,
            //     position: place.geometry.location,
            // });
            // markers.push(marker);

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
                alert('일치하는 검색결과가 없습니다.');
            }
        }

    });
}

function createMarker(place) {
    deleteMaker(null);
    console.log(place); // 위치 정보 출력
    let marker = new google.maps.Marker({
        position: place.geometry.location,
        map: map,
        title: place.name
    });
    map.panTo(marker.position);
    markers.push(marker);
    codeCoordinate(marker.position)
    // gotoServerLatLng(marker.position, );
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

                // infoWindow.setPosition(pos);
                // infoWindow.setContent("Location found.");
                // infoWindow.open(map);
                addMarker(pos);
                map.setCenter(pos);
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
    let marker = new google.maps.Marker({
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
    // marker.setMap(map);
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
                // gotoServerLatLng(latLng, results[1].formatted_address);
            }
        }
    });

}

function gotoServerLatLng(latLng, addr) {
    // let name = JSON.stringify(addr);
    let xy = JSON.stringify(latLng);
    let location = JSON.parse(xy);
    $.ajax({
        type: "post",
        url: "getLatLng",
        data: { lat: location.lat, lng: location.lng, loc_name: addr },
        // dataType: "dataType",
        success: function () {
            console.log("위도 경도 성공");
        }
    });
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
    if (typeof google == 'undefined') {
        mapDiv.innerHTML = '맵을 로드하는 동안 에러가 발생했습니다. 새로고침해주세요.';
        $("#search").css("display", "none");
        $("#search-button").css("display", "none");
    }

}
// 에러 이벤트 핸들러를 등록.
window.addEventListener('error', handleMapError);

window.initMap = initMap;