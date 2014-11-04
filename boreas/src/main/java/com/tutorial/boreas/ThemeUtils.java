package com.tutorial.boreas;

import javax.faces.context.*;

public class ThemeUtils {
    public static final String DEFAULT_THEME = "aristo"; // Specified by PrimeFaces as default
    
    public static String currentTheme() {
      String theme = DEFAULT_THEME;
      ExternalContext externalContext =
        FacesContext.getCurrentInstance().getExternalContext();
      String param = 
        externalContext.getInitParameter("primefaces.THEME");
      if (param != null) {
        theme = param;
      }
      return(theme);
    }

    private ThemeUtils() {} // Uninstantiatable class
}
