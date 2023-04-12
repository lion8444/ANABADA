// markers = 거래 위치 선택 마커
let map, infoWindow, markers = [];
// , marker;
let locations = [];
let latitude, longitude, loc_name;
function initMap() {
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    let latLng;
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 37.574187, lng: 126.976882 },
        zoom: 17,
        minZoom: 5,
        maxZoom: 20
    });
    infoWindow = new google.maps.InfoWindow();

    latitude = $("#latitude");
    longitude = $("#longitude");
    loc_name = $("#loc_name");

    if (!latitude.length || latitude.val() == "" || longitude.val() == "" || loc_name.val() == "") {
        var mapDiv = document.getElementById('map');
        mapDiv.innerHTML = '맵을 로드하는 동안 에러가 발생했습니다. 창을 다시 열어주세요.';
        return;
    }
    else {
        latLng = { lat: parseFloat(latitude.val()), lng: parseFloat(longitude.val()) };
        map.setCenter(latLng);

    }

    const endMarker = new google.maps.Marker({
        position: latLng,
        map: map,
        title: loc_name.val()
    });
    infoWindow.setContent(loc_name.val());
    infoWindow.open(map, endMarker);

    let startMarker = null;

    map.addListener("click", (event) => {
        directionsRenderer.setMap(null);
        directionsRenderer.setMap(map);
        if(startMarker != null) {
            startMarker.setMap(null);
        }
        let geocoder = new google.maps.Geocoder();
        // 좌표를 받아 reverse geocoding(좌표를 주소로 바꾸기)를 실행
        geocoder.geocode({ 'latLng': event.latLng }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (results[1]) {
                    startMarker = new google.maps.Marker({
                        position: event.latLng,
                        map: map,
                        title: results[1].formatted_address
                    });
                    infoWindow.setContent(results[1].formatted_address);     //infowindow로 주소를 표시
                    infoWindow.open(map, startMarker);
                    let start, end, startPoint, endPoint, request;
        if (startMarker == null) {
            // 경로를 계산할 출발지와 도착지를 지정합니다.
            navigator.geolocation.getCurrentPosition(function (position) {
                // 현재 위치를 출발지로 설정
                start = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                // 마커 위치를 도착지로 설정
                end = endMarker.getPosition();
                startPoint = "현재 위치";
                endPoint = endMarker.getTitle();
                // 길찾기 요청 생성
                request = {
                    origin: start,
                    destination: end,
                    travelMode: 'TRANSIT'
                };
            });
        }
        else {
            start = startMarker.getPosition();
            // 마커 위치를 도착지로 설정
            end = endMarker.getPosition();
            startPoint = startMarker.getTitle();
            endPoint = endMarker.getTitle();
            // 길찾기 요청 생성
            request = {
                origin: start,
                destination: end,
                travelMode: 'TRANSIT'
            };
        }
        // DirectionsService 객체에서 경로 정보를 요청합니다.
        directionsService.route(request, function (result, status) {
            if (status == 'OK') {
                // 경로 정보를 DirectionsRenderer 객체에 설정합니다.
                directionsRenderer.setDirections(result);

                // 경로 정보를 표시할 요소를 가져옵니다.
                var directionsPanel = $('#directions-panel');
                directionsPanel.css("display", "block");

                // 경로 정보를 HTML로 변환하여 표시합니다.
                $("#startPoint").text(startPoint);
                $("#endPoint").text(endPoint);
                $("#distance").text(result.routes[0].legs[0].distance.text);
                $("#duration").text(result.routes[0].legs[0].duration.text);
                // 길 안내 문자열 생성
                const route = result.routes[0];
                const legs = route.legs;
                let instructions = "<ol>";
                for (let i = 0; i < legs.length; i++) {
                    const steps = legs[i].steps;
                    const mode = legs[i].steps[0].travel_mode;
                    const transit = legs[i].transit;
                    if (mode == google.maps.TravelMode.TRANSIT) {
                        const line = transit.line;
                        instructions += "<li>";
                        instructions += "Take the " + line.short_name;
                        instructions += " (" + line.name + ") ";
                        instructions +=
                            "toward " + transit.arrival_stop.name + ". ";
                        instructions +=
                            "Get off at " + transit.departure_stop.name + " ";
                        instructions +=
                            "in " + transit.departure_time.text + " (" +
                            transit.departure_time.time_zone + "). ";
                        instructions += "</li>";
                    } else {
                        for (let j = 0; j < steps.length; j++) {
                            instructions += "<li>" + steps[j].instructions + "</li>";
                        }
                    }
                    instructions += "</ol>";
                    // 길 안내 문자열 표시
                    document.getElementById("instructions").innerHTML = instructions;
                }
            }
        });
                }
            }
        });

    });
    autocomplete();
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
                map.setCenter(place.geometry.location);
            } else {
                alert("검색어가 없습니다.");
            }
        }

    });
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