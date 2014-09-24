package com.tutorial.boreas;

/*
 * to run NorthwindTest.java, take the following steps:
 * run as java application OR debug as java application
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
/** A JDBC example that connects to the MicroSoft Access sample
 * Northwind database, issues a simple SQL query to the
 * employee table, and prints the results.
 */
public class NorthwindTest {
 public static void main(String[] args) {
	 System.out.println("NorthwindTest Started\n" + "==========");
	 String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	 String url = "jdbc:odbc:NorthWindEx"; // System ODBC name = NorthWindEx
	 String username = " "; // No username/password required
	 String password = " "; // for desktop access to MS Access.
	 showEmployeeTable(driver, url, username, password);
	 //showEmployee(driver, args);
 }
 /** Query the employee table and print the first and
 * last names.
 */
 
 public static void showEmployeeTable(String driver,
		 						String url,
		 						String username,
		 						String password) {
   try {
	 // Load database driver if it's not already loaded.
	 Class.forName(driver);
	 
	 EmployeeData empData = new EmployeeData();
	 // Establish network connection to database.
	 Connection connection = empData.getConnection();
//	 DriverManager.getConnection(url, username, password);
//	 System.out.println("Employees\n" + "==========");
	 // Create a statement for executing queries.
//	 Statement statement = connection.createStatement();
//	 String query =
//	 "SELECT Employees.[Last Name] as lastname, Employees.[First Name] as firstname FROM Employees";
	 // Send query to database and store results.
//	 ResultSet resultSet = statement.executeQuery(query);
	 // Print results.
//	 while(resultSet.next()) {
//	 System.out.print(resultSet.getString("firstname") + " ");
//	 System.out.println(resultSet.getString("lastname"));
//	 }
	 List <Employee> res = empData.getEmployees();
	 Iterator it = res.iterator();
	 Employee emp;
	 System.out.println("Employees\n" + "==========");
	 System.out.println(" -----ID----- Company   Last Name   First Name  ");
	 while (it.hasNext()) {
		 emp = (Employee) it.next();
		 System.out.println(emp.getID()+"  "+emp.getCompany()+"  "+emp.getLastName()+"  "+emp.getFirstName()+
				 " "+emp.getStartDate()+", "+emp.getWebPage());
		 
	 }
	 
	 
	 connection.close();
   } catch(ClassNotFoundException cnfe) {
	 System.err.println("Error loading driver: " + cnfe);
   } catch(SQLException sqle) {
	 System.err.println("Error with connection: " + sqle);
   }

 }
 
 public static void showEmployee(String driver, String[] args){
	 // Load database driver if it's not already loaded.
	 try {
		 Class.forName(driver);
		 EmployeeData empData = new EmployeeData();
		 // Establish network connection to database.
		 Connection connection = empData.getConnection();
		 SessionData sessionData = SessionData.getInstance();
		 Employee emp;
		 System.out.println("Employee Details\n" + "==========");
		 System.out.println(" -----ID----- Company   Last Name   First Name  Start Date");
		 for (int i=0;i<args.length;i++) {
			 sessionData.put(SessionData.DESTINATION, args[i]);
			 emp = empData.getContextEmployee();
			 System.out.println(emp.getID()+"  "+emp.getCompany()+"  "+emp.getLastName()+", "+emp.getFirstName()+
					 " : "+emp.getStartDate());
		 }
	 } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
 }

}
