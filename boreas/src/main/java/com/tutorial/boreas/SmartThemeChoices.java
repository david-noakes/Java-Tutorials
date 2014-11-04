package com.tutorial.boreas;

import java.sql.SQLException;
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
      //String currentTheme = ThemeUtils.currentTheme();
      try {
        SessionData session = SessionData.getInstance();
        String currentTheme = (String) session.get("theme");
        themes.remove(currentTheme);
        themes.add(0, currentTheme);
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      return(themes);
    }

    public SmartThemeChoices() {
        // TODO Auto-generated constructor stub
    }

}
