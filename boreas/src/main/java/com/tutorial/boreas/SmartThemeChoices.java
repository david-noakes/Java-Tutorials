package com.tutorial.boreas;

import java.util.*;

import javax.faces.bean.*;

@ManagedBean
public class SmartThemeChoices {

    /** Returns a List of all possible themes, with the current theme at the front. */
    
    public List<String> getThemes() {
      List<String> themes = new LinkedList<String>();
      for(String theme: ThemeChoices.POSSIBLE_THEMES) {
        themes.add(theme);
      }
      String currentTheme = ThemeUtils.currentTheme();
      themes.remove(currentTheme);
      themes.add(0, currentTheme);
      return(themes);
    }

    public SmartThemeChoices() {
        // TODO Auto-generated constructor stub
    }

}
