var map;
var drawingManager;
var basicSearchBox;
var proximitySearchBox;
var geocoder;
var drawingManager;
var searchManager;
var layerManager;
var welcomeViewIsShown = false;
var createPinToolActive = false;
var featureDetailsDataStore = new Array();
var searchHistory = [];
var selectedPlace;
var basicSearchPlaceChangedListener;
var proximityPlaceChangedListener;
var accessTokenCheckInProgress = false;
var windowRef;

$(document).ready(function () {

    //---------------------------------------------------------------
    // Work around for an issue with iPads when the page disappears
    //  under the tab bars and is too long
    //---------------------------------------------------------------

    if (navigator.userAgent.match(/iPad;.*CPU.*OS 7_\d/i) && window.innerHeight != document.documentElement.clientHeight) {
        var fixViewportHeight = function () {
            document.documentElement.style.height = window.innerHeight + "px";
            if (document.body.scrollTop !== 0) {
                window.scrollTo(0, 0);
            }
        }.bind(this);

        window.addEventListener("scroll", fixViewportHeight, false);
        window.addEventListener("orientationchange", fixViewportHeight, false);
        fixViewportHeight();

        document.body.style.webkitTransform = "translate3d(0,0,0)";
    }


    var mapCentre = new google.maps.LatLng(-27.64288610,153.10398550);	
	var mapBounds = new google.maps.LatLngBounds();
	mapBounds.extend(mapCentre);
    var mapOptions = {
        zoom: 13,
        center: mapCentre,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        streetViewControl: false,
        scaleControl: true

    };

    map = new google.maps.Map(document.getElementById('mapcanvas'), mapOptions);


    drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: null,
        drawingControl: false,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [
              google.maps.drawing.OverlayType.MARKER
            ]
        },
    });

    google.maps.event.addListener(drawingManager, 'markercomplete', function (marker) {
        marker.setMap(null);

        if (createPinToolActive) {

            //hideAllLegends();
            //layerManager.clearVisibility();

            searchManager.getParcel(marker.getPosition().lat(), marker.getPosition().lng(), null, null);

            unSelectCreatePin();
        }
    });

    drawingManager.setMap(map);



    searchManager = new SearchManager(map);
    layerManager = new LayerManager(map);

    searchManager.createMarker('Logan Central', mapCentre, false);

    geocoder = new google.maps.Geocoder();

    map.fitBounds(mapBounds);

    basicSearchBox = new google.maps.places.Autocomplete(
        $("#txtBasicSearchText").get(0),
        {
            componentRestrictions: { country: "au" },
            bounds: mapBounds
        });

    proximitySearchBox = new google.maps.places.Autocomplete(
        $("#txtASProximityAddress").get(0),
        {
            componentRestrictions: { country: "au" },
            bounds: mapBounds
        });

    basicSearchPlaceChangedListener = google.maps.event.addListener(basicSearchBox, 'place_changed', function () {
        basicSearch_PlaceChanged();
    });

    proximityPlaceChangedListener = google.maps.event.addListener(proximitySearchBox, 'place_changed', function () {
        proximity_PlaceChanged();
    });

    if (mapStyles != undefined) {
        map.set('styles', mapStyles);
    }

    //---------------------------------------------------------------
    // Add the button over the canvas to toggle the navigation of the 
    //  navigation pan
    //---------------------------------------------------------------

    var btnToggleNav = $('<button id="btnToggleNavigation" title="Toggle visibility of navigation panel" onclick="toggleNavigation();" class="openNavPanel" ></button>')[0];

    btnToggleNav.index = 0;
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(btnToggleNav);

    //---------------------------------------------------------------
    // Add click event to show terms button in the welcome panel
    //---------------------------------------------------------------

    $("#btnShowTerms").click(function () {
        $(".navButton").removeClass("ui-button-active");
        $(".panel").hide();

        $("#terms").show();
        $("#btnTerms").addClass("ui-button-active");

        var pageContent = $("#terms .panelContent");

        if (pageContent.length > 0) {
            pageContent.jScrollPane();
        }
        else {
            google.maps.event.trigger(map, "resize");
        }

    });

    //---------------------------------------------------------------

    $("#btnDropPin").click(function () {
        showMapPage();

        if (createPinToolActive) {
            unSelectCreatePin();
        }
        else {
            selectCreatePin();
        }
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

    $("#txtASProximityAddress").keydown(function (e) {

        if (e.which == 13) {

            e.preventDefault();

            return false;
        }

        return true;
    });

    $("#searchHistory").keydown(function (event) {
        event.preventDefault();

        //---------------------------------------------------------------
        // if key pressed is the up key then we need to select the
        //  previous item.
        // if the key pressed is the down key then we need to select the
        //  next item.
        // if the key pressed is a enter key then we need to click the
        //---------------------------------------------------------------

        if (event.which == 13) {
            var selectedItem = $("#searchHistory .pac-item-selected")

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
        } else if (event.which == 38) {
            // Up
            var selectedItem = $("#searchHistory .pac-item-selected")

            if (selectedItem.length == 0) {
                var lastItem = $("#searchHistory .pac-item:last");

                if (lastItem) {
                    lastItem.addClass("pac-item-selected");
                }
            }
            else {
                selectedItem.removeClass("pac-item-selected");
                selectedItem.prev().addClass("pac-item-selected");
            }

        } else if (event.which == 40) {
            // Down
            var selectedItem = $("#searchHistory .pac-item-selected")

            if (selectedItem.length == 0) {
                var firstItem = $("#searchHistory .pac-item:first");

                if (firstItem) {
                    firstItem.addClass("pac-item-selected");
                }
            }
            else {
                selectedItem.removeClass("pac-item-selected");
                selectedItem.next().addClass("pac-item-selected");
            }
        }
    });

    //---------------------------------------------------------------
    //
    //---------------------------------------------------------------

    $("#contentPanel").focus(function () {
        $('#shareOptions').hide();
    });

    $("#header").blur(function () {
        $('#shareOptions').hide();
    });

    $("#btnShare").click(function () {
        var shareButton = $('#btnShare');

        var targetWidth = 140;
        var targetLeft = shareButton.offset().left + (shareButton.outerWidth() / 2) - (targetWidth / 2);
        var targetTop = $('#header').height() - 11;

        $("#shareOptions").css({ top: targetTop, left: targetLeft, width: targetWidth, position: 'absolute' });
        $('#shareOptions').show();
    });

    //---------------------------------------------------------------
    // Add an event that opens the tooltip when the icon is clicked
    //  this is required for touch devices that don't do hover
    //---------------------------------------------------------------

    $('document').tooltip();
    $('.help').tooltip();

    $('.help').click(function (e) {
        e.preventDefault();

        $(this).tooltip("open");

        stopEventPropagation(e);

        return false;
    });

    //---------------------------------------------------------------
    // Add click event to scroll to top of the page
    //---------------------------------------------------------------


    $("#btnGotoTopAboutUs").click(function (e) {
        $("#aboutusContent").data('jsp').scrollToY(0);

        return false;
    });

    $("#btnGotoTopTerms").click(function (e) {
        $("#termsContent").data('jsp').scrollToY(0);

        return false;
    });

    $("#btnGotoTopContactUs").click(function (e) {
        $("#contactusContent").data('jsp').scrollToY(0);

        return false;
    });

    $("#btnGotoTopFeedback").click(function (e) {
        $("#feedbackContent").data('jsp').scrollToY(0);

        return false;
    });

    //$(".top").click(function (e) {
    //    $(".panelContent").data('jsp').scrollToY(0);
        
    //    e.preventDefault();
    //});

    //---------------------------------------------------------------
    // Add click event to navigation buttons
    //---------------------------------------------------------------

    $(".navButton").click(function () {
        hideFeatureDetails();
        hideFeedbackSent();
        hideShareOptions();

        $(".navButton").removeClass("ui-button-active");
        $(this).addClass("ui-button-active");

        $(".panel").hide();

        var pageId = "#" + $(this).data("page");
        $(pageId).show();

        var pageContent = $(pageId + " .panelContent");

        if (pageContent.length > 0) {
            pageContent.jScrollPane();
        }
        else {
            google.maps.event.trigger(map, "resize");
        }

    });

    //---------------------------------------------------------------
    // Register event handlers for the Layer Manager
    //---------------------------------------------------------------

    layerManager.addListener("visibility_changed", function (layerId, visible) {
        checkAccessToken();
        showMapPage();

        var checkbox = $(".dl_" + layerId);
        checkbox.prop("checked", visible);

        // If we are making a layer visible we will open the legend

        if (visible) {
            var layerClicked = $($("#tl_" + layerId).parent().parent().parent()[0]);

            showLegend(layerClicked, true);
        }

    });

    layerManager.addListener("show_busy", function () {
        $("#busy").show();
    });

    layerManager.addListener("hide_busy", function () {
        $("#busy").hide();
    });

    layerManager.addListener("loaded", function () {
        $("#loaded").hide();
    });

    layerManager.addListener("max_visible_exceeded", function () {

        $("#numlayers-exceeded").dialog({
            modal: true,
            resizable: false,
            closeOnEscape: false,
            width: 500,
            buttons: {
                Ok: function () {
                    $(this).dialog("close");
                }
            }
        });

        resizeScreen();
    });

    //---------------------------------------------------------------
    // Register event handlers for the Search Manager
    //---------------------------------------------------------------

    searchManager.addListener("show_busy", function () {
        $("#busy").show();
    });

    searchManager.addListener("hide_busy", function () {
        $("#busy").hide();
    });

    searchManager.addListener("show_admin", function (data) {
        processAdminResponse(data);
    });

    searchManager.addListener("display_parcel", function (data) {
        processCadastreResponse(data);
    });

    searchManager.addListener("display_nomatch", function () {
        processNoMatchResponse();
    });    

    searchManager.addListener("display_zones_geometry", function (data) {
        searchManager.loadLgaLandUsePolygons(data);
    });

    searchManager.addListener("display_lga_layers", function (data) {

        data.Layers.sort(function (a, b) {
            if (a.QueryOrder == b.QueryOrder) { return 0; }
            if (a.QueryOrder > b.QueryOrder) {
                return 1;
            }
            else {
                return -1;
            }
        });


        $("#lgaLayerPanel").empty();
        $("#lgaLayerTemplate").tmpl(data).appendTo("#lgaLayerPanel");

        $('.scrollableblock').jScrollPane();
    });

    searchManager.addListener("display_growtharea_layers", function (data) {

        data.Layers.sort(function (a, b) {
            if (a.QueryOrder == b.QueryOrder) { return 0; }
            if (a.QueryOrder > b.QueryOrder) {
                return 1;
            }
            else {
                return -1;
            }
        });




        $("#growthAreaLayerPanel").empty();
        $("#growthAreaLayerTemplate").tmpl(data).appendTo("#growthAreaLayerPanel");

        $('.scrollableblock').jScrollPane();

    });

    searchManager.addListener("display_zones_list", function (data) {
        $("#planningZonePanel").empty();
        $("#lgaPlanningZoneTemplate").tmpl(data).appendTo("#planningZonePanel");

        //---------------------------------------------------------------
        // We need to attach a click event to make sure that no more than 
        //  7 zones are selected.
        //---------------------------------------------------------------

        $(".lgaZoneNamePanel input[type='checkbox']").click(function () {
            if ($(this).prop("checked") == true) {
                // Hide the error label

                $("#lgaZoneForm").find("label").remove(".error");
                $("#lgaZoneForm").find(".error").removeClass("error");


                if ($(".lgaZoneNamePanel input[type='checkbox']:checked").length > 7) {
                    $(this).prop("checked", false);

                    $("#numzones-exceeded").dialog({
                        modal: true,
                        resizable: false,
                        closeOnEscape: false,
                        width: 500,
                        buttons: {
                            Ok: function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    resizeScreen();

                }
            }
        });

        $('.scrollableblock').jScrollPane();
    });

    searchManager.addListener("hide_feature_data", function (data) {
        hideFeatureDetails();
    });

    searchManager.addListener("display_feature_data", function (data) {
        var layerFound = false;

        if ($('#metadataPopup').is(':data(dialog)'))
        {
            $('#metadataPopup').dialog("close");
        }

        //---------------------------------------------------------------
        // The order of this array is dependant on the query order
        //  property
        //---------------------------------------------------------------

        for (var i = 0; i < featureDetailsDataStore.length; i++) {
            if (featureDetailsDataStore[i].Layer == data.Layer) {
                layerFound = true;
                featureDetailsDataStore[i].Features = data.Features;
                break;
            }
        }

        if (!layerFound) {
            featureDetailsDataStore.push(data);
        }

        //---------------------------------------------------------------
        // Sort the array by Query order
        //---------------------------------------------------------------

        featureDetailsDataStore.sort(function (a, b) {
            if (a.QueryOrder == b.QueryOrder) { return 0; }
            if (a.QueryOrder > b.QueryOrder) {
                return 1;
            }
            else {
                return -1;
            }
        });

        $('#metadataPopupContent').empty();
        var templateData = { Layers: featureDetailsDataStore, MapTiles: data.MapTiles };
        $("#featurePopupTemplate").tmpl(templateData).appendTo("#metadataPopupContent");

        $(".featurePopupSection").each(function (index, value) {
            if ($(value).data("layerid") == data.Layer) {
                $(value).removeClass("closedPanel").addClass("openPanel");
            }
        });


        //show popup with properties
        $("#metadataPopup").dialog({
            title: ' ',
            minWidth: 500,
            minHeight: 200,
            maxHeight: 800,
            close: function (event, ui) {
                featureDetailsDataStore = [];
            },
            open: function (event, ui) {
                //$("#metadataPopup").jScrollPane();                
            },
            resize: function (event, ui) {
                $("#metadataPopup").jScrollPane();
            }
        });

        $("#metadataPopup").jScrollPane();

        $(".popupLayerName, .popupLayerToggle").click(function () {
            var sectionClicked = $($(this).parent().parent()[0]);

            if (sectionClicked.hasClass("openPanel")) {
                sectionClicked.removeClass("openPanel").addClass("closedPanel");
            }
            else {
                sectionClicked.removeClass("closedPanel").addClass("openPanel");
            }

            $("#metadataPopup").jScrollPane();

        });

        resizeScreen();
    });

    searchManager.addListener("error", function (data) {

        $("#errorDetails").text("(" + ((data.status) ? data.status : "unknown") + " : '" + ((data.statusText) ? data.statusText : "unknown")  + "') ");

        $("#errorDialog").dialog({
            modal: true,
            resizable: false,
            closeOnEscape: false,
            width: 500,
            buttons: {
                OK: function () {
                    $(this).dialog("close");
                }
            }
        });
        resizeScreen();
    });

    $("#btnClearLayers").click(function () {
        hideAllLegends();
        layerManager.clearVisibility();
    });

    $("#btnLocationSearch").click(function () {
        showLocationSearch(true);
    });

    $("#btnAdvancedSearch").click(function () {
        showAdvancedSearch();
    });

    //$('#shareOptions').mouseout(function () {
    //    hideShareOptions();
    //});

    $("#hideWelcome").click(function () {
        hideWelcome(false);
    });

    //---------------------------------------------------------------
    // Add click events to share buttons
    //---------------------------------------------------------------

    $("#shareByTwitter").click(function () {
        if (shareViaTwitterLink) {
            window.open(shareViaTwitterLink, "Share", "width=550, height=500");
        }
        hideShareOptions();
    });

    $("#shareByEmail").attr('href', shareViaEmailLink);

    $("#shareByEmail").click(function () {
        if (shareViaEmailLink) {
            window.open(shareViaEmailLink, "Share");
        }
        hideShareOptions();
    });

    $("#shareByFacebook").click(function () {
        if (shareViaFacebookLink) {
            window.open(shareViaFacebookLink, "Share", "width=550, height=500");
        }
        hideShareOptions();
    });

    $("#shareByGooglePlus").click(function () {
        if (shareViaGooglePlusLink) {
            window.open(shareViaGooglePlusLink, "Share", "width=550, height=500");
        }
        hideShareOptions();
    });


    //---------------------------------------------------------------
    // Add click events to the search and clear buttons for the the
    //  Lot, Plan, Section Advanced Search
    //---------------------------------------------------------------

    $("#btnLotPlanSearch").click(function () {
        showMapPage();

        $("#lotPlanDetailsForm").find("label").remove(".error");
        $("#lotPlanDetailsForm").find(".error").removeClass("error");

        $('#lotPlanDetailsForm').validate();

        var planValid = true;
        
        var plan = $("#txtASPlanNumber").val();
        if (plan) {
            var planPrefix = plan.substring(0, 2);

            if ((planPrefix == "SP") || (planPrefix == "DP")) {
                planValid = true;
            }
            else {
                planValid = false;
            }
        }

        if ($('#lotPlanDetailsForm').valid() && planValid) {
            searchManager.findByLotPlanSection($("#txtASLotNumber").val(), $("#txtASPlanNumber").val(), $("#txtASSectionNumber").val());

            //showLocationSearch();
            //openSection();
        }
        else if (!planValid) {
            var errorlabel = $("<label>").text('Plan number must begin with either SP or DP.').attr({ "for": "txtASPlanNumber", "class": "error" });
            $("#txtASPlanNumber").parent().append(errorlabel);
        }

        return false;
    });


    $("#btnLotPlanClear").click(function () {
        showMapPage();
        clearLotPlanSearch();

        return false;
    });

    $("#txtASLotNumber").blur(function (e) {
        //---------------------------------------------------------------
        // Remove spaces
        //---------------------------------------------------------------

        var strVal = $(this).val();
        if (strVal) {
            strVal = strVal.replace(/\s+/g, '');
            $(this).val(strVal);
        }
    });

    //---------------------------------------------------------------
    // Update the required status of the Lot number based on the
    //  content of the Plan field. If Plan starts with SP this 
    //  indicates a Strata Plan and these don't require a lot number
    //---------------------------------------------------------------

    $("#txtASPlanNumber").blur(function () {
        var planValue = $("#txtASPlanNumber").val();

        //---------------------------------------------------------------
        // Remove spaces
        //---------------------------------------------------------------

        if (planValue) {
            planValue = planValue.replace(/\s+/g, '');
            $(this).val(planValue);
        }

        if (planValue.indexOf("SP") == 0) {
            $("#txtASLotNumber").removeAttr("required");
            $("#lblASLotNumber").text('Enter lot number');
        }
        else {
            $("#txtASLotNumber").attr("required", true);
            $("#lblASLotNumber").text('Enter lot number*');
        }
    });

    $("#txtASSectionNumber").blur(function (e) {
        //---------------------------------------------------------------
        // Remove spaces
        //---------------------------------------------------------------

        var strVal = $(this).val();
        if (strVal) {
            strVal = strVal.replace(/\s+/g, '');
            $(this).val(strVal);
        }
    });
    //---------------------------------------------------------------
    // Add click events to the search and clear buttons for the the
    //  Lot, Plan, Section Advanced Search
    //---------------------------------------------------------------

    $("#btnProximitySearch").click(function () {
        showMapPage();

        //---------------------------------------------------------------
        // Remove any validation
        //---------------------------------------------------------------

        $("#proximityDetailsForm").find("label").remove(".error");
        $("#proximityDetailsForm").find(".error").removeClass("error");

        $("#proximityDetailsForm").validate();

        if ($('#proximityDetailsForm').valid()) {
            var place = proximitySearchBox.getPlace();

            if (place) {
                if (place.geometry) {
                    searchManager.getParcel(place.geometry.location.lat(), place.geometry.location.lng(), $("#cbxRange").val(), place);

                    clearBasicPlace();

                    //showLocationSearch();
                    //openSection();

                } else {
                    //---------------------------------------------------------------
                    // Add a validation messages
                    //---------------------------------------------------------------

                    var errorlabel = $("<label>").text('Please enter an address.').attr({ "for": "txtASProximityAddress", "class": "error" });
                    $("#txtASProximityAddress").parent().append(errorlabel);
                }
            }
            else {
                //---------------------------------------------------------------
                // Add a validation message
                //---------------------------------------------------------------

                var errorlabel = $("<label>").text('Please enter an address.').attr({ "for": "txtASProximityAddress", "class": "error" });
                $("#txtASProximityAddress").parent().append(errorlabel);
            }
        }

        return false;
    });

    $("#btnProximityClear").click(function () {
        showMapPage();
        clearProximitySearch();

        return false;
    });

    $("#btnLandUseSearch").click(function (e) {
        showMapPage();

        $("#lgaZoneForm").find("label").remove(".error");
        $("#lgaZoneForm").find(".error").removeClass("error");

        $("#lgaZoneForm").validate();

        if ($('#lgaZoneForm').valid()) {

            var selectedLga = $("#cbxLocalGovernmentArea2").children(":selected").attr("value");

            if (selectedLga) {
                zoomToBounds($("#cbxLocalGovernmentArea2").children(":selected"));

                hideAllLegends();
                layerManager.clearVisibility();

                $("#lblLocalGovernmentArea2").hide();

                tickedLandUseZones = [];

                $("#planningZonePanel input:checkbox").each(function (index, element) {
                    if (element.checked) {
                        tickedLandUseZones.push($(element).attr("data-zone_short_code"));
                    }
                });

                var lgaName = $("#cbxLocalGovernmentArea2").children(":selected").text();

                if (tickedLandUseZones.length > 0) {
                    $("#lblPlanningZones").hide();
                    searchManager.getZoneGeometry(selectedLga, tickedLandUseZones, lgaName);

                    showLocationSearch(true);
                    openSection();
                }
                else {
                    var errorlabel = $("<label>").text('Please select between 1 and 7 zones.').attr({ "for": "cbxLocalGovernmentArea2", "class": "error" });
                    $("#cbxLocalGovernmentArea2").parent().append(errorlabel);
                }
            }
            else {
                var errorlabel = $("<label>").text('Please select an LGA.').attr({ "for": "cbxLocalGovernmentArea2", "class": "error" });
                $("#cbxLocalGovernmentArea2").parent().append(errorlabel);
            }
        }        

        return false;
    });

    $("#btnLandUseClear").click(function (e) {
        showMapPage();
        clearLandUseSearch();

        return false;
    });

    $("#btnLGASearch").click(function (e) {
        showMapPage();

        // Remove any existing error messages

        $("#lgaForm").find("label").remove(".error");
        $("#lgaForm").find(".error").removeClass("error");

        var selectedLga = $("#cbxLocalGovernmentArea").children(":selected").attr("value");

        if (selectedLga) {
            if ($("#lgaLayerPanel input[type='checkbox']:checked").length > 0) {

                zoomToBounds($("#cbxLocalGovernmentArea").children(":selected"));

                hideAllLegends();
                layerManager.clearVisibility();

                $("#lgaLayerPanel input[type='checkbox']:checked").each(function (index, item) {
                    var layerId = $(item).data("layerid");
                    layerManager.setVisibility(layerId, true);
                });

                showLocationSearch(true);
                openSection();
            }
            else {
                var errorlabel = $("<label>").text('Please select between 1 and 14 layers.').attr({ "for": "cbxLocalGovernmentArea", "class": "error" });
                $("#cbxLocalGovernmentArea").parent().append(errorlabel);
            }
        }
        else {
            var errorlabel = $("<label>").text('Please select an LGA.').attr({ "for": "cbxLocalGovernmentArea", "class": "error" });
            $("#cbxLocalGovernmentArea").parent().append(errorlabel);
        }

        return false;
    });

    $("#btnLGAClear").click(function (e) {
        showMapPage();
        clearLGALayersSearch();

        return false;
    });

    $("#btnGrowthAreaSearch").click(function (e) {
        showMapPage();

        // Remove any existing error messages

        $("#growthAreaForm").find("label").remove(".error");
        $("#growthAreaForm").find(".error").removeClass("error");

        var selectedArea = $("#cbxASGrowthAreas").children(":selected").attr("value");

        if (selectedArea) {
            if ($("#growthAreaLayerPanel input[type='checkbox']:checked").length > 0)
            {
                zoomToBounds($("#cbxASGrowthAreas").children(":selected"));

                hideAllLegends();
                layerManager.clearVisibility();

                $("#growthAreaLayerPanel input[type='checkbox']:checked").each(function (index, item) {
                    var layerId = $(item).data("layerid");
                    layerManager.setVisibility(layerId, true);
                });

                showLocationSearch(true);
                openSection();
            }
            else
            {
                var errorlabel = $("<label>").text('Please select between 1 and 14 layers.').attr({ "for": "cbxASGrowthAreas", "class": "error" });
                $("#cbxASGrowthAreas").parent().append(errorlabel);
            }
        }
        else {
            var errorlabel = $("<label>").text('Please select a Growth Area or Precinct.').attr({ "for": "cbxASGrowthAreas", "class": "error" });
            $("#cbxASGrowthAreas").parent().append(errorlabel);
        }

        return false;
    });

    $("#btnGrowthAreaClear").click(function (e) {
        showMapPage();

        clearGrowthAreaLayersSearch();

        return false;
    });

    $('#cbxLocalGovernmentArea').change(function () {

        showMapPage();

        $("#cbxLocalGovernmentArea option:selected").each(function () {

            //------------------------------------------------------------------
            // Remove and error messages
            //------------------------------------------------------------------

            $("#lgaForm").find("label").remove(".error");
            $("#lgaForm").find(".error").removeClass("error");

            //------------------------------------------------------------------
            // Clear any exising results
            //------------------------------------------------------------------

            clearSearchResults();

            //------------------------------------------------------------------
            // We should turn off all the layers that are switched on otherwise
            //  we will need to set the checked status of the layers being 
            //  displayed. 
            //------------------------------------------------------------------

            //hideAllLegends();
            //layerManager.clearVisibility();

            //------------------------------------------------------------------
            // Zoom to the selected LGA
            //------------------------------------------------------------------

            var lgaId = $(this).val();

            if (lgaId != "[Select LGA]") {
                
                //------------------------------------------------------------------
                // Get a list of layers that intersect the selected lga.
                //  on success the 'display_lga_layers' event will be fired
                //  on searchManager
                //------------------------------------------------------------------
                var lgaName = $(this).text();
                searchManager.getLGALayers(lgaId, lgaName);
                $("#lblPlanningZones").hide();
            }
        });
    });

    $('#cbxLocalGovernmentArea2').change(function () {
        showMapPage();

        //------------------------------------------------------------------
        // Hide the error label
        //------------------------------------------------------------------

        $("#lblLocalGovernmentArea2").hide();


        $("#cbxLocalGovernmentArea2 option:selected").each(function () {

            //------------------------------------------------------------------
            // Remove and error messages
            //------------------------------------------------------------------

            $("#lgaZoneForm").find("label").remove(".error");
            $("#lgaZoneForm").find(".error").removeClass("error");

            //------------------------------------------------------------------
            // Clear any exising results
            //------------------------------------------------------------------

            clearSearchResults();


            //------------------------------------------------------------------
            // Clear the current visible layers
            //------------------------------------------------------------------

            //hideAllLegends();
            //layerManager.clearVisibility();

            //------------------------------------------------------------------
            // Zoom to the selected LGA
            //------------------------------------------------------------------

            var lgaId = $(this).val();
            if (lgaId != "[Select LGA]") {

                //------------------------------------------------------------------
                // Get a list of layers that intersect the selected lga.
                //  on success the 'display_lga_layers' event will be fired
                //  on searchManager
                //------------------------------------------------------------------
                var lgaName = $(this).text();
                searchManager.getLGAZones(lgaId, lgaName)
            }
        });
    });

    $('#cbxASGrowthAreas').change(function () {
        showMapPage();

        $("#cbxASGrowthAreas option:selected").each(function () {

            //------------------------------------------------------------------
            // Remove and error messages
            //------------------------------------------------------------------

            $("#growthAreaForm").find("label").remove(".error");
            $("#growthAreaForm").find(".error").removeClass("error");

            //------------------------------------------------------------------
            // Clear any exising results
            //------------------------------------------------------------------

            clearSearchResults();

            //------------------------------------------------------------------
            // We should turn off all the layers that are switched on otherwise
            //  we will need to set the checked status of the layers being 
            //  displayed. 
            //------------------------------------------------------------------

            //hideAllLegends();
            //layerManager.clearVisibility();

            //------------------------------------------------------------------
            // Zoom to the selected growth area
            //------------------------------------------------------------------

            var areaId = $(this).val();

            if (areaId != "[Select Growth Area]") {

                var areaName = $(this).text();
                searchManager.getGrowthAreaLayers(areaId, areaName)
            }
        });
    });

    //------------------------------------------------------------------
    // Build the list of layer objects based on the details returned 
    //  from a call to the layer manager
    //------------------------------------------------------------------

    layerManager.loadLayers();

    //------------------------------------------------------------------
    // Attach the click events
    //------------------------------------------------------------------

    $(".metadata").click(function () {
        showMapPage();

        var metadataLink = $(this).data("link");

        if (metadataLink != undefined) {
            var url = metadataLink;
            if (metadataLink == "") {
                var assetId = $(this).data("assetid");
                url = '/Metadata/Get/?assetId=' + assetId;
            }
            window.open(url, "layerMetadata", 'width=800, height=400, toolbar=0, scrollbars=0, location=0, resizable=1').focus();
        }
        return false;
    });

    $(".toggleSection").click(function (e) {
        showMapPage();

        var sectionClicked = $($(this).parent().parent().parent()[0]);

        if (sectionClicked.hasClass("openPanel")) {
            sectionClicked.removeClass("openPanel").addClass("closedPanel");
        }
        else {
            $(".themeSection").removeClass("openPanel").removeClass("closedPanel").addClass("closedPanel");

            sectionClicked.removeClass("closedPanel").addClass("openPanel");
        }

        $('#lowerPanel').jScrollPane();
    });

    $(".themeTitle").click(function (e) {
        showMapPage();

        var sectionClicked = $($(this).parent().parent()[0]);

        if (sectionClicked.hasClass("openPanel")) {
            sectionClicked.removeClass("openPanel").addClass("closedPanel");
        }
        else {
            $(".themeSection").removeClass("openPanel").removeClass("closedPanel").addClass("closedPanel");

            sectionClicked.removeClass("closedPanel").addClass("openPanel");
        }

        $('#lowerPanel').jScrollPane();

    });

    //------------------------------------------------------------------
    // Add an click event to toggle the visibility of a selected layer
    //------------------------------------------------------------------

    $(".toggleLegend").click(function () {
        showMapPage();

        var layerClicked = $($(this).parent().parent().parent()[0]);

        showLegend(layerClicked, !layerClicked.hasClass("openLegend"));

        $('#lowerPanel').jScrollPane();
    });

    //------------------------------------------------------------------
    // Add click events to toggle the visibility of each advanced search
    //  panel
    //------------------------------------------------------------------

    $(".advancedSearchPanelHeader").click(function (e) {
        setAdvancedSearchPanel($($(this).parent()[0]));

        //------------------------------------------------------------------
        // Stop the event from propagating
        //------------------------------------------------------------------

        stopEventPropagation(e);
    });

    $(".sectionTitle").click(function (e) {
        setAdvancedSearchPanel($($(this).parent().parent()[0]));

        //------------------------------------------------------------------
        // Stop the event from propagating
        //------------------------------------------------------------------

        stopEventPropagation(e);
    });

    $(".toggleSearchSection").click(function (e) {
        setAdvancedSearchPanel($($(this).parent().parent().parent()[0]));

    //------------------------------------------------------------------
        // Stop the event from propagating
        //------------------------------------------------------------------

        stopEventPropagation(e);
    });

    //------------------------------------------------------------------
    // Register a resize event and set the initial layout of the page
    //------------------------------------------------------------------

    $(window).resize(function () {
        resizeScreen();
    });

    resizeScreen();

    $("#searchButtons").height($("#searchControl").height());

    $("#btnSearch").innerHeight($("#searchButtons").innerHeight());
    $("#btnSearch").innerWidth($("#btnSearch").innerHeight());

    $("#txtBasicSearchText").innerWidth($("#searchControl").width() -$("#searchButtons").width()-14 );

    //---------------------------------------------------------------
    // Show the welcome view
    //---------------------------------------------------------------

    var data = new SearchResult();
    data.state = "QLD";
    updateAdminResultsDisplay(data);

    //---------------------------------------------------------------
    // Add listener to update the layers state when the map zooms
    //---------------------------------------------------------------

    google.maps.event.addListener(map, 'zoom_changed', function () {
        layerManager.checkLayerControlVisibility();
    });

    //---------------------------------------------------------------
    //  Zoom the map to the appropriate place, Enable the require 
    //  layers or Perform the required search
    //---------------------------------------------------------------

    if (lgaParameter || parcelParameter) {

        if (parcelParameter) {
            //---------------------------------------------------------------
            // parcel search, if both LGA and lot/plan are shown then LPS 
            //  takes precedence
            //---------------------------------------------------------------

            var lot = parcelParameter.split('\\')[0];
            var plan = parcelParameter.split('\\')[1];

            if (zoomLevelParameter) {
                searchManager.findByLotPlanSection(lot, plan, null, zoomLevelParameter);
            } else {
                searchManager.findByLotPlanSection(lot, plan, null);
            }

            hideWelcome(true);

        } else {
            //---------------------------------------------------------------
            // We have been passed an LGA code so we need to set the extent
            //  and display its name
            //---------------------------------------------------------------

            var lgaFound = false;

            $.each(lgas, function (index, item) {
                if (item.AbsCode == lgaParameter) {
                    zoomToExtents(item.extents, 0);

                    //put a marker in the middle
                    var lgaCentroid = new google.maps.LatLng(item.Centroid.x, item.Centroid.y);
                    searchManager.createMarker('LGA', lgaCentroid, false);

                    var lgaResult = new SearchResult();
                    lgaResult.admin = item.Name;
                    lgaResult.state = "QLD";
                    updateAdminResultsDisplay(lgaResult);

                    lgaFound = true;
                    hideWelcome(true);
            }
            });
        }

        //---------------------------------------------------------------
        // Zoom to a level if needed
        //---------------------------------------------------------------

        if (zoomLevelParameter) {
            var preZoomIdleListener = google.maps.event.addListener(map, "idle", function () {
                var level = parseInt(zoomLevelParameter);

                if (!isNaN(level)) {
                    map.setZoom(level);
                    hideWelcome();
                }
                google.maps.event.removeListener(preZoomIdleListener);
            });
        }

        //---------------------------------------------------------------
        // Turn on layers, but only zoom if we haven't set one
        //---------------------------------------------------------------

        if (layersParameter) {
            var layersToEnable = layersParameter.split('|');
            if (layersToEnable.length > 0) {

                //---------------------------------------------------------------
                // If there is more than 14 layers specified, get the first 14
                //  layers and ignore the rest
                //---------------------------------------------------------------

                if (layersToEnable.length > 14)
                    layersToEnable = layersToEnable.slice(0, 14);

                //---------------------------------------------------------------
                // When the map is ready, load the layers.
                //---------------------------------------------------------------

                var preLayerLayerIdleListener = google.maps.event.addListener(map, "idle", function () {
                    layerManager.loadLayers(layersToEnable);
                    isWelcomeViewShown = false;
                    google.maps.event.removeListener(preLayerLayerIdleListener);
                });
            }

            hideWelcome(true);
        }
    }

    google.maps.event.addListener(map, "bounds_changed", function () {
        checkAccessToken();
    });


    $('input, textarea').placeholder();
});

function sendFeedbackFailure() {
    var pageContent = $("#feedback .panelContent");

    if (pageContent.length > 0) {
        pageContent.jScrollPane();
    }
}

function stopEventPropagation(e) {
    if (e.stopPropagation) {
        e.stopPropagation();   // W3C model
    } else {
        e.cancelBubble = true; // IE model
    }
}

function hideShareOptions() {
    $('#shareOptions').hide();
}

function hideAllLegends() {

    $(".themeSectionPanel .layer").removeClass("openLegend").addClass("closedLegend");

    $('#lowerPanel').jScrollPane();
}

function showLegend(layer, visible) {

    if (visible) {
        layer.removeClass("closedLegend").addClass("openLegend");
    }
    else {
        layer.removeClass("openLegend").addClass("closedLegend");
    }

    $('#lowerPanel').jScrollPane();
}

function showLocationSearch(clearPlace)
{
    if (clearPlace) {
        clearBasicPlace();
    }

    $("#locationSearchTypeContainer").removeClass("active").addClass("active");
    $("#advancedSearchTypeContainer").removeClass("active");

    $("#advancedSearchContainer").hide();
    $("#basicSearchContainer").show();

    $("#welcome").hide();

    $("#lowerPanel").show();
    $("#layerManager").show();

    $("#searchHistory").hide();


    resizeScreen();
    //$("#lowerPanel").jScrollPane();
}

function showAdvancedSearch() {

    //---------------------------------------------------------------
    // We need to make sure that all search results are cleared and
    //  all panels are closed
    //---------------------------------------------------------------

    setAdvancedSearchPanel();
    clearAdvancedSearchCriteria();

    clearSearchResults();

    //---------------------------------------------------------------
    // Make sure the map page is visible
    //---------------------------------------------------------------

    showMapPage();

    //---------------------------------------------------------------
    // Make sure the visibility and style of elements are set
    //---------------------------------------------------------------

    $("#locationSearchTypeContainer").removeClass("active");
    $("#advancedSearchTypeContainer").removeClass("active").addClass("active");

    $("#advancedSearchContainer").show();
    $("#basicSearchContainer").hide();

    $("#searchHistory").hide();
    $("#welcome").hide();
    $("#lowerPanel").hide();
}

function setAdvancedSearchPanel(section) {
    showMapPage();

    //---------------------------------------------------------------
    // We will toggle the visibility of the select section. If no 
    //  section is passed then we will close all sections
    //---------------------------------------------------------------

    if (section) {
        if (section.hasClass("openPanel")) {
            section.removeClass("openPanel").addClass("closedPanel");
        }
        else {

            $(".advancedSearchPanel").removeClass("openPanel").removeClass("closedPanel").addClass("closedPanel");

            section.removeClass("closedPanel").addClass("openPanel");

            $('.scrollableblock').jScrollPane();
        }
    }
    else {
        $(".advancedSearchPanel").removeClass("openPanel").removeClass("closedPanel").addClass("closedPanel");
        $('.scrollableblock').jScrollPane();
    }
}

function clearAdvancedSearchCriteria() {
    clearLotPlanSearch()
    clearProximitySearch();
    clearLGALayersSearch(true);
    clearGrowthAreaLayersSearch(true);
    clearLandUseSearch(true);
}


function clearLotPlanSearch() {
    $("#txtASLotNumber").val('');
    $("#txtASLotNumber").attr("required", true);
    $("#lblASLotNumber").text('Enter lot number*');

    $("#txtASPlanNumber").val('');
    $("#txtASSectionNumber").val('');

    $("#lotPlanDetailsForm").find("label").remove(".error");
    $("#lotPlanDetailsForm").find(".error").removeClass("error");
}

function clearProximitySearch() {
    // clear the proximity place
    clearProximityPlace();

    // remove all validation messages or styling
    $("#proximityDetailsForm").find("label").remove(".error");
    $("#proximityDetailsForm").find(".error").removeClass("error");

    // Reselect the first range option
    $("#cbxRange").val($("#cbxRange option:first").val());
}

function clearLGALayersSearch(delayScreenUpdate) {
    //hideAllLegends();
    //layerManager.clearVisibility();

    $("#cbxLocalGovernmentArea").val($("#cbxLocalGovernmentArea option:first").val());
    $("#lgaLayerPanel").empty();

    $("#lgaForm").find("label").remove(".error");
    $("#lgaForm").find(".error").removeClass("error");


    if (!delayScreenUpdate) {
        $('.scrollableblock').jScrollPane();
    }
}

function clearGrowthAreaLayersSearch(delayScreenUpdate) {
    //hideAllLegends();
    //layerManager.clearVisibility();

    $("#cbxASGrowthAreas").val($("#cbxASGrowthAreas option:first").val());
    $("#growthAreaLayerPanel").empty();

    $("#growthAreaForm").find("label").remove(".error");
    $("#growthAreaForm").find(".error").removeClass("error");
    
    if (!delayScreenUpdate) {
        $('.scrollableblock').jScrollPane();
    }
}

function clearLandUseSearch(delayScreenUpdate) {
    searchManager.clearPolygons();

    $("#cbxLocalGovernmentArea2").val($("#cbxLocalGovernmentArea2 option:first").val());

    $("#lgaZoneForm").find("label").remove(".error");
    $("#lgaZoneForm").find(".error").removeClass("error");

    $("#planningZonePanel").empty();

    if (!delayScreenUpdate) {
        $('.scrollableblock').jScrollPane();
    }

}

function showDataNotAvailable()
{
    $("#data-not-available").dialog({
        modal: true,
        resizable: false,
        closeOnEscape: false,
        width: 500,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
});

    resizeScreen();
}


function proximity_PlaceChanged() {
    showMapPage();

    $("#lblASProximityAddress").hide();

    clearBasicPlace();

    proximityPlaceChanged(proximitySearchBox.getPlace());

    return false;
}

function basicSearch_PlaceChanged() {
    clearProximityPlace();

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

function clearBasicPlace() {

    if (basicSearchBox.getPlace()) {
        $("#txtBasicSearchText").val('');

        if (basicSearchPlaceChangedListener) {
            google.maps.event.removeListener(basicSearchPlaceChangedListener);
        }

        basicSearchBox = new google.maps.places.Autocomplete(
            $("#txtBasicSearchText").get(0),
            {
                componentRestrictions: { country: "au" },
                bounds: mapBounds
            }
        );

        basicSearchPlaceChangedListener = google.maps.event.addListener(basicSearchBox, 'place_changed', function () {
            basicSearch_PlaceChanged();
        });
    }
}

function clearProximityPlace() {
    $("#txtASProximityAddress").val('');

    if (proximitySearchBox.getPlace()) {
        if (proximityPlaceChangedListener) {
            google.maps.event.removeListener(proximityPlaceChangedListener);
        }

        proximitySearchBox = new google.maps.places.Autocomplete(
            $("#txtASProximityAddress").get(0),
            {
                componentRestrictions: { country: "au" },
                bounds: mapBounds
            }
        );

        proximityPlaceChangedListener = google.maps.event.addListener(proximitySearchBox, 'place_changed', function () {
            proximity_PlaceChanged();
        });
    }
}

function checkAccessToken() {

    //get the time difference between now and when the current access token in the cache was retrieved.
    var diff = Math.abs(new Date() - gmeAccessTokenRetrieved); //time difference in milliseconds

    //a token has a lifetime of 60 minutes, therefore we can guarantee that the maximum valid lifetime 
    //of a token will be 60 mins minus the number of minutes the token is held in the cache.
    var guaranteedTokenAccessTime = 3600000 - (gmeAccessTokenCachePeriodInMinutes * 60 * 1000);
    //var guaranteedTokenAccessTime = 6000;

    //if the difference between the time the token was retrieved and now is greater than the guaranteed 
    //access token lifetime then we need to go back to the server and update our access token. This is
    //to prevent us from holding on to an expired access token.
    if (diff >= guaranteedTokenAccessTime) {
        if (!accessTokenCheckInProgress) {
            //need to refresh token
            accessTokenCheckInProgress = true;
            $.ajax({
                url: "/Map/GetAccessToken",
                dataType: "json",
                success: function (response) {
                    gmeAccessToken = response.GMEAccessToken;
                    gmeAccessTokenRetrieved = new Date();
                    layerManager.UpdateLayerAccessToken();
                    accessTokenCheckInProgress = false;
                },
                error: function (status, error) {
                    accessTokenCheckInProgress = false;
                }
            });
        }
    }
};


function selectCreatePin() {
    createPinToolActive = true;

    // Res style the icon to allow cancelling
    $("#btnDropPin").removeClass("ui-button-droppin").addClass("ui-button-canceldroppin");
    $("#btnDropPin").attr("title", "Click here to cancel the dropping of a location from the map.");

    drawingManager.setDrawingMode(google.maps.drawing.OverlayType.MARKER);
}

function unSelectCreatePin() {
    createPinToolActive = false;

    $("#btnDropPin").removeClass("ui-button-canceldroppin").addClass("ui-button-droppin")
    $("#btnDropPin").attr("title", "Click here and then click on the map to select a location from the map.");

    drawingManager.setDrawingMode(null);

}

function toggleNavigation() {

    if ($("#navigationPanel").hasClass("openNavPanel")) {
        $("#navigationPanel").removeClass("openNavPanel").addClass("closedNavPanel");
        $("#navigationPanel").hide();

        $("#btnToggleNavigation").removeClass("openNavPanel").addClass("closedNavPanel");
    }
    else {
        $("#navigationPanel").removeClass("closedNavPanel").addClass("openNavPanel");
        $("#navigationPanel").show();

        $("#btnToggleNavigation").removeClass("closedNavPanel").addClass("openNavPanel");
    }

    resizeScreen();

    return false;
}

function resizeScreen() {
    $('#shareOptions').hide();
    $("#contentPanel").height($(window).height() - $("#header").height() - 2);

    if ($("#navigationPanel").is(":visible")) {
        //------------------------------------------------------------------
        // The folowing is a work around an issue where a white bar is visible 
        /// between the navgation panel and the map panel. 
        //------------------------------------------------------------------

        $("#mapPanel").width($("#contentPanel").width() - $("#navigationPanel").outerWidth());
        $("#mapPanel").width($("#contentPanel").width() - $("#navigationPanel").outerWidth());
    }
    else {
        $("#mapPanel").width($("#contentPanel").width());
    }

    google.maps.event.trigger(map, "resize");
    google.maps.event.trigger(map, "resize");

    $("#lowerPanel").height($("#navigationPanel").height() - $("#upperPanel").height() - 2);

    $("#lowerPanel").jScrollPane();

    $(".panelContent").jScrollPane();

}

function clearSearchResults() {
    //------------------------------------------------------------------
    // Clear the basic Search Results
    //------------------------------------------------------------------

    $("#searchResultsPanel").hide();

    $("#searchResultsPanel").html('');

    //------------------------------------------------------------------
    // Clear the markers
    //------------------------------------------------------------------

    searchManager.clearMarkers();

    //------------------------------------------------------------------
    // Clear the polygons
    //------------------------------------------------------------------

    searchManager.clearPolygons();

    //------------------------------------------------------------------
    // Clear the zones
    //------------------------------------------------------------------

    $("#planningZonePanel").html('');

    //------------------------------------------------------------------
    // Clear the lga layers
    //------------------------------------------------------------------

    $("#lgaLayerPanel").html('');

    //------------------------------------------------------------------
    // Clear the growth area layers
    //------------------------------------------------------------------

    $("#growthAreaLayerPanel").html('');

    //------------------------------------------------------------------
    // Clear feature details
    //------------------------------------------------------------------

    hideFeatureDetails();

}

function zoomToBounds(option) {

    var bottom = option.data("extent-bottom");
    var top = option.data("extent-top");
    var left = option.data("extent-left");
    var right = option.data("extent-right");

    var sw = new google.maps.LatLng(bottom, left);
    var ne = new google.maps.LatLng(top, right);

    var bounds = new google.maps.LatLngBounds(sw, ne);

    if (bounds) {
        map.fitBounds(bounds);

        if (map.getZoom() < 13) {
            map.setZoom(13);
        }
    }
}

function zoomToExtents(extents, expandBy) {
    var mapBounds;

    if (expandBy) {
        var width = extents.Right - extents.Left;
        var height = extents.Top - extents.Bottom;

        var widthDelta = width * expandBy;
        var heightDelta = height * expandBy;

        mapBounds = new google.maps.LatLngBounds(
            new google.maps.LatLng(extents.Bottom - heightDelta, extents.Left - widthDelta, false),
            new google.maps.LatLng(extents.Top + heightDelta, extents.Right + widthDelta, false)
        );
    }
    else {
        mapBounds = new google.maps.LatLngBounds(
        new google.maps.LatLng(extents.Bottom, extents.Left, false),
        new google.maps.LatLng(extents.Top, extents.Right, false));
    }

    map.fitBounds(mapBounds);
}

function zoomToLatLng(lat, lng, zoomLevel) {
    var latlng = new google.maps.LatLng(lat, lng);

    if (latlng) {
        map.panTo(latlng);

        if (zoomLevel) {
            map.setZoom(zoomLevel);
        }
    }

}



function getSectionExpandedState(visible) {
    return visible == true ? "openPanel" : "closedPanel";
}

function getLegendExpandedState(visible) {
    return visible == true ? "openLegend" : "closedLegend";
}

function getLayerCheckedState(checked) {

    return checked == true ? "checked" : "";
}

function basicPlaceChanged() {

    if ((selectedPlace) && (selectedPlace.geometry)) {

        searchManager.getParcel(selectedPlace.geometry.location.lat(), selectedPlace.geometry.location.lng(), null, selectedPlace);

        $('#txtASProximityAddress').val('');
    }
}

function proximityPlaceChanged(place) {

    //searchManager.createMarker(place.name, place.geometry.location, true);

    //var bounds = new google.maps.LatLngBounds();
    //bounds.extend(place.geometry.location);

    //map.panTo(place.geometry.location);
    //map.setZoom(17);
}

function processAdminResponse(responseData) {

    if (responseData.isState) {

        if (responseData.lat && responseData.lng) {
            zoomToLatLng(responseData.lat, responseData.lng, 7);
        }
    }
    else {
        if (responseData.lat && responseData.lng) {
            zoomToLatLng(responseData.lat, responseData.lng, 13);

        }
    }

    showLocationSearch(true);

    updateAdminResultsDisplay(responseData);

    openSection();
}

function processNoMatchResponse() {
    
    hideSearchResults();

    $("#noMatchDialog").dialog({
        modal: true,
        resizable: false,
        closeOnEscape: false,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    });
    resizeScreen();
}

function processCadastreResponse(responseData) {
    searchManager.loadParcelPolygons(responseData.parcel);

    //---------------------------------------------------------------
    // Update the extent of the map by zooming to the boundaries of 
    //  the polygons.
    //---------------------------------------------------------------

    if (responseData.parcel) {
        if (responseData.parcel.BufferedGeometry) {
            zoomToExtents(responseData.parcel.BufferedGeometry.extents, 1);
        } else if (responseData.parcel.Geometry) {
            zoomToExtents(responseData.parcel.Geometry.extents, 1);
        }
    } else if (responseData.lat && responseData.lng) {
        //dont specify a zoom level here, this ensures that if there is no 
        //land parcel returned, the user is prompted and the current zoom 
        //level is maintained.
        zoomToLatLng(responseData.lat, responseData.lng);
    }

    if (responseData.zoomLevel) {
        var cadastreResponseZoom = google.maps.event.addListener(map, "idle", function () {
            var level = parseInt(responseData.zoomLevel);
            if (!isNaN(level)) {
                map.setZoom(parseInt(responseData.zoomLevel));
            }
            google.maps.event.removeListener(cadastreResponseZoom);
        });
    }

    searchManager.OpenParcelInfoWindow();

    //---------------------------------------------------------------
    // Turn the Land Use Layer on if it is present in the layers
    //---------------------------------------------------------------

    $.each(defaultVisibleLayers, function (index, shortCode) {
        if (containsLayer(responseData.parcel, shortCode)) {
            //---------------------------------------------------------------
            // Show layer
            //---------------------------------------------------------------

            layerManager.showLayer(shortCode);

            //---------------------------------------------------------------
            // Open the legend
            //---------------------------------------------------------------

            var layerClicked = $($(".ll_" + shortCode).parent().parent().parent()[0]);

            if (layerClicked) {
                if (layerClicked.hasClass("openLegend")) {
                    // do nothing and leave it open
                }
                else {
                    // open the legend
                    showLegend(layerClicked, true);
                }
            }
        }
    });

    showLocationSearch(true);

    updateBasicResultsDisplay(responseData);

    // Open the epi section

    openSection();
}



function containsLayer(parcel, shortcode) {
    var found = false;

    if (parcel) {
        if (parcel.IntersectingLayers) {
            $(parcel.IntersectingLayers).each(function (index, value) {
                if (value.ShortCode == shortcode) {
                    found = true;
                }
            });
        }

        if ((!found) && (parcel.BufferedIntersectingLayers)) {
            $(parcel.BufferedIntersectingLayers).each(function (index, value) {
                if (value.ShortCode == shortcode) {
                    found = true;
                }
            });
        }
    }


    return found;
}

function updateBasicResultsDisplay(data) {
        clearBasicSearchResults();

    if (data) {
        $("#basicResultsTemplate").tmpl(data).appendTo("#searchResultsPanel");
        $("#searchResultsPanel").show();
    }
    else {
        $("#searchResultsPanel").hide();
    }

    resizeScreen();
}

function updateAdminResultsDisplay(data) {
        clearBasicSearchResults();

    if (data) {
        $("#adminTemplate").tmpl(data).appendTo("#searchResultsPanel");
        $("#searchResultsPanel").show();
    }
    else {
        $("#searchResultsPanel").hide();
    }

    resizeScreen();
}

function showMapPage() {
    if (!$("#mapcanvas").is(":visible")) {
        $(".navButton").removeClass("ui-button-active");
        $(".panel").hide();

        $("#mapcanvas").show();
        $("#btnHome").addClass("ui-button-active");
    }
}

function clearBasicSearchResults() {
    $("#searchResultsPanel").html('');
}

function hideSearchResults() {

}

function hideWelcome(displaySection) {
    $("#locationSearchTypeContainer").removeClass("active").addClass("active");
    $("#advancedSearchTypeContainer").removeClass("active");

    $("#advancedSearchContainer").hide();
    $("#basicSearchContainer").show();

    $("#welcome").hide();

    $("#layerManager").show();
    $("#lowerPanel").show();

    if (displaySection) {
        openSection();
    }

    $("#lowerPanel").jScrollPane();
}

function hideFeatureDetails() {
    if ($("#metadataPopup").hasClass('ui-dialog-content')) {
        //$("#metadataPopup").empty();
        $("#metadataPopup").dialog("close");
    }
}

function validateLGALayerSelection(layerId, visible, source) {    
    //  We need to check the number of layers being checked and 

    $("#lgaForm").find("label").remove(".error");
    $("#lgaForm").find(".error").removeClass("error");

    if ($("#lgaLayerPanel input[type='checkbox']:checked").length > 14) {
        $(source).prop("checked", false);

        $("#numlayers-exceeded").dialog({
            modal: true,
            resizable: false,
            closeOnEscape: false,
            width: 500,
            buttons: {
                Ok: function () {
                    $(this).dialog("close");
                }
            }
        });
        resizeScreen();
    }
}

function validateGrowthAreaLayerSelection(layerId, visible, source) {
    //  We need to check the number of layers being checked and 

    $("#growthAreaForm").find("label").remove(".error");
    $("#growthAreaForm").find(".error").removeClass("error");

    if ($("#growthAreaLayerPanel input[type='checkbox']:checked").length > 14) {
        $(source).prop("checked", false);

        $("#numlayers-exceeded").dialog({
            modal: true,
            resizable: false,
            closeOnEscape: false,
            width: 500,
            buttons: {
                Ok: function () {
                    $(this).dialog("close");
                }
            }
        });
        resizeScreen();
    }
}

function hideFeedbackSent() {
    if ($("#feedbackSent").is(":visible")) {
        $("#feedbackSent").hide();
        $("#feedbackForm").show();
    }
}

function openSection(sectionId) {
    // Open the epi section

    //---------------------------------------------------------------
    // If the section id has not been specified then assume the section
    //  with an ID of 1
    //---------------------------------------------------------------

    if (!sectionId) {
        sectionId = 1;
    }

    var epiSection = $(".sl_" + sectionId);

    if (epiSection.hasClass("closedPanel")) {
        $(".themeSection").removeClass("openPanel").removeClass("closedPanel").addClass("closedPanel");

        epiSection.removeClass("closedPanel").addClass("openPanel");
    }

    resizeScreen();
}

function formatFeatureField(value) {
    if (value.indexOf("http") == 0) {
        return "<a href='" + value + "' target='_blank' title='External link ggle legislation site.'>" + value + "</a>"
    }
    else {
        return value;
    }
}


function getQueryParameter(key) {
    key = key.replace(/[*+?^$.\[\]{}()|\\\/]/g, "\\$&"); // escape RegEx meta chars
    var match = location.search.match(new RegExp("[?&]" + key + "=([^&]+)(&|$)"));
    return match && decodeURIComponent(match[1].replace(/\+/g, " "));
}

function strip(name) {
    //name = name.replace(/\/+/g, '')
    //name = name.replace(/:+/g, '')
    //name = name.replace(/\++/g, '')
    //name = name.replace(/\.+/g, '')

    name = name.replace(/\W+/g, '')


    return name;
}

function truncateLayer(name) {
    if (name.length > 35) {
        return name.substring(0, 34) + "...";
    }
    else {
        return name;
    }
}

function truncateZone(name) {
    if (name.length > 31) {
        return name.substring(0, 30) + "...";
    }
    else {
        return name;
    }
}

function openLegislationLink(link) {
    window.open(link, "_blank", "width=600, height=400, toolbar=0, scrollbars=0, location=0, resizable=1");
}

function showHelp() {
    windowRef = window.open('documents/PlanningViewerHelp.pdf', 'Help');
}