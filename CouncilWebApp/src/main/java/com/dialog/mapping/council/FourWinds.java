package com.dialog.mapping.council;

import java.io.PrintStream;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="FourWinds", eager=true)
public class FourWinds
{
  public FourWinds()
  {
    System.out.println("NorthWind started!");
  }
  
  public String getMessage()
  {
    return "Four Winds";
  }
}
