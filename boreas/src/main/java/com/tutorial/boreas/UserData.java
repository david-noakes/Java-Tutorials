package com.tutorial.boreas;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/// NOTE - Encryption requires the encryption jar file to go into the JRE runtime ..  jre/lib/ext directory
//         currently the bouncy castle jar file is "bcprov-jdk16-1.45.jar"
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
   private byte[] keyBytes =  new byte [] {78, -90, 42, 70, -5, 20, -114, 103, -99, -25, 76, 95, -85, 94, 57, 54};

   private SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
 
   
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
       if (name.length()>0 && validUidPwd(name, password)) {
           session.put(SessionData.USERNAME, name);
           message = "";
           EmployeeData eData = new EmployeeData();
           EmployeeDTO eDTO = eData.fetchEmpByUid(name);
           session.put(SessionData.USER_EMPLOYEE, eDTO);
           return MAIN_PAGE;
       } else {
           session.put(SessionData.USERNAME, "");
           session.put(SessionData.USER_EMPLOYEE, null);
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
       Security.addProvider(new BouncyCastleProvider());
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
   public byte[] encrypt(String encryptMe) {
       byte[] input = encryptMe.getBytes();
       int len = encryptMe.length() / 16;
       int rem = encryptMe.length() % 16;
       if (rem > 0) {
           len += 16;
       }
       byte[] cipherText = new byte[len];
       Cipher cipher;
    try {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        System.out.println("cipher text: " + new String(cipherText) + " bytes: " + ctLength);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return cipherText;   

   }
   public String decrypt(byte[] decryptMe) {
       byte[] plainText = new byte[decryptMe.length];
       String s = "";
       Cipher cipher;
       try {
           cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
           cipher.init(Cipher.DECRYPT_MODE, key);
           int ptLength = cipher.update(decryptMe, 0, decryptMe.length, plainText, 0);
           ptLength += cipher.doFinal(plainText, ptLength);
           System.out.println("plain text : " + new String(plainText).substring(0, ptLength) + " bytes: " + ptLength);
           s = new String(plainText).substring(0, ptLength);
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return s;           
   }
   
   public boolean validUidPwd(String uid, String pwd) {
       try {
        session = SessionData.getInstance();
       } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }
       EmployeeData eData = new EmployeeData();
       EmployeeDTO eDTO = eData.fetchEmpByUid(uid);
       if (eDTO == null) {
           return false;
       }
       byte[] ePwd = encrypt(pwd);
       String s = new String(ePwd);
       if (eDTO.getPassword().equals(s) || 
               (pwd.equals(EmployeeDTO.BLANK) && eDTO.getPassword().equalsIgnoreCase(EmployeeDTO.BLANK))) {
           return true;
       }
       
       return false;
   }
}
