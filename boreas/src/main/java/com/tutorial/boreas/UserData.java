package com.tutorial.boreas;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userData", eager = true)
@SessionScoped
public class UserData implements Serializable {

   private static final long serialVersionUID = 1L;

   private SessionData session;
   private String name;
   private String password;
   private boolean loggedIn;
   private boolean loginValid;
   private String message = "";
   
   public static final String LOGIN_PAGE = "login?faces-redirect=true";
   public static final String MAIN_PAGE = "boreas?faces-redirect=true";
   
   public String getName() {
       return name;
   }
   public void setName(String name) {
       System.out.println("Setname ["+name+"]");
       this.name = name;
   }
   public String getPassword() {
       return password;
   }
   public void setPassword(String password) {
       System.out.println("Setpwd ["+password+"]");
       this.password = password;
   }    
   public String getMessage() {
       return message;
   }
   public void setMessage(String message) {
       this.message = message;
   }
   public String login(){
       System.out.println("Login: ["+name+"|"+password+"]");
       if (name.length()>0 && password.length()>0) {
           session.put(SessionData.USERNAME, name);
           message = "";
           return MAIN_PAGE;
       } else {
           session.put(SessionData.USERNAME, "");
           message = "Username or Password is invalid";
           if (name.length() == 0) {
               message = "Username is required";
           } else if (password.length() == 0) {
               message = "Password is required";
           }
           loggedIn = false;
           loginValid = false;
           return LOGIN_PAGE;
       }
   }    
   public String logout() {
       name = "";
       password = "";
       message = "";
       loggedIn = false;
       loginValid = true;
       session.put(SessionData.USERNAME, name);
       return LOGIN_PAGE;
   }

   public boolean isLoggedIn() {
       return loggedIn;
   }
   public boolean isLoginValid() {
       return loginValid;
   }

   public TimeZone getTimeZone() {
       return TimeZone.getDefault();
   }
   
   public UserData() {
       try {
           session = SessionData.getInstance();
       } catch (SQLException e) {
        // TODO Auto-generated catch block
           e.printStackTrace();
       }
       loginValid = true;  // default
       loggedIn = false;
       if (session.containsKey(SessionData.USERNAME)) {
           name = (String) session.get(SessionData.USERNAME);
           if (name.trim().length()>0) {
             loggedIn = true;   
           }
       } else {
           name = "";
       }
       password = "";
   }
   
}
