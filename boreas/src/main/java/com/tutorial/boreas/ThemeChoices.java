package com.tutorial.boreas;

import javax.faces.bean.*;

@ManagedBean
public class ThemeChoices {
    // Choices taken from http://www.primefaces.org/themes.html
    // Theme JARS downloaded from http://repository.primefaces.org/org/primefaces/themes/
    // Last updated 11/2012, version 1.0.8.
    public static final String[] POSSIBLE_THEMES =
      { "afterdark", "afternoon", "afterwork", "aristo", 
        "black-tie", "blitzer", "bluesky", "casablanca",
        "cruze", "cupertino", "dark-hive", "dot-luv",
        "eggplant", "excite-bike", "flick", "glass-x",
        "home", "hot-sneaks", "humanity", "le-frog",
        "midnight", "mint-choc", "overcast", "pepper-grinder",
        "redmond", "rocket", "sam", "smoothness",
        "south-street", "start", "sunny", "swanky-purse",
        "trontastic", "twitter bootstrap", "ui-darkness", "ui-lightness",
        "vader" };

    /** Returns a List of all possible themes, in alphabetical order.
     *  Since the current theme is not necessarily first, the facelets code
     *  must put a dummy entry like "--Choose Theme--" at the top of the
     *  list. Otherwise, you cannot trigger top "real" entry.
     *  See SmartThemeChoices for a class that fixes this problem.
     */
    public String[] getThemes() {
      return(POSSIBLE_THEMES);
    }

    public ThemeChoices() {
        // TODO Auto-generated constructor stub
    }

}
