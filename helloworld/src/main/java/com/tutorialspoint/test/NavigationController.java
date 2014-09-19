package com.tutorialspoint.test;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="navigationController", eager=true)
@RequestScoped
public class NavigationController
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @ManagedProperty("#{param.pageId}")
  private String pageId;
  
  public String moveToPage1()
  {
    return "page1";
  }
  
  public String moveToPage2()
  {
    return "page2";
  }
  
  public String moveToHomePage()
  {
    return "home";
  }
  
  public String processPage1()
  {
    return moveToPage1();
  }
  
  public String processPage2()
  {
    return moveToPage2();
  }
  
  public String showPage()
  {
    if (this.pageId == null) {
      return "home";
    }
    if ((this.pageId.equals("1")) || (this.pageId.equals("2"))) {
      return "page" + this.pageId;
    }
    return "home";
  }
  
  public String getPageId()
  {
    return this.pageId;
  }
  
  public void setPageId(String pageId)
  {
    this.pageId = pageId;
  }
}
