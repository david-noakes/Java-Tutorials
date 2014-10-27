package com.tutorial.boreas;

/*
 * to run NorthwindTest.java, take the following steps:
 * run as java application OR debug as java application
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.fugue.utilities.CipherUtility;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/** A JDBC example that connects to the MicroSoft Access sample
 * Northwind database, issues a simple SQL query to the
 * employee table, and prints the results.
 */
public class NorthwindTest {
 public static void main(String[] args) throws KeyException, Base64DecodingException, GeneralSecurityException, IOException {
	 System.out.println("NorthwindTest Started\n" + "==========");
	 String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	 String url = "jdbc:odbc:NorthWindEx"; // System ODBC name = NorthWindEx
	 String username = " "; // No username/password required
	 String password = " "; // for desktop access to MS Access.
	 //showEmployeeTable(driver, url, username, password);
	 showEmployee(driver, args);
	 byte[] keyBytes =  new byte [] {78, (byte) 166, 42, 70, (byte) 251, 20, (byte) 142, 103, (byte) 157, (byte) 231, 76, 95, (byte) 171, 94, 57, 54};
	        // {78, -90, 42, 70, -5, 20, -114, 103, -99, -25, 76, 95, -85, 94, 57, 54};
	 String rubbish = new String(keyBytes, "UTF-8");
	 UserData ud = new UserData();
     CipherUtility cyx = new CipherUtility();
	 String z;
	 String s;
	 z = cyx.encrypt("password", rubbish);
	 s = cyx.decrypt(z, rubbish);
	 System.out.println("in: "+s+", enctypt: "+z);
     z = cyx.encrypt("secret", rubbish);
     s = cyx.decrypt(z, rubbish);
     System.out.println("in: "+s+", enctypt: "+z);
     z = cyx.encrypt("pwd", rubbish);
     s = cyx.decrypt(z, rubbish);
     System.out.println("in: "+s+", enctypt: "+z);
     z = cyx.encrypt("it's a secret", rubbish);
     s = cyx.decrypt(z, rubbish);
     System.out.println("in: "+s+", enctypt: "+z);
     z = cyx.encrypt("qx9", rubbish);
     s = cyx.decrypt(z, rubbish);
     System.out.println("in: "+s+", enctypt: "+z);
     z = cyx.encrypt("q", rubbish);
     s = cyx.decrypt(z, rubbish);
     System.out.println("in: "+s+", enctypt: "+z);
	 System.out.println("david + q "+ud.validUidPwd("david", "q"));
     System.out.println("Danny + qx9 "+ud.validUidPwd("Danny", "qx9"));
     System.out.println("HelluA + it's a secret "+ud.validUidPwd("HelluA", "it's a secret"));
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
	 List <EmployeeDTO> res = empData.getEmployees();
	 Iterator it = res.iterator();
	 EmployeeDTO emp;
	 System.out.println("Employees\n" + "==========");
	 System.out.println(" -----ID----- Company   Last Name   First Name  ");
	 while (it.hasNext()) {
		 emp = (EmployeeDTO) it.next();
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
		 EmployeeDTO emp;
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
