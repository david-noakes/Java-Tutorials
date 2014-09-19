package com.tutorialspoint.test;

import java.io.PrintStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="helloWorld2", eager=true)
@RequestScoped
public class HelloWorld2
{
  @ManagedProperty("#{message}")
  private Message messageBean;
  private String message;
  
  public HelloWorld2()
  {
    System.out.println("HelloWorld2 started!");
  }
  
  public String getMessage()
  {
    if (this.messageBean != null) {
      this.message = this.messageBean.getMessage();
    }
    return this.message;
  }
  
  public void setMessageBean(Message message)
  {
    this.messageBean = message;
  }
}
