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
    ResultSet results = null;
    
    private static final String EMPLOYEE_DTO = "employeeDTO";
	
    public List<EmployeeDTO> getEmployees() {
    	if (sessionData == null) {
    		getConnection();
    	}
        ResultSet results = null;
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
    	return employee;
    }
    
    /**
     * use the employee stored in sessionData
     * @return saved employeeDTO
     */
	public EmployeeDTO getEmployeeFromSessionData() {
		EmployeeDTO employee = getSavedEmployee();
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
		EmployeeDTO employee = getContextEmployee();
		return employee;
	}
}
