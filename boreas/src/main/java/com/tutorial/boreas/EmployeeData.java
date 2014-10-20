package com.tutorial.boreas;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "employeeData", eager = true)
@SessionScoped
public class EmployeeData  implements Serializable{
	
	private SessionData sessionData = null;
	private Connection con = null;
    private static final long serialVersionUID = 1L;
    private ResultSet results = null;
    
    private String listFormMessage = "";
    
    private boolean readOnly;
    private boolean editMode;
    private boolean newMode;
    private boolean deleteMode;
    
    private static final String EMPLOYEE_DTO = "employeeDTO";
    private static final String EMPLOYEE_DTO_ORIG = "employeeDTO_orig";
    private static final String SHOW_HTML_ELEMENT = "block";
    private static final String HIDE_HTML_ELEMENT = "none";
    public static final String EMPLOYEE_LIST_FORM = "employeeList";
    public static final String EMPLOYEE_DETAIL_FORM = "employeeDetail";
    
	
    public List<EmployeeDTO> getEmployees() {
    	if (sessionData == null) {
    		getConnection();
    	}
        results = null;
        PreparedStatement pst = null;
        List<EmployeeDTO> records = new ArrayList<EmployeeDTO>();
        String sql = "select * from employees";
        //this fails on record names with spaces
        
        try {   
            pst = con.prepareStatement(sql);
            pst.execute();
            results = pst.getResultSet();

            while(results.next()){
               EmployeeDTO employee = new EmployeeDTO();
               //employee.morphFromDB(results);
               employee.morphUsingReflection(results);
               records.add(employee);				
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
        
        
        return records;
    }
    
    public EmployeeData() {
        super();
        readOnly = true;
        editMode = false;
        newMode = false;
        deleteMode = false;
        // TODO Auto-generated constructor stub
    }

    public EmployeeDTO getContextEmployee() {
    	if (sessionData == null) {
    		getConnection();
    	}
    	EmployeeDTO employee = new EmployeeDTO();
        ResultSet results = null;
        PreparedStatement pst = null;
        String sql = "select * from employees where ID = "+getDestination();
        
        try {   
            pst = con.prepareStatement(sql);
            pst.execute();
            results = pst.getResultSet();

            while(results.next()){
               //employee.morphFromDB(results);
               employee.morphUsingReflection(results);
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
        return employee;
    }
    /**
     * gets an employee from the database, based on the destination saved in the sessionData
     * it then stores this employeeDTO into sessionData
     * @return Employee
     */
    public EmployeeDTO putEmployeeToSessionData() {
    	EmployeeDTO employee = getContextEmployee();
    	sessionData.put(EMPLOYEE_DTO, employee);
    	EmployeeDTO clone = (EmployeeDTO) employee.clone();
        sessionData.put(EMPLOYEE_DTO_ORIG, clone); // for detecting changes in update mode
    	return employee;
    }
    public void startEditMode() {
        setEditMode(true);
        setReadOnly(false);
        setNewMode(false);
        setDeleteMode(false);
        // TODO - any other database stuff
    }
    
    public void startNewMode() {
        setNewMode(true);
        setReadOnly(false);
        setEditMode(false);
        setDeleteMode(false);
    }
    
    public void startDeleteMode() {
        setDeleteMode(true);
        setNewMode(false);
        setReadOnly(false);
        setEditMode(false);
    }
    
    public int updateEmployee(){
        if (sessionData == null) {
            getConnection();
        }
        int result=0;
        EmployeeDTO employee = (EmployeeDTO) sessionData.get(EMPLOYEE_DTO);
        EmployeeDTO orig     = (EmployeeDTO) sessionData.get(EMPLOYEE_DTO_ORIG);
        PreparedStatement pst = null;
        String sql = employee.toSQLString();
        
        try {   
            pst = employee.toUpdateSQLStatement(); // stuffs up - ODBC barfs with incorrect COUNT
           // pst = con.prepareStatement(sql);
            result=pst.executeUpdate(); 

         } catch (SQLException e) {
            e.printStackTrace();
         }
        return result;
        
    }
    public String confirmEdit(){
        //TODO - write to database
        // don't use the procedures, 
        EmployeeDTO employee = getEmployeeFromSessionData();
        if (employee.getID() == -1) {
            // insert
        } else {
            // update
            updateEmployee();
        }
        
        setEditMode(false);
        setReadOnly(true);
        setNewMode(false);
        setDeleteMode(false);
        
        return EMPLOYEE_LIST_FORM;
    }
    public void cancelEdit(){
        setEditMode(false);
        setReadOnly(true);
        setNewMode(false);
        setDeleteMode(false);
    }
    public String confirmDelete(){
        //TODO - write to database
        return EMPLOYEE_LIST_FORM;
    }
    public void cancelDelete(){
        setEditMode(false);
        setReadOnly(true);
        setNewMode(false);
        setDeleteMode(false);
    }
    /**
     * use the employee stored in sessionData
     * @return saved employeeDTO
     */
	public EmployeeDTO getEmployeeFromSessionData() {
		EmployeeDTO employee = (EmployeeDTO) sessionData.get(EMPLOYEE_DTO);
		return employee;
	}
     
    public Connection getConnection() {
	   if (sessionData == null) {
		  try {
			sessionData = SessionData.getInstance();
	  	  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		  }
	   }
  	   con = sessionData.getConnection();
	   return con;
    }
	public String getContext() {
		getConnection();
		return (String) sessionData.get(sessionData.CONTEXT);
	}
	
	public String getDestination(){
		getConnection();
		return (String) sessionData.get(sessionData.DESTINATION);
	}
	
	public EmployeeDTO getSavedEmployee() {
		if (sessionData == null) {
			getConnection();
		}
		EmployeeDTO employee = getEmployeeFromSessionData(); 
		if (employee == null || !Integer.toString(employee.getID()).equals(getDestination())) {
		    employee = getContextEmployee();
		}
		return employee;
	}

	public String readModeVisibility() {
	    if (readOnly) {
	        return SHOW_HTML_ELEMENT;
	    } else {
            return HIDE_HTML_ELEMENT;
	    }
	}
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String editModeVisibility() {
        if (editMode || newMode) {
            return SHOW_HTML_ELEMENT;
        } else {
            return HIDE_HTML_ELEMENT;
        }
    }
    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isNewMode() {
        return newMode;
    }

    public void setNewMode(boolean newMode) {
        this.newMode = newMode;
    }

    public String deleteModeVisibility() {
        if (deleteMode) {
            return SHOW_HTML_ELEMENT;
        } else {
            return HIDE_HTML_ELEMENT;
        }
    }

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    public String getListFormMessage() {
        return listFormMessage;
    }

    public void setListFormMessage(String listFormMessage) {
        this.listFormMessage = listFormMessage;
    }
}
