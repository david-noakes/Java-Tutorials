var geocoder;
var map;
var brisbaneLatLng;
var councilCentre;
var councilBounds;
var councilMarker;
var basicSearchBox;

function initialize()
{
    geocoder = new google.maps.Geocoder();
    brisbaneLatLng = new google.maps.LatLng(-27.4775067,153.0281366);
	councilCentre = new google.maps.LatLng(-27.64288610,153.10398550);
	councilBounds = new google.maps.LatLngBounds(new google.maps.LatLng(-27.5, 152.8), new google.maps.LatLng(-27.8, 153.4));
	councilMarker = new google.maps.Marker(
            {
                position: councilCentre,
		        title: 'Logan Council, 150 Wembley Road, Logan Central Qld 4141'
            });
	// we set defaults. Now try for the real thing
	var sAddress = 'Logan Council, 150 Wembley Road, Logan Central Qld 4141';
    geocoder.geocode( { 'address': sAddress}, function(results, status)
    {
        if (status == google.maps.GeocoderStatus.OK)
        {
			councilCentre = new google.maps.LatLng(results[0].geometry.location);
			var northEast = new google.maps.LatLng(councilCentre.lat+0.15, councilCentre.lng-0.3);
			var southWest = new google.maps.LatLng(councilCentre.lat-0.15, councilCentre.lng+0.3);
			councilBounds = new google.maps.LatLngBounds(northEast, southWest);
            councilMarker = new google.maps.Marker(
            {
                position: councilCentre,
				title: results[0].formatted_address
            });
		}
	});
	if (councilCentre != null) {
        map = new google.maps.Map(document.getElementById("map-canvas"),
        {
            zoom: 13,
            center: councilCentre,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });
	} else {
        map = new google.maps.Map(document.getElementById("map-canvas"),
        {
            zoom: 13,
            center: brisbaneLatLng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });
	}
    councilMarker.setMap(map);
	map.fitBounds(councilBounds);
    if (mapStyles != undefined) {
        map.set('styles', mapStyles);
    }
    resizeScreen();

    basicSearchBox = new google.maps.places.Autocomplete(
        $("#txtBasicSearchText").get(0),
        {
            componentRestrictions: { country: "au" },
            bounds: councilBounds
        });

}

function codeAddress()
{
    var address = document.getElementById("address").value;
    var bounds = map.getBounds();
    geocoder.geocode( { 'address': address}, function(results, status)
    {
        if (status == google.maps.GeocoderStatus.OK)
        {
		   for (var i=0;i<results.length;i++) {
            map.setCenter(results[i].geometry.location);
			//var lat = results[i].geometry.location.B;
			//var lng = results[i].geometry.location.k;
			//var point = new google.maps.LatLng(lat, lng); // this ends up with an invalid point
			//bounds.extend(point);                         // this will cause an exception for fitbounds
			bounds.extend(results[i].geometry.location);   
            var marker = new google.maps.Marker(
            {
                map: map,
                position: results[i].geometry.location,
				title: results[i].formatted_address
            });
			}
			//map.fitBounds(bounds);   // this keeps zooming out each time it is called.
        }
        else
        {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

function showHelp() {
    windowRef = window.open('resources/documents/Council Map Viewer Help.pdf', 'Help');
}
function resizeScreen() {
    $("#contentPanel").height($(window).height() - $("#header").height() - 2);

    if ($("#navigationPanel").is(":visible")) {
        //------------------------------------------------------------------
        // The folowing is a work around an issue where a white bar is visible 
        /// between the navgation panel and the map panel. 
        //------------------------------------------------------------------

        $("#map-Panel").width($("#contentPanel").width() - $("#navigationPanel").outerWidth());
        $("#map-Panel").width($("#contentPanel").width() - $("#navigationPanel").outerWidth());
    }
    else {
        $("#map-Panel").width($("#contentPanel").width());
    }

    google.maps.event.trigger(map, "resize");
    google.maps.event.trigger(map, "resize");

    //$("#lowerPanel").height($("#navigationPanel").height() - $("#upperPanel").height() - 2);

    //$("#lowerPanel").jScrollPane();

    //$(".panelContent").jScrollPane();

}

window.onresize = function() {
	resizeScreen();
}