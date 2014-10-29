function SearchManager(parentMap) {

    var markers = [];
    var polygons = [];
    var infowindow = new google.maps.InfoWindow();
    var events = [];
    var currentResponse;
    var map = parentMap;

    var ParcelStyle = {
        fillColor: "#505050",
        fillOpacity: 0.2,
        strokeColor: "#303030",
        strokeOpacity: 1,
        strokeWeight: 1,
        draggable: false,
        clickable: false,
        editable: false,
        zIndex: 1
    }; 

    var BufferedParcelStyle = {
        fillColor: "#B01010",
        fillOpacity: 0.2,
        strokeColor: "#303030",
        strokeOpacity: 1,
        strokeWeight: 1,
        draggable: false,
        clickable: false,
        editable: false,
        zIndex: 1
    };


    function raiseError(data) {
        if (events.hasOwnProperty("error")) {
            events["error"](data);
        }
    }

    function raiseMarkerMoved(location, proximity) {
        if (events.hasOwnProperty("marker_moved")) {
            events["marker_moved"](location, proximity);
        }
    }

    function raiseShowAdminPlace(data) {
        if (events.hasOwnProperty("show_admin")){
            events["show_admin"](data);
        }
    }

    function raiseHideFeatureDetails() {
        if (events.hasOwnProperty("hide_feature_data")) {
            events["hide_feature_data"]();
        }
    }

    function raiseShowFeatures(data) {
        if (events.hasOwnProperty("display_feature_data")) {
            events["display_feature_data"](data);
        }
    }


    function raiseProcessResponse(data) {
        if (events.hasOwnProperty("display_parcel")) {
            events["display_parcel"](data);
        }
    }

    function raiseNoMatch() {
        if (events.hasOwnProperty("display_nomatch")) {
            events["display_nomatch"]();
        }
    }

    function raiseLGALayersResponse(data) {
        if (events.hasOwnProperty("display_lga_layers")) {
            events["display_lga_layers"](data);
        }
    }

    function raiseGrowthAreaLayersResponse(data) {
        if (events.hasOwnProperty("display_growtharea_layers")) {
            events["display_growtharea_layers"](data);
        } 
    }

    function raiseZonesListResponse(data) {
        if (events.hasOwnProperty("display_zones_list")) {
            events["display_zones_list"](data);
        }
    }

    function raiseZonePoygonsResponse(data) {
        if (events.hasOwnProperty("display_zones_geometry")) {
            events["display_zones_geometry"](data);
        }
    }


    function raiseShowBusy() {
        if (events.hasOwnProperty("show_busy")) {
            events["show_busy"]();
        }
    }

    function raiseHideBusy() {
        if (events.hasOwnProperty("hide_busy")) {
            events["hide_busy"]();
        }
    }

    function raiseNotFound() {
        if (events.hasOwnProperty("not_found")) {
            events["not_found"]();
        }
    }

    this.addListener = function (type, action) {
        events[type] = action;
    };

    this.OpenParcelInfoWindow = function(){
        if (infowindow) {
            infowindow.open(map, infowindow.marker);
        }
    };

    this.CloseParcelInfoWindow = function () {
        if (infowindow) {
            infowindow.close();
        }
    };

    this.CloseFeatureDetailsWindow = function () {
        raiseHideFeatureDetails();
    };

    this.findByLotPlanSection = function (lot, plan, section, zoomLevel) {
        //----------------------------------------------------------------------
        // Fire event to show busy curatin
        //----------------------------------------------------------------------

        raiseShowBusy();

        //----------------------------------------------------------------------
        // Clear the layer visibility
        //----------------------------------------------------------------------

        //layerManager.clearVisibility();

        //----------------------------------------------------------------------
        // Call the Cadastre controller 
        //----------------------------------------------------------------------

        $.ajax({
            url: "/Cadastre/GetByLotPlanSection",
            data: { lot: lot, plan: plan, section: section },
            dataType: "json",
            success: function (data) {

                if (data.parcel) {
                    // Parcel found
                    var lat = data.parcel.Geometry.centroid.Y;
                    var lng = data.parcel.Geometry.centroid.X;

                    var geocoder = new google.maps.Geocoder()
                    var latlng = new google.maps.LatLng(lat, lng);

                    geocoder.geocode({ 'latLng': latlng }, function (results, status) {
                        if (status == google.maps.GeocoderStatus.OK) {
                            if (results[0]) {
                                result = results[0];

                                //----------------------------------------------------------------------
                                // Have geocoded the location we will assume a street address so we will
                                //`query for a cadastral parcel
                                //----------------------------------------------------------------------

                                var searchResult = new SearchResult();
                                searchResult.address = getAddressFromGeocode(result);
                                searchResult.admin = data.parcel.Lga;
                                searchResult.state = "QLD";
                                searchResult.lat = lat;
                                searchResult.lng = lng;
                                searchResult.parcel = data.parcel;
                                searchResult.zoomLevel = zoomLevel;

                                createParcelInfoWindow(searchResult);

                                raiseProcessResponse(searchResult);

                                raiseHideBusy();
                            }
                        }
                    });
                }
                else {
                    var searchResult = new SearchResult();
                    searchResult.admin = "n/a";
                    searchResult.state = "QLD";

                    createParcelInfoWindow(searchResult);
                    raiseHideBusy();

                    raiseNoMatch();
                }

            },
            error: function (status, error) {
                raiseHideBusy();

                raiseError(status);
            }
        });
    }

   

    this.SearchByLocation = function (request) {

        $.ajax({
            url: "/Cadastre/Get",
            data: { lat: request.lat, lng: request.lng, proximity: request.proximity },
            dataType: "json",
            success: function (data) {

                //----------------------------------------------------------------------
                // OK we can now return
                //----------------------------------------------------------------------

                var searchResult = new SearchResult();
                searchResult.lat = request.lat;
                searchResult.lng = request.lng;
                searchResult.address = request.address;
                searchResult.admin = (data.parcel) ? data.parcel.Lga : null;
                searchResult.state = "QLD";
                searchResult.parcel = data.parcel;
                searchResult.proximity = request.proximity;

                createParcelInfoWindow(searchResult);

                raiseHideFeatureDetails();
                raiseProcessResponse(searchResult);

                raiseHideBusy();
            },
            error: function (status, error) {
                raiseHideFeatureDetails();
                raiseHideBusy();
                raiseError(status);
            }
        });
    }

    this.getParcel = function(lat, lng, proximity, place)
    {
        raiseShowBusy();

        // ---------------------------------------------------------------------
        // Google Analytics event categories
        //      [Proximity] Address Search
        //      [Proximity] POI Search
        //      [Proximity] Drag & Drop Search
        // ---------------------------------------------------------------------     
        var gaEventCategory = '';
        if (proximity) {
            gaEventCategory = 'Proximity ';
        }

        //----------------------------------------------------------------------
        // Clear the layer visibility
        //----------------------------------------------------------------------

        //layerManager.clearVisibility();


        if (place) {
            //----------------------------------------------------------------------
            // We have been supplied a place so depending what type of place we will 
            //  either search for parcel and display the results or we will treat it
            //  as an location such as an LGA and simple goto to this location.
            // +++ Originally only certain places were searched. P&E have requested 
            //  that all places be searched  04/07/2014 (CM)
            //----------------------------------------------------------------------

            var locationSearchRequest = new SearchRequest();
            locationSearchRequest.address = getAddressFromPlace(place);
            locationSearchRequest.admin = getLGAFromPlace(place);
            locationSearchRequest.state = getStateFromPlace(place);
            locationSearchRequest.proximity = proximity;
            locationSearchRequest.lat = place.geometry.location.lat();
            locationSearchRequest.lng = place.geometry.location.lng();

            if ($.inArray("street_address", place.types) > -1 || $.inArray("route", place.types) > -1) {
                gaEventCategory = gaEventCategory + 'Address Search';
            }
            else {
                gaEventCategory = gaEventCategory + 'POI Search';
            }

           // _gaq.push(['_trackEvent', gaEventCategory, 'Get', locationSearchRequest.address]);

            searchManager.SearchByLocation(locationSearchRequest);
        }
        else {
            //----------------------------------------------------------------------
            // Place is not known
            //----------------------------------------------------------------------

            var geocoder = new google.maps.Geocoder()
            var latlng = new google.maps.LatLng(lat, lng);

            geocoder.geocode({ 'latLng': latlng }, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        result = results[0];

                        //----------------------------------------------------------------------
                        // Have geocoded the location we will assume a street address so we will
                        //`query for a cadastral parcel
                        //----------------------------------------------------------------------

                        var locationSearchRequest = new SearchRequest();
                        locationSearchRequest.address = getAddressFromGeocode(result);
                        locationSearchRequest.admin = getLGAFromGeocode(result);
                        locationSearchRequest.state = getStateFromGeocode(result);
                        locationSearchRequest.proximity = proximity;
                        locationSearchRequest.lat = lat;
                        locationSearchRequest.lng = lng;

                        // Log the POI search 
                        gaEventCategory = gaEventCategory + 'Drag & Drop Search';
                     //   _gaq.push(['_trackEvent', gaEventCategory, 'Get', locationSearchRequest.address]);

                        searchManager.SearchByLocation(locationSearchRequest);
                    }
                }
                else {
                    //----------------------------------------------------------------------
                    // Nothing was found
                    //----------------------------------------------------------------------

                    var searchResult = new SearchResult();
                    searchResult.lat = lat;
                    searchResult.lng = lng;
                    searchResult.parcel = null;

                    createParcelInfoWindow(searchResult);
                    raiseHideFeatureDetails();

                    raiseProcessResponse(searchResult);
                    //raiseNoMatch();

                    raiseHideBusy();
                }
            });
        }
    }

    this.getZoneGeometry = function (lgaId, zones, lgaName) {

        // Show busy curtain

        raiseShowBusy();

        //----------------------------------------------------------------------
        // Google Analytics
        //----------------------------------------------------------------------
        //var gaLabel = lgaName + ':' + zones;
        //_gaq.push(['_trackEvent', 'Search LGA Planning Zone Polygons', 'Get', gaLabel]);

        $.ajax({
            url: "/Data/PlanningZonePolygons",
            traditional: true,
            data: { id: lgaId, zones: zones },
            dataType: "json",
            success: function (data) {
                raiseZonePoygonsResponse(data);

                raiseHideBusy();
            },
            error: function (status, error) {
                raiseHideBusy();

                raiseError(status);
            }
        });
    }

    this.getLGALayers = function (lgaId, lgaName) {
        //----------------------------------------------------------------------
        // Fire event to show busy curatin
        //----------------------------------------------------------------------

        raiseShowBusy();

        //----------------------------------------------------------------------
        // Google Analytics
        //----------------------------------------------------------------------
        //var gaLabel = lgaName;
        //_gaq.push(['_trackEvent', 'Search LGA Layers', 'Get', gaLabel]);

        //----------------------------------------------------------------------
        // Call the Cadastre controller 
        //----------------------------------------------------------------------

        $.ajax({
            url: "/Data/LGALayers",
            data: { id: lgaId },
            dataType: "json",
            success: function (data) {
                raiseLGALayersResponse(data)

                raiseHideBusy();
            },
            error: function (status, error) {
                raiseHideBusy();

                raiseError(status);
            }
        });
    }

    this.getLGAZones = function (lgaId, lgaName) {
        //----------------------------------------------------------------------
        // Fire event to show busy curatin
        //----------------------------------------------------------------------

        raiseShowBusy();

        //----------------------------------------------------------------------
        // Google Analytics
        //----------------------------------------------------------------------
        //var gaLabel = lgaName;
        //_gaq.push(['_trackEvent', 'Search LGA Planning Zones', 'Get', gaLabel]);

        $.ajax({
            url: "/Data/LGAZones",
            data: { id: lgaId },
            dataType: "json",
            success: function (data) {
                raiseZonesListResponse(data);

                raiseHideBusy();
            },
            error: function (status, error) {
                raiseHideBusy();

                raiseError(status);
            }
        });
    }

    this.getGrowthAreaLayers = function (areaId, areaName) {
        //----------------------------------------------------------------------
        // Fire event to show busy curatin
        //----------------------------------------------------------------------

        raiseShowBusy();

        //----------------------------------------------------------------------
        // Google Analytics
        //----------------------------------------------------------------------
        //var gaLabel = areaName;
        //_gaq.push(['_trackEvent', 'Search Growth Area Layers', 'Get', gaLabel]);

        $.ajax({
            url: "/Data/GrowthAreaLayers",
            data: { id: areaId },
            dataType: "json",
            success: function (data) {
                raiseGrowthAreaLayersResponse(data);

                raiseHideBusy();
            },
            error: function (status, error) {
                raiseHideBusy();

                raiseError(status);
            }
        });
    }

    this.getLayerFeatures = function (layerId, parcelId, mapTiles, proximity) {

        raiseShowBusy();

        //----------------------------------------------------------------------
        // Google Analytics
        //----------------------------------------------------------------------
        //var gaLabel = parcelId.toString() + ':' + layerId.toString();
        //_gaq.push(['_trackEvent', 'Get Layer Features', 'Get', gaLabel]);

        var requestData;

        if (proximity) {
            requestData = { parcelId: parcelId, layerId: layerId, mapTiles: mapTiles, proximity: proximity }
        }
        else {
            requestData = { parcelId: parcelId, layerId: layerId, mapTiles: mapTiles }
        }

        $.ajax({
            url: "/Cadastre/GetAssociatedLayerFeatures",
            dataType: "json",
            data: requestData,
            success: function (data) {
                raiseShowFeatures(data);
                raiseHideBusy();
            },
            error: function (status, error) {
                raiseHideBusy();
                raiseError(status);
            }
        });

    }

    this.clearMarkers = function () {
        clearMarkers();
    }

    function clearMarkers() {
        if (markers) {
            for (var i = 0, marker; marker = markers[i]; i++) {
                marker.setMap(null);
            }
        }

        markers = [];
    }

    this.clearPolygons = function () {
        clearPolygons();
    }

    function clearPolygons() {
        if (polygons) {
            // Remove all polygons from the maps

            for (var i = 0; i < polygons.length; i++) {
                polygons[i].overlay.setMap(null);
            }
        }

        polygons = [];
    }

    function createAdminInfoWindow(data) {
        clearMarkers();

        var content = '';

        if (infowindow) {
            infowindow.close();
        }

        infowindow = null;


        if (data.lat && data.lng) {
            var position = new google.maps.LatLng(data.lat, data.lng);
          
            var marker = new google.maps.Marker({
                map: map,
                draggable: true,
                position: position,
                zIndex: 0
            });

            markers.push(marker);

            google.maps.event.addListener(marker, 'dragend', function () {
                // The location of the marker has changed so we  will need to
                // re run the search. If the last search was a proximity then
                // will neeed to re-run a promity otherwise it will be a normal
                // search.

                searchManager.getParcel(marker.position.lat(), marker.position.lng(), null, null);
            });

            google.maps.event.addListener(marker, 'dragstart', function () {
                // The user is dragging the marker to a new location so we
                //  will get the info window out of the way

                searchManager.CloseParcelInfoWindow();
                searchManager.CloseFeatureDetailsWindow();
            });

            
        }
    }


    function createParcelInfoWindow(data) {
        clearMarkers();
        clearPolygons();

        var content = '';

        if (infowindow) {
            infowindow.close();
        }

        infowindow = null;

        if (data) {
            if (data.lat && data.lng) {
                var content;
                var position = new google.maps.LatLng(data.lat, data.lng);

                if (data.parcel) {

                    if (data.parcel.IntersectingLayers) {
                        if (data.parcel.IntersectingLayers.length > 0) {
                            data.parcel.IntersectingLayers.sort(function (a, b) {
                                if (a.QueryOrder == b.QueryOrder) { return 0; }
                                if (a.QueryOrder > b.QueryOrder) {
                                    return 1;
                                }
                                else {
                                    return -1;
                                }
                            });
                        }
                    }

                    if (data.parcel.BufferedIntersectingLayers) {
                        if (data.parcel.BufferedIntersectingLayers.length == 0) {
                            data.parcel.BufferedIntersectingLayers = null;
                        }
                        else {
                            data.parcel.BufferedIntersectingLayers.sort(function (a, b) {
                                if (a.QueryOrder == b.QueryOrder) { return 0; }
                                if (a.QueryOrder > b.QueryOrder) {
                                    return 1;
                                }
                                else {
                                    return -1;
                                }
                            });
                        }
                    }
                    else {
                        data.parcel.BufferedIntersectingLayers = null;
                    }
                }



                content = ($("#locationBoxTemplate").tmpl(data))[0].outerHTML;

                infowindow = new google.maps.InfoWindow({
                    content: content,
                    position: position
                });


                google.maps.event.addListener(infowindow, 'domready', function () {

                    //----------------------------------------------------------------------
                    // whatever you want to do once the DOM is ready
                    //----------------------------------------------------------------------

                    $(".themeLocationBoxTitle").click(function () {
                        var sectionClicked = $($(this).parent()[0]);

                        if (sectionClicked.hasClass("openPanel")) {
                            sectionClicked.removeClass("openPanel").addClass("closedPanel");

                            $(".locationBox").data('jsp').reinitialise();
                        }
                        else {
                            //$(".themeLocationBoxSection").removeClass("openPanel").removeClass("closedPanel").addClass("closedPanel");

                            sectionClicked.removeClass("closedPanel").addClass("openPanel");

                            $(".locationBox").data('jsp').reinitialise();
                            $(".locationBox").data('jsp').scrollToElement(sectionClicked, true);
                        }

                    });

                    //----------------------------------------------------------------------
                    // We will need to resync the visible layers
                    //----------------------------------------------------------------------

                    if (layerManager) {
                        layerManager.resetVisibility();
                        layerManager.checkLayerControlVisibility();
                    }

                    //----------------------------------------------------------------------
                    // We need to initialise the jquery scroll panel
                    //----------------------------------------------------------------------

                    $(".locationBox").jScrollPane();
                });

                var marker;

                if (data.proximity) {
                    marker = new google.maps.Marker({
                        map: map,
                        draggable: true,
                        position: position,
                        zIndex: 0
                    });

                    //----------------------------------------------------------------------
                    // Add the proximity to the marker so that if we drag the marker we know
                    //  to do a proximity search.
                    //----------------------------------------------------------------------

                    marker.proximity = data.proximity;
                }
                else {
                    marker = new google.maps.Marker({
                        map: map,
                        draggable: true,
                        position: position,
                        zIndex: 0
                    });
                }

                markers.push(marker);

                infowindow.marker = marker;

                google.maps.event.addListener(marker, 'dragend', function () {
                    //----------------------------------------------------------------------
                    // The location of the marker has changed so we  will need to
                    // re run the search. If the last search was a proximity then
                    // will neeed to re-run a promity otherwise it will be a normal
                    // search.
                    //----------------------------------------------------------------------

                    if (marker.proximity) {
                        searchManager.getParcel(marker.position.lat(), marker.position.lng(), marker.proximity, null);
                    }
                    else {
                        searchManager.getParcel(marker.position.lat(), marker.position.lng(), null, null);
                    }
                });

                google.maps.event.addListener(marker, 'dragstart', function () {
                    //----------------------------------------------------------------------
                    // The user is dragging the marker to a new location so we
                    //  will get the info window out of the way
                    //----------------------------------------------------------------------

                    searchManager.CloseParcelInfoWindow();
                    searchManager.CloseFeatureDetailsWindow();
                });


                google.maps.event.addListener(marker, 'click', function () {
                    infowindow.open(map, marker);
                })

                //infowindow.open(map, marker);
            }
        }
    }


    this.createMarker = function (title, position, clear) {

        if (clear) {
            this.clearMarkers();
        }

        var marker = new google.maps.Marker({
            map: map,
            draggable: true,
            position: position,
            zIndex: 0
        });

        markers.push(marker);

        google.maps.event.addListener(marker, 'dragend', function () {
            //----------------------------------------------------------------------
            // The location of the marker has changed so we  will need to re run the 
            //  search. If the last search was a proximity then will neeed to re-run 
            //  a promity otherwise it will be a normal search.
            //----------------------------------------------------------------------

            searchManager.getParcel(marker.position.lat(), marker.position.lng(), null, null);
        });

        google.maps.event.addListener(marker, 'dragstart', function () {
            //----------------------------------------------------------------------
            // The user is dragging the marker to a new location so we will get the 
            //  info window out of the way
            //----------------------------------------------------------------------

            searchManager.CloseParcelInfoWindow();
            searchManager.CloseFeatureDetailsWindow();
        });


        return marker;
    }

    this.loadLgaLandUsePolygons = function (data) {
        this.clearPolygons();

        for (var s = 0; s < data.length; s++) {
            var item = data[s];

            var polygonStyle = {
                draggable: false,
                clickable: false,
                editable: false,
                zIndex: 1
            };

            if (item.FillColour) {
                polygonStyle.fillColor = item.FillColour;
                polygonStyle.fillOpacity = 0.5;
            }
            else {
                polygonStyle.fillOpacity = 0;
            }

            if (item.OutlineColour) {
                polygonStyle.strokeColor = item.OutlineColour;
                polygonStyle.strokeOpacity = 1;
                polygonStyle.strokeWeight = 1;
            }

            if (item.Geometry != null) {

                var geoJsonPolygons = new GeoJSON(item.Geometry, polygonStyle);

                if (geoJsonPolygons.error) {

                }
                else {
                    if (geoJsonPolygons.length) {
                        for (var i = 0; i < geoJsonPolygons.length; i++) {
                            if (geoJsonPolygons[i].length) {
                                for (var j = 0; j < geoJsonPolygons[i].length; j++) {
                                    addPolygonToList(geoJsonPolygons[i][j]);
                                }
                            }
                            else {
                                addPolygonToList(geoJsonPolygons[i]);
                            }
                        }
                    }
                    else {
                        addPolygonToList(geoJsonPolygons);
                    }
                }
            }
        }

        //----------------------------------------------------------------------
        // I think we should pan the map to centre of the extent that contains 
        //  the zones
        //----------------------------------------------------------------------

        zoomToPolygons(polygons);
    }

    this.loadParcelPolygons = function (parcel) {
        this.clearPolygons();

        if (parcel) {
            if (parcel.Geometry) {
                createGeoJsonPolygons(parcel.Geometry, this.ParcelStyle);
            }

            if (parcel.BufferedGeometry) {
                createGeoJsonPolygons(parcel.BufferedGeometry, this.BufferedParcelStyle);
            }
        }
    }

    // Private methods

    function zoomToPolygons(polygons) {
        
    }

    function getAddressFromPlace(place) {
        return place.formatted_address;
    }

    function createGeoJsonPolygons(geometry, geometryStyle) {

        var geoJsonPolygons = new GeoJSON(geometry, geometryStyle);

        if (geoJsonPolygons.error) {

        }
        else {
            if (geoJsonPolygons.length) {
                for (var i = 0; i < geoJsonPolygons.length; i++) {
                    if (geoJsonPolygons[i].length) {
                        for (var j = 0; j < geoJsonPolygons[i].length; j++) {
                            addPolygonToList(geoJsonPolygons[i][j]);
                        }
                    }
                    else {
                        addPolygonToList(geoJsonPolygons[i]);
                    }
                }
            }
            else {
                addPolygonToList(geoJsonPolygons);
            }
        }

    }

    function addPolygonToList(polygon) {
        if (polygon) {
            polygon.setMap(map);

            polygons.push(
                {
                    type: "polygon",
                    overlay: polygon
                });
        }
    }

    function getLGAFromPlace(place)
    {
        var lga = null;

        $.each(place.address_components, function (i, item) {

            if ($.inArray("political", item.types) > -1)
            {
                if (lga == null) {
                    lga = item.short_name;
                }
            }
        });

        return lga;
    }

    function getStateFromPlace(place)
    {
        var state = null;

        $.each(place.address_components, function (i, item) {

            if ($.inArray("administrative_area_level_1", item.types) > -1)
            {
                if (state == null) {
                    state = item.short_name;
                }
            }
        });

        return state;
    }

    function isState(place) {
        var isPlaceState = false;

        if (place.address_components.length == 2) {
            if ($.inArray("administrative_area_level_1", place.address_components[0].types) > -1) {
                isPlaceState = true;
            }
        }

        return isPlaceState;
    }

    function getAddressFromGeocode(geocodeResult) {
        if (geocodeResult) {
            return geocodeResult.formatted_address;
        }
        else {
            return "Address Unknown";
        }
    }

    function getLGAFromGeocode(geocodeResult) {
        var lga = null;

        $.each(geocodeResult.address_components, function (i, item) {

            if ($.inArray("political", item.types) > -1)
            {
                lga = item.short_name;
            }
        });

        return lga;
    }

    function getStateFromGeocode(geocodeResult) {
        var state = null;

        $.each(geocodeResult.address_components, function (i, item) {

            if ($.inArray("administrative_area_level_1", item.types) > -1)
            {
                state = item.short_name;
            }
        });

        return state;
    }

    function geocodeAddress(lat, lng)
    {
        var result = null;

        if ((lat) && (lng))
        {
            var geocoder = new google.maps.Geocoder()
            var latlng = new google.maps.LatLng(lat, lng);

            geocoder.geocode({ 'latLng': latlng }, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        result = results[0];
                    }
                }
            });
        }

        return result;
    }
}

function SearchResult() {
        this.name = "";
        this.address = "";
        this.admin = "";
        this.state = "";
        this.isState = false;
        this.parcel = null;
        this.lat = null;
        this.lng = null;
        this.proximity = null;
        this.zoomLevel = null;
}

function SearchRequest() {
    this.name = "";
    this.address = "";
    this.admin = "";
    this.state = "";
    this.lat = null;
    this.lng = null;
    this.proximity = null;
    this.place = null;
}
