// markers = 거래 위치 선택 마커
let map, infoWindow, markers = [];
// , marker;
let markerDatas = [];
let latitude, longitude, loc_name;
let searchTest = false;
function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 37.574187, lng: 126.976882 },
        zoom: 17,
        minZoom: 14,
        maxZoom: 17
    });
    infoWindow = new google.maps.InfoWindow();

    locationList();

    geolocation();
    autocomplete();

    $("#search-button").on("click", searchMap);
    let mapCheckInterval = setInterval(function () {
        if (typeof map !== "undefined") {
            clearInterval(mapCheckInterval); // setInterval 정지
            google.maps.event.addListener(map, 'mouseup', function() {
                makersData();
            });
            google.maps.event.addListener(map, 'zoom_changed', function () {
                makersData();
            });
        }
    }, 500); // 500ms 간격으로 실행
    $(document).on("click", ".productTag", function () {
        let board_no = $(this).find(".product-details input").val();
        let type = $(this).find(".product-type").text();

        if (type == "중고") {
            location.href = "/used/usedSellBoardRead?used_id=" + board_no;
        } else if (type == "렌탈") {
            location.href = "/rental/rentalBoardRead?rental_id=" + board_no;
        } else {
            location.href = "/auction/auctionBoardRead?auction_id=" + board_no;
        }

    });
    $(".showBoardType").on("click", typeLocations);
    $("#allLocationList").on("click", locationList);
    $("#item-search-button").on("click", itemSearch);
}
// initMap 끝

// 현재 보이는 마커 데이터 수집 후 서버로 전송
function makersData() {
    let mapCheckInterval = setInterval(function () {
        if (typeof map !== "undefined") {
            clearInterval(mapCheckInterval); // setInterval 정지
            // 현재 보이는 지도의 중심 좌표와 확대/축소 레벨을 가져옵니다.
            let center = map.getCenter();
            let zoom = map.getZoom();

            // 현재 보이는 지도 영역의 경도/위도 좌표를 가져옵니다.
            let bounds = map.getBounds();
            let ne = bounds.getNorthEast();
            let sw = bounds.getSouthWest();

            // 현재 보이는 마커들의 데이터를 수집합니다.
            let viewMarkers = [];
            for (let i = 0; i < markerDatas.length; i++) {
                let marker = markerDatas[i].marker;
                let latlng = marker.getPosition();
                if (latlng.lat() < ne.lat() && latlng.lat() > sw.lat() &&
                    latlng.lng() < ne.lng() && latlng.lng() > sw.lng()) {
                    viewMarkers.push(markerDatas[i]);
                }
            }
            // 마커 데이터를 처리합니다.
            $(".simplebar-content ul li").html("");
            for (var i = 0; i < viewMarkers.length; i++) {
                var markerData = viewMarkers[i].info;
                // 마커 데이터를 수집하여 처리합니다.
                serverToBoardNo(markerData);

            }
        }
    }, 500); // 500ms 간격으로 실행
}
function typeLocations() {
    
    $("#allLocationList").removeAttr("disabled");
    $(".showBoardType").removeAttr("disabled");
    $(this).attr('disabled', 'true');

    let type = $(this).attr("board_type");

    $.ajax({
        type: "post",
        url: "/typeLocations",
        data: { type: type },
        dataType: "json",
        success: function (locations) {
            deleteMaker(null);
            $.each(locations, function (i, location) {
                addMarker(location);
            });
            makersData();
        }
    });
}
function locationList() {
    $("#allLocationList").attr('disabled', 'false');
    $(".showBoardType").removeAttr("disabled");
    $.ajax({
        type: "post",
        url: "/locations",
        dataType: "json",
        success: function (locations) {
            deleteMaker(null);
            $.each(locations, function (i, location) {
                addMarker(location);
            });
            makersData();
        }
    });
}
function addMarker(location) {
    let marker = new google.maps.Marker({
        position: { lat: location.lat, lng: location.lng },
        map: map,
        title: location.loc_name
    });
    let markerInfo = {
        marker: marker,
        info: location // 마커 정보 객체
    };
    markerDatas.push(markerInfo);
    markers.push(marker);
}
function deleteMaker(map) {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
    markers = [];
    markerDatas = [];
}

function serverToBoardNo(viewMarkersData) {
    $.ajax({
        type: "post",
        url: "/markerData",
        data: { board_no: viewMarkersData.board_no },
        dataType: "json",
        success: function (board) {
            let product = '<div class="productTag"><div class="product">';
            product += '<div class="product-img">';
            product += '<div class="product-imgTag"><img src="/imgShow?board_no=' + board.board_no + '" alt="Image" class="img-fluid"></div></div>';
            product += '<div class="product-details">'
            product += '<input type="hidden" name="board_no" value="' + board.board_no + '"><strong class="product-type" style="font-weight: 400; font-size: 20px;">' + board.board_type + '</strong><h1 class="product-title">' + board.title + '</h1>'
            if(board.board_type == "경매" && board.high_price != null) {
                product += '<p class="product-price" style="font-weight: 600;">' + "&#92; " + parseInt(board.high_price).toLocaleString("en-US"); + ''
            } else {
                product += '<p class="product-price" style="font-weight: 600;">' + "&#92; " + parseInt(board.price).toLocaleString("en-US"); + ''
            }
            if (board.board_type == "렌탈") {
                product += '<span> (일)</span>';
            }
            product += '</p><p class="product-seller">' + board.seller + '</p><p class="product-info">' + board.info + '</p></div></div></div></div>'
            $(".simplebar-content ul li").append(product);
        }
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
        // For each place, get the icon, name and location.
        const bounds = new google.maps.LatLngBounds();
        places.forEach((place) => {
            if (!place.geometry || !place.geometry.location) {
                console.log("찾을 수 없는 지역입니다.");
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
                map.setZoom(17);
                map.setCenter(place.geometry.location);
                makersData()
            } else {
                alert('일치하는 검색결과가 없습니다. 검색창에서 enter를 눌러주세요.');
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

function itemSearch() {
    let search = $("#item-search").val();
    console.log(search);
    $.ajax({
        url: '/search'
        , type: 'post'
        , data: { str: search }
        , dataType: 'json'
        , success: (locations) => {
            deleteMaker(null);
            $.each(locations, function (i, location) {
                addMarker(location);
            });
            makersData();
        }
    });
}

// 에러 이벤트 핸들러를 등록.
window.addEventListener('error', handleMapError);

window.initMap = initMap;