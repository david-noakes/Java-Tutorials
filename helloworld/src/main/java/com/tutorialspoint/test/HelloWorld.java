package com.tutorialspoint.test;

import java.io.PrintStream;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="helloWorld", eager=true)
public class HelloWorld
{
  public HelloWorld()
  {
    System.out.println("HelloWorld started!");
  }
  
  public String getMessage()
  {
    return "The messge = Hello World!";
  }
}
