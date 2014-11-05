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

    public static final String FACES_REDIRECT = "?faces-redirect=true";
    public static final String CONTEXT_MAIN_PAGE = "boreas";
    public static final String CONTEXT_EMPLOYEE_DETAIL = "employeeDetail";
    public static final String CONTEXT_EMPLOYEE_LIST = "employeeList";
    public static final String CONTEXT_EMPLOYEE_DETAIL_PF = "employeeDetailPF";
    public static final String CONTEXT_EMPLOYEE_LIST_PF = "employeeListPF";
	public static final String NAV_MAIN_PAGE = CONTEXT_MAIN_PAGE+FACES_REDIRECT;
	public static final String NAV_EMPLOYEE_LIST = CONTEXT_EMPLOYEE_LIST+FACES_REDIRECT;
	public static final String NAV_EMPLOYEE_DETAILS = CONTEXT_EMPLOYEE_DETAIL+FACES_REDIRECT;
    public static final String NAV_EMPLOYEE_LIST_PF = CONTEXT_EMPLOYEE_LIST_PF+FACES_REDIRECT;
    public static final String NAV_EMPLOYEE_DETAILS_PF = CONTEXT_EMPLOYEE_DETAIL_PF+FACES_REDIRECT;
	  
	/* 
	 * convention is context:destination
	 * context may be blank, in which case destination is an absolute page name  
	 */
	  
	  
	 public String showPage()  {
		try {
			sessionData = SessionData.getInstance();
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
				      return "page" + this.pageId+FACES_REDIRECT;
				}
		    	return this.pageId+FACES_REDIRECT;
		    } else {
		    	context = bits[0];
		    	destination = bits[1];
		    	sessionData.put(SessionData.CONTEXT, context);
		    	sessionData.put(SessionData.DESTINATION, destination);   
		    	if (context.equals(CONTEXT_EMPLOYEE_DETAIL) || context.equals(CONTEXT_EMPLOYEE_DETAIL_PF)) {
		    	    new EmployeeData().putEmployeeToSessionData();
		    	}
			    if (context.trim().length()>0) {
			    	return context+FACES_REDIRECT; 
			    }
			    
		    }
	    	sessionData.put(SessionData.CONTEXT, "");
	    	sessionData.put(SessionData.DESTINATION, NAV_MAIN_PAGE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return NAV_MAIN_PAGE;
	}
	  
	public String employeeDetailContext() {
		return CONTEXT_EMPLOYEE_DETAIL+":";
	}
    public String employeeDetailContextPF() {
        return CONTEXT_EMPLOYEE_DETAIL_PF+ ":";
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
