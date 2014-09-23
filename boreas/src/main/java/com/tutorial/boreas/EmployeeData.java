package com.tutorial.boreas;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
    public List<Employee> getEmployees() {
    	if (sessionData == null) {
    		getConnection();
    	}
        ResultSet results = null;
        PreparedStatement pst = null;
        List<Employee> records = new ArrayList<Employee>();
        String sql = "select * from employees";
        //this fails on record names with spaces
        
        try {   
            pst = con.prepareStatement(sql);
            pst.execute();
            results = pst.getResultSet();

            while(results.next()){
               Employee employee = new Employee();
               //employee.morphFromDB(results);
               employee.morphUsingReflection(results);
//               employee.setID(results.getInt(1));
//               employee.setCompany(results.getString(2));
//               employee.setLastName(results.getString(3));
//               employee.setFirstName(results.getString(4));
               records.add(employee);				
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
        
        
        return records;
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
}
