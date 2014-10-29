

function LayerManager(parentMap) {
    var layers = [];
    var events = [];
    var map = parentMap;


    //----------------------------------------------------------------------
    // Define methods to raise events
    //----------------------------------------------------------------------

    function raiseLoaded(data) {
        if (events.hasOwnProperty("loaded")) {
            events["loaded"](data);
        } 
    }

    function raiseMaximumNumberExceeded() {
        if (events.hasOwnProperty("max_visible_exceeded")) {
            events["max_visible_exceeded"]();
        }
    }


    function raiseVisibilityChanged(layerId, visible) {
        if (events.hasOwnProperty("visibility_changed")) {
            events["visibility_changed"](layerId, visible);
        }
    }


    function raiseError(data) {
        if (events.hasOwnProperty("error")) {
            events["error"](data);
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

    function getAssetIds(assetId) {
        var assetIds = assetId.split(',');
        return assetIds;
    }

    function loadGmeLayers(item) {

        var mapsEngineLayers = [];

        if (item.IsVisible) {
            $.each(item.AssetIds, function (i, assetId) {
                var mapsEngineLayer = new google.maps.visualization.MapsEngineLayer({
                    layerId: assetId,
                    clickable: item.ShowInfoWindow,
                    suppressInfoWindows: !item.ShowInfoWindow,
                    zIndex: item.MapOrder,
                    accessToken: gmeAccessToken
                });
                mapsEngineLayers.push(mapsEngineLayer);
            });
        }

        item.MapsEngineLayers = mapsEngineLayers;
    }

    this.addListener = function (type, action) {
        events[type] = action;
    };


    this.isEnabled = function (layerId, currentZoom)
    {
        var enabled = false;

        $.each(layers, function (i, item) {
            if (item.LayerId == layerId) {
                if ((item.VisibleFrom <= currentZoom) && (currentZoom <= item.VisibleTo)) {
                    enabled = true;
                }
            }
        });

        return enabled;
    }

    //----------------------------------------------------------------------
    // Remove the selected layers from the map and make sure that they become
    //  unchecked in the table of contents and the info windows if visible
    //----------------------------------------------------------------------

    this.clearVisibility = function () {
        //----------------------------------------------------------------------
        // Clear all the layers from the map
        //----------------------------------------------------------------------

        $.each(layers, function (i, item) {

            if (item.MapsEngineLayers) {
                $.each(item.MapsEngineLayers, function (i, mapsEngineLayer) {

                    if (mapsEngineLayer.getMap() != null) {
                        mapsEngineLayer.setMap(null);

                        raiseVisibilityChanged(item.LayerId, false);
                    }
                });
            }
        });
    }

    this.checkLayerControlVisibility = function() {
        //---------------------------------------------------------------
        // We need to see if layers are within the current zoom limits
        //---------------------------------------------------------------
        $.each($(".layerVisibility"), function (i, item) {
           
            var isEnabled = layerManager.isEnabled($(item).data("layerid"), map.getZoom());

            $(this).prop("disabled", !isEnabled);

            var layer = $($(this).parent()[0]);

            if (isEnabled) {
                layer.removeClass("disabled");
            }
            else {
                layer.removeClass("disabled").addClass("disabled");
            }
        });
    }

    this.showLayer = function (shortCode) {
        var layerId;

        $.each(layers, function (index, item) {
            if (item.ShortCode == shortCode) {
                layerId = item.LayerId;
            }
        });

        if (layerId) {
            this.setVisibility(layerId, true);
        }
    }

    //----------------------------------------------------------------------
    // Resets the visibility status of all controls listening for the 
    //  visibility changed event
    //----------------------------------------------------------------------

    this.resetVisibility = function () {
        $.each(layers, function (i, item) {
            if (item.MapsEngineLayers) {
                $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                    if (mapsEngineLayer.getMap() != null) {
                        raiseVisibilityChanged(item.LayerId, item.IsVisible);
                    }
                });
            }
        });
    }

    //----------------------------------------------------------------------
    // Add the selected layers to the map and make sure that they become
    //  checked in the table of contents and the info windows if visible
    //----------------------------------------------------------------------
    
    this.setVisibility = function (layerId, visible) {
        var numVisibleLayers = 0;

        // Count the number of layers visible

        $.each(layers, function (i, item) {
            if (item.MapsEngineLayers) {
                $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                    if (mapsEngineLayer.getMap() != null) {
                        numVisibleLayers++;
                    }
                });
            }
        });

        $.each(layers, function (i, item) {
            if (item.LayerId == layerId) {
                if (visible) {
                    if (numVisibleLayers < 14) {

                        //if (_gaq) {
                        //    _gaq.push(['_trackEvent', 'Layer Visibility', 'Turn On', item.DisplayAlias]);
                        //}

                        //mark as visible
                        item.IsVisible = true;

                        // We need to make sure that the mapEngineLayer objects have been created

                        if (!item.MapsEngineLayers) {
                            loadGmeLayers(item);
                        }

                        if (item.MapsEngineLayers) {
                            $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                                mapsEngineLayer.setMap(map);
                            });

                        }
                    }
                    else {
                        //mark as hidden
                        item.IsVisible = false;

                        if (item.MapsEngineLayers) {
                            $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                                mapsEngineLayer.setMap(null);
                            });
                        }

                        visible = false;
                        raiseMaximumNumberExceeded();
                    }
                }
                else {
                    if (item.MapsEngineLayers) {
                        $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                            mapsEngineLayer.setMap(null);
                        });
                    }
                }

                raiseVisibilityChanged(layerId, visible);
            }
        });
    }

    this.loadLayers = function (visibleLayers) {
        //----------------------------------------------------------------------
        // The order of these layers is set by the map order attribute on the 
        //  layer
        //----------------------------------------------------------------------

        //----------------------------------------------------------------------
        // visibleLayers is array of shortcodes that will be visible this will 
        //  override the
        //----------------------------------------------------------------------


        raiseShowBusy();

        layers = [];

        $.ajax({
            url: "/Data/Layers",
            dataType: "json",
            success: function (data) {
                var numVisibleLayers = 0;

                $.each(data, function (index, item) {

                    item.AssetIds = getAssetIds(item.AssetId);

                    if (item.IsVisible) {
                        loadGmeLayers(item);
                    }

                    layers.push(item);

                    if (visibleLayers) {
                        if ($.inArray(item.ShortCode, visibleLayers) > -1) {
                            item.IsVisible = true;

                            if (!item.MapsEngineLayers) {
                                loadGmeLayers(item);
                            }

                            if (item.MapsEngineLayers) {
                                $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                                    mapsEngineLayer.setMap(map);
                                });
                            }

                            raiseVisibilityChanged(item.LayerId, true);
                        }
                    }
                    else {
                        if ((item.IsVisible == true) && (numVisibleLayers < 14)) {

                            if (!item.MapsEngineLayers) {
                                loadGmeLayers(item);
                            }

                            if (item.MapsEngineLayers) {
                                $.each(item.MapsEngineLayers, function (j, mapsEngineLayer) {
                                    numVisibleLayers++;
                                    mapsEngineLayer.setMap(map);
                                });
                            }

                            raiseVisibilityChanged(item.LayerId, true);
                        }
                        
                    }
                });

                raiseLoaded();
                raiseHideBusy();

            },
//            error: function (status, error) {
//                raiseHideBusy();
//                raiseError(status);
            error: function (status, error) {
                raiseHideBusy();
                //raiseError(status);
            }
        });
    }



    this.getGmeLayerMetadata = function (assetId) {
        var url = '/Metadata/Get/?assetId=' + assetId;
        var w = window.open(url, "_blank", "width=600, height=400, toolbar=0, scrollbars=0, location=0, resizable=1");
    }

    this.UpdateLayerAccessToken = function() {
        //get a list of the currently visible layers
        var visibleLayers = [];
        $.each(layers, function(index, item) {
            if (item.IsVisible) {
                visibleLayers.push(item.ShortCode);
            }
        });

        $.each(layers, function (index, item) {
            if (item.MapsEngineLayers) {
                $.each(item.MapsEngineLayers, function (i, gmeLayer) {
                    gmeLayer.set('accessToken', gmeAccessToken.AccessToken);
                });
            }
        });
    };
}
