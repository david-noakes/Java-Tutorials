package com.tutorial.boreas;

import java.io.*;
import javax.faces.bean.*;

@ManagedBean(name = "colorPreferences")
@SessionScoped

public class PrimeColourBean {

    private String foreground="0000ff", background="fdf5e6";

    public String getForeground() {
      return(foreground);
    }

    public void setForeground(String foreground) {
      this.foreground = foreground;
    }

    public String getBackground() {
      return(background);
    }

    public void setBackground(String background) {
      this.background = background;
    }
    
    public String getStyle() {
      String style =
        String.format("color: #%s; background-color: #%s",
                      foreground, background);
      return(style);
    }
    
    public String showSample() {
      return("show-colors");
    }

    public PrimeColourBean() {
        // TODO Auto-generated constructor stub
    }

}
