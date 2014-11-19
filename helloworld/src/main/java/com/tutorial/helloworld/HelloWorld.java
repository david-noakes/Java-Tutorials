package com.tutorial.helloworld;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "helloWorld3", eager = true)
@RequestScoped
public class HelloWorld {
	   @ManagedProperty(value="#{message}")
	   private Message messageBean;

	   private String message;

   public HelloWorld() {
      System.out.println("HelloWorld started!");
   }
   public String getMessage() {
      return "Hello World!";
   }
}