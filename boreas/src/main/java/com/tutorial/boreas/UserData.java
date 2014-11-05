package com.tutorial.boreas;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import java.security.InvalidAlgorithmParameterException;
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

import com.fugue.utilities.CipherUtility;

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
   private byte[] keyBytes =  new byte [] {78, (byte) 166, 42, 70, (byte) 251, 20, (byte) 142, 103, (byte) 157, (byte) 231, 76, 95, (byte) 171, 94, 57, 54};
   private String rubbish;
   
   //@ManagedProperty("#{param.myTheme}")
   //@SessionScoped
   private String myTheme = "bluesky";

   private SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
 
   
   public static final String LOGIN_PAGE = "Login_Faces?faces-redirect=true";
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
   public String getMyTheme() {
       return myTheme;
   }
   public void setMyTheme(String myTheme) {
       this.myTheme = myTheme;
       session.put("theme", this.myTheme);
   }
   public void saveTheme() {
       session.put("theme", this.myTheme);  // ajax calls this
   }
   
   public String login(){
       System.out.println("Login: ["+name+"|"+password+"]");
       if (name.length()>0 && validUidPwd(name, password)) {
           session.put(SessionData.USERNAME, name);
           message = "";
           EmployeeData eData = new EmployeeData();
           EmployeeDTO eDTO = eData.fetchEmpByUid(name);
           session.put(SessionData.USER_EMPLOYEE, eDTO);
           session.put("theme", myTheme);
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
           return ""; //LOGIN_PAGE;
       }
       
   }    
   public String logout() {
       name = "";
       password = "";
       message = "";
       loggedIn = false;
       loginValid = true;
       session.put(SessionData.USERNAME, name);
       return ""; //LOGIN_PAGE;
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
           rubbish = new String(keyBytes, "UTF-8");
       } catch (SQLException e) {
        // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (UnsupportedEncodingException e) {
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
   public byte[] encryptBC(String encryptMe) {
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
   public String decryptBC(byte[] decryptMe) {
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
       CipherUtility cyx = new CipherUtility();
       
       String ePwd;
        try {
            ePwd = cyx.encrypt(pwd, rubbish);
            if (eDTO.getPassword().equals(ePwd) || 
                    (pwd.equals(EmployeeDTO.BLANK) && eDTO.getPassword().equalsIgnoreCase(EmployeeDTO.BLANK))) {
                return true;
            }
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
       return false;
   }
}
