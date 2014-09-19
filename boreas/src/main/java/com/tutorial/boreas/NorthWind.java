package com.tutorial.boreas;

import java.io.PrintStream;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="northWind", eager=true)
public class NorthWind
{
  public NorthWind()
  {
    System.out.println("NorthWind started!");
  }
  
  public String getMessage()
  {
    return "North Wind!";
  }
}
