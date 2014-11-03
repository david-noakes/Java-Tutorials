package com.tutorial.boreas;

import javax.faces.bean.ManagedBean;


@ManagedBean
public class PrimeMaskBean {
  private String phone, phoneWithExt, ssn, productKey, license, message1, message2;

  public String getPhone() {
    return(phone);
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhoneWithExt() {
    return(phoneWithExt);
  }

  public void setPhoneWithExt(String phoneWithExt) {
    this.phoneWithExt = phoneWithExt;
  }

  public String getSsn() {
    return(ssn);
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getProductKey() {
    return(productKey);
  }

  public void setProductKey(String productKey) {
    this.productKey = productKey;
  }

  public String getLicense() {
    return(license);
  }

  public void setLicense(String license) {
    this.license = license;
  }
  

  public String getMessage1() {
    return(message1);
  }

  public void setMessage1(String message1) {
    this.message1 = message1;
  }

  public String getMessage2() {
    return(message2);
  }

  public void setMessage2(String message2) {
    this.message2 = message2;
  }

  public String register() {
    return("show-input-mask-data");
  }
  public PrimeMaskBean() {
      // TODO Auto-generated constructor stub
  }

}
