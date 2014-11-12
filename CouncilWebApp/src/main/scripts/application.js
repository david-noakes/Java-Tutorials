var geocoder;
var map;
var brisbaneLatLng;
var councilCentre;
var councilBounds;
var councilMarker;
var basicSearchBox;
var selectedPlace;
var searchHistory = [];

$(document).ready(function () {

//function initialize()
//{
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
	
	
	
	var stylez = [
	              {
	            	    featureType: "administrative.land_parcel",
	            	    elementType: "all",
	            	    stylers: [
	            	      { "visibility": "on" }
	            	    ]
	            	  }
	            	];
	var styledMapOptions = {
			name: "brisbanePlanning"
	}
	
	var styledMapType = new google.maps.StyledMapType(stylez, styledMapOptions);
			
	map.mapTypes.set(styledMapType);
	
    $("#searchHistory").hide();
    
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

    basicSearchPlaceChangedListener = google.maps.event.addListener(basicSearchBox, 'place_changed', function () {
        basicSearch_PlaceChanged();
    });

    $("#btnSearch").click(function () {
        showMapPage();

        if (selectedPlace) {
            basicPlaceChanged();
        }
    });

    $("#btnShowHistory").click(function () {
        showMapPage();

        if ($("#searchHistory").is(":visible")) {
            $("#searchHistory").hide();
        }
        else {
            if (searchHistory.length > 0) {
                var searchControl = $("#searchControl");

                var temp = [];

                $(searchHistory).each(function (index, value) {
                    temp.push({ title: value.title });
                });

                $("#searchHistory").empty();
                $("#searchHistoryTemplate").tmpl({ data: temp.reverse() }).appendTo("#searchHistory");

                $("#searchHistory").css({ top: searchControl.offset().top + searchControl.height() + 4, left: searchControl.offset().left, width: searchControl.width(), position: 'absolute' });

                $("#searchHistory .pac-item").click(function () {
                    var selectedItem = $(this)

                    if (selectedItem.length > 0) {
                        var placeKey = selectedItem.data("key");

                        $(searchHistory).each(function (index, value) {
                            if (value.title == placeKey) {
                                selectedPlace = value.place;

                                $("#txtBasicSearchText").val(selectedPlace.formatted_address);

                                $("#searchHistory").hide();

                                basicPlaceChanged();
                            }
                        });
                    }
                });
                $("#searchHistory").show();
                $("#searchHistory").focus();
            }
        }
    });

    $("#searchHistory").blur(function () {
        setTimeout(function () {
            $("#searchHistory").hide();
        }, 100);
    });


//}
});



/// ****** function declarations **** ///

function basicPlaceChanged() {

    if ((selectedPlace) && (selectedPlace.geometry)) {

        //searchManager.getParcel(selectedPlace.geometry.location.lat(), selectedPlace.geometry.location.lng(), null, selectedPlace);

    	codeAddress();
    	
        $('#txtBasicSearchText').val('');
    }
}


function codeAddress()
{
    var address = document.getElementById("txtBasicSearchText").value;
    var bounds = map.getBounds();
    geocoder.geocode( { 'address': address}, function(results, status)
    {
        if (status == google.maps.GeocoderStatus.OK)
        {
		    for (var i=0;i<results.length;i++) {
			    if (i==0) {
				    map.setCenter(results[i].geometry.location);
			    } 
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
		    map.setZoom(20);  // we need to turn on the cadastre layer
			//map.fitBounds(bounds);   // this keeps zooming out each time it is called.
        }
        else
        {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

function basicSearch_PlaceChanged() {
    //clearProximityPlace();

    showMapPage();
    selectedPlace = basicSearchBox.getPlace();

    //---------------------------------------------------------------
    // We need to check see if the place is in the history. If the 
    //  value exists then we should remove it and then re add it 
    //  as the most recent (ie the end)
    //---------------------------------------------------------------

    var deleteAt;

    $(searchHistory).each(function (index, value) {
        if (value.title == selectedPlace.formatted_address) {
            deleteAt = index;
        }
    });

    if (deleteAt) {
        searchHistory.splice(deleteAt, 1);
    }

    //---------------------------------------------------------------
    // push the select place onto the history stack
    //---------------------------------------------------------------

    if (selectedPlace.formatted_address)
        searchHistory.push({ title: selectedPlace.formatted_address, place: selectedPlace });

    //---------------------------------------------------------------
    // If the history array is too long remove the first one in the
    //  stack
    //---------------------------------------------------------------

    if (searchHistory.length > 7) {
        searchHistory.splice(0, 1);
    }

    basicPlaceChanged();

}

function showHelp() {
    windowRef = window.open('resources/documents/Council Map Viewer Help.pdf', 'Help');
}

function showMapPage() {
    if (!$("#map-canvas").is(":visible")) {
        $(".navButton").removeClass("ui-button-active");
        $(".panel").hide();

        $("#map-canvas").show();
        $("#btnHome").addClass("ui-button-active");
    }
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