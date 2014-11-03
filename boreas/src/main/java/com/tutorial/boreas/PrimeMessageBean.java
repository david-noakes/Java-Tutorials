package com.tutorial.boreas;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="primeMessageBean")
public class PrimeMessageBean {

    private String message="", emailAddress="";
    private final static String DEFAULT_MESSAGE_1
      = "(None: Message Not Sent)";
    private final static String DEFAULT_EMAIL_1
      =  "(No Reply Email Given)";
    private final static String DEFAULT_MESSAGE_2="(Missing Message)";
    private final static String DEFAULT_EMAIL_2="(Missing Email)";
    
    public String getMessage() {
      return(message);
    }

    public void setMessage(String message) {
      this.message = message;
    }
    
    public String getEmailAddress() {
      return(emailAddress);
    }

    public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
    }
    
    public String getDialogMessage() {
      String template =
        "<p>Message that was sent to customer support:</p>\n" +
        "<hr/>\n" +
        "<div style='font-size: 120%%'>\n" +
        "%s\n" +
        "</div>\n" +  
        "<hr/>\n" +
        "<p>Replies will be sent to %s.</p>";
      if (!message.isEmpty() && !emailAddress.isEmpty()) {
        String dialogMessage =
          String.format(template, message, emailAddress);
        return(dialogMessage);
      } else {
        return("");
      }
    }
    
    public String getConfirmDialogMessage() {
      String msg = message;
      if (msg.isEmpty()) {
        msg = DEFAULT_MESSAGE_2;
      }
      String email = emailAddress;
      if (email.isEmpty()) {
        email = DEFAULT_EMAIL_2;
      }
      String template =
        "<p>Message:</p>\n" +
         "<hr/>\n" +
         "<div style='font-size: 120%%'>\n" +
          "%s\n" +
          "</div>\n" +
          "<hr/>\n" +
          "<p>Email for replies: %s.</p>";
      String confirmDialogMessage =
        String.format(template, msg, email);
      return(confirmDialogMessage);
    }

    public String showMessage() {
      return("show-message");
    }
    
    public String showMessage2() {
      if (message.isEmpty()) {
        message = DEFAULT_MESSAGE_1;
      }
      if (emailAddress.isEmpty()) {
        emailAddress = DEFAULT_EMAIL_1;
      }
      if (!message.isEmpty()) {
        sendMessageToCustomerSupport();
      }
      return(null); // So works if Ajax disabled
    }
    
    public String showMessage3() {
      if (!message.isEmpty()) {
        sendMessageToCustomerSupport();
      }
      return(null); // So works if Ajax disabled
    }
    
    private void sendMessageToCustomerSupport() {
      // Ignore message and tell customer 
      // it has been filed for consideration :-)
    }

    public PrimeMessageBean() {
        // TODO Auto-generated constructor stub
    }

}
