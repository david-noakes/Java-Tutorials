package com.tutorial.boreas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "SessionData", eager = true)
@SessionScoped
public class SessionData extends HashMap {
	
	
	public static final String CONTEXT = "context";
	public static final String DESTINATION = "destination";
	
	private String username = " ";
	private String password = " ";
	private static SessionData instance = null;
	private Connection connection;
	
	
    private static String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String url = "jdbc:odbc:NorthWindEx"; // System ODBC name = NorthWindEx

	public String getUserName() {
		return username;
	}
	public void setUserName(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private SessionData(String uName, String pWord) throws SQLException {
		this.username = uName;
		this.password = pWord;
		connection = DriverManager.getConnection(url, username, password);
	}
	
//	public static SessionData getInstance(String uName, String pWord) throws SQLException {
//		if (instance == null) {
//		    instance = new SessionData(uName, pWord);	
//		}
//		
//		return instance;
//	}
	public static SessionData getInstance() throws SQLException {
		
		if (instance == null) {
		    instance = new SessionData(" ", " ");	
		}
		
		return instance;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	// NOTE - do not put getters in this bean for JSF pages wanting session data
	// JSF is unable to create a second copy of the bean
	// instead put the getters into the bean for the page, and let it get it from sessionData
	
	
}
