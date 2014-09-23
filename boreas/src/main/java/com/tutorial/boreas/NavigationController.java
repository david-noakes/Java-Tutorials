package com.tutorial.boreas;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="navigationController", eager=true)
@RequestScoped
public class NavigationController  implements Serializable {

	private static final long serialVersionUID = 1L;
	@ManagedProperty("#{param.pageId}")
	private String pageId;
	private String context;
	private String destination;
	private SessionData sessionData;

	public static final String NAV_MAIN_PAGE = "boreas";
	public static final String NAV_EMPLOYEE_LIST = "employeeList";
	public static final String NAV_EMPLOYEE_DETAILS = "employeeDetails";
	  
	/* 
	 * convention is context:destination
	 * context may be blank, in which case destination is an absolute page name  
	 */
	  
	  
	 public String showPage()  {
	    if (this.pageId == null) {
	    	System.out.println("NavigationController pageId=null");
	    	sessionData.put(SessionData.CONTEXT, "");
	    	sessionData.put(SessionData.DESTINATION, NAV_MAIN_PAGE);
	    	return NAV_MAIN_PAGE;
	    }
    	System.out.println("NavigationController pageId='"+pageId+"'");
	    String[] bits =    pageId.split(":");
	    if (bits.length == 1) {
	    	// absolute page reference
	    	destination = bits[0];
	    	sessionData.put(SessionData.CONTEXT, "");
	    	sessionData.put(SessionData.DESTINATION, destination);
		    if ((this.pageId.equals("1")) || (this.pageId.equals("2"))) {
			      return "page" + this.pageId;
			}
	    	return this.pageId;
	    } else {
	    	context = bits[0];
	    	destination = bits[1];
	    	sessionData.put(SessionData.CONTEXT, context);
	    	sessionData.put(SessionData.DESTINATION, destination);
		    if (context.trim().length()>0) {
		    	return context; 
		    }
		    
	    }
    	sessionData.put(SessionData.CONTEXT, "");
    	sessionData.put(SessionData.DESTINATION, NAV_MAIN_PAGE);
	    return NAV_MAIN_PAGE;
	}
	  
	public String employeeDetailContext() {
		return "employeeDetail:";
	}
	  
	  
	public NavigationController()  {
	    System.out.println("NavigationController started");
	    try {
			sessionData = SessionData.getInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	  
	  
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	  

}
