var postalCodes = [
    '01067', // Dresden
    '10405', // Berlin
    '20359', // Hamburg
];

function initialize() {
    var map = new google.maps.Map(document.getElementById('map-canvas'), {
        center: new google.maps.LatLng(52.5167, 12.1833),
        zoom:   6
    });
    
    var geocoder = new google.maps.Geocoder();
    
    google.maps.event.addListenerOnce(map, 'tilesloaded', function() {
        for (var i = 0; i < postalCodes.length; ++i) {
            geocoder.geocode({
                address: postalCodes[i],
                region: 'DE'
            }, function(result, status) {
                if (status == 'OK' && result.length > 0) {
                    new google.maps.Marker({
                        position: result[0].geometry.location,
                        map: map
                    });
                }
            });
        }
    });
}

google.maps.event.addDomListener(window, 'load', initialize);