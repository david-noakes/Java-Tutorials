package com.tutorial.boreas;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="registrationBean")

public class PrimeRegistrationBean {

    private String firstName, lastName, emailAddress, password;

    public String getFirstName() {
      return(firstName);
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return(lastName);
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getEmailAddress() {
      return(emailAddress);
    }

    public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
    }
    
    public void setPassword(String password) {
      this.password = password;
    }

    public String getPassword() {
      return(password);
    }
    
    public String getObscuredPassword() {
      return(firstLetter(password) + "..." + lastLetter(password));
    }
    // No validation needed because captcha already passed and
    // all entries are non-empty. Validating email addresses is hard,
    // but see http://stackoverflow.com/questions/2250820/java-email-extraction-regular-expression
    // for a good attempt that handles most cases.
    
    public String register1() {
      return("show-registration-1");
    }
    
    public String register2() {
      return("show-registration-2");
    }
    
    // s is already known to be non-null and at least two letters long
    
    private String firstLetter(String s) {
      return(s.substring(0, 1));
    }
    
    // s is already known to be non-null and at least two letters long
    
    private String lastLetter(String s) {
      int length = s.length();
      return(s.substring(length-1, length));
    }

    public PrimeRegistrationBean() {
        // TODO Auto-generated constructor stub
    }

}
