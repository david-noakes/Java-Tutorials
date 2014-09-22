package com.tutorial.boreas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Employee {
	// Northwind Employee Table
	private int ID;
	private String Company;
	private String LastName;
	private String FirstName;
    private String EmailAddress;
    private String JobTitle;
    private String BusinessPhone;
    private String HomePhone;
    private String MobilePhone;
    private String FaxNumber;
    private String Address;
    private String City;
    private String StateProvince;
    private String PostalCode;
    private String CountryRegion;
    private String WebPage;
    private String Notes;
    private String Attachments;
    
    private static final HashMap fieldList = new HashMap() {{
	    put("ID", "ID");
	    put("Company", "Company");
	    put("LastName","Last Name");
	    put("FirstName","First Name");
	    put("EmailAddress","E-mail Address");
	    put("JobTitle", "Job Title");
	    put("BusinessPhone","Business Phone");
	    put("HomePhone","Home Phone");
	    put("MobilePhone","Mobile Phone");
	    put("FaxNumber","Fax Number");
	    put("Address","Address");
	    put("City","City");
	    put("StateProvince", "State/Province");
	    put("PostalCode", "ZIP/Postal Code");
	    put("CountryRegion", "Country/Region");
	    put("WebPage", "Web Page");
	    put("Notes", "Notes");
	    put("Attachments", "Attachments");
    }};
    
	//Constructors
    public Employee() {
		super();
		ID = 0;
		Company = "";
		LastName = "";
		FirstName = "";
	    EmailAddress = "";
	    JobTitle = "";
	    BusinessPhone = "";
	    HomePhone = "";
	    MobilePhone = "";
	    FaxNumber = "";
	    Address = "";
	    City = "";
	    StateProvince = "";
	    PostalCode = "";
	    CountryRegion = "";
	    WebPage = "";
	    Notes = "";
	    Attachments = "";
	    
	}

    
    public void Morph(ResultSet result) {
    	int keyIdx;
    	String dbFieldName;
    	String fieldValue;
    	String key;
    	Method setter;
	    if (result != null ) {
			Iterator iter = (Iterator) fieldList.keySet().iterator();
			while (iter.hasNext() ) {
				try {
					key = (String) iter.next();
					dbFieldName  = (String)fieldList.get(key);
					keyIdx = result.findColumn(dbFieldName);
					
				    //setter = this.getClass().getDeclaredMethod("set"+key);
					if (key.contentEquals("ID")) {
					    setID(result.getInt(keyIdx));
					} else if (key.contentEquals("Company")) {    
					    setCompany(result.getString(keyIdx));
					} else if (key.contentEquals("LastName")) {    
					    setLastName(result.getString(keyIdx));
					} else if (key.contentEquals("FirstName")) {
					    setFirstName(result.getString(keyIdx));
					} else if (key.contentEquals("EmailAddress")) {
					    setEmailAddress(result.getString(keyIdx));
					} else if (key.contentEquals("JobTitle")) {
					    setJobTitle(result.getString(keyIdx));
					} else if (key.contentEquals("BusinessPhone")) {
					    setBusinessPhone(result.getString(keyIdx));
					} else if (key.contentEquals("HomePhone")) {
					    setHomePhone(result.getString(keyIdx));
					} else if (key.contentEquals("MobilePhone")) {
					    setMobilePhone(result.getString(keyIdx));
					} else if (key.contentEquals("FaxNumber")) {
					    setFaxNumber(result.getString(keyIdx));
					} else if (key.contentEquals("Address")) {
					    setAddress(result.getString(keyIdx));
					} else if (key.contentEquals("City")) {
					    setCity(result.getString(keyIdx));
					} else if (key.contentEquals("StateProvince")) {
					    setStateProvince(result.getString(keyIdx));
					} else if (key.contentEquals("PostalCode")) {
					    setPostalCode(result.getString(keyIdx));
					} else if (key.contentEquals("CountryRegion")) {
					    setCountryRegion(result.getString(keyIdx));
					} else if (key.contentEquals("WebPage")) {
					    setWebPage(result.getString(keyIdx));
					} else if (key.contentEquals("Notes")) {
					    setNotes(result.getString(keyIdx));
					} else if (key.contentEquals("Attachments")) {
					    setAttachments(result.getString(keyIdx));
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }	
    }
    
    public void MorphFailsWithNoSuchMethod(ResultSet result) {
    	// getting a setter method fails
    	int keyIdx;
    	String dbFieldName;
    	String fieldValue;
    	String key;
    	Method setter;
	    if (result != null ) {
			Iterator iter = (Iterator) fieldList.keySet().iterator();
			while (iter.hasNext() ) {
				try {
					key = (String) iter.next();
					dbFieldName  = (String)fieldList.get(key);
					keyIdx = result.findColumn(dbFieldName);
					
				    setter = this.getClass().getDeclaredMethod("set"+key);	
				    if (getClass().getField(key).getType().isPrimitive()) {
				    	int i = result.getInt(keyIdx);
				    	setter.invoke(this, i);
				    } else {
				    	fieldValue = result.getString(keyIdx);
				        setter.invoke(this, fieldValue);
				    }
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
    }
    
    
    // Setters and Getters
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		if (company != null) {
			Company = company;
		} else {
			Company = "";
		}
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		if (lastName != null) {
			LastName = lastName;
		} else {
			LastName = "";
		}
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		if (firstName != null) {
			FirstName = firstName;
		} else {
			FirstName = "";
		}
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		if (emailAddress != null) {
			EmailAddress = emailAddress;
		} else {
			EmailAddress = "";
		}
	}
	public String getJobTitle() {
		return JobTitle;
	}
	public void setJobTitle(String jobTitle) {
		if (jobTitle != null) {
			JobTitle = jobTitle;
		} else {
			JobTitle = "";
		}
	}
	public String getBusinessPhone() {
		return BusinessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		if (businessPhone != null) {
			BusinessPhone = businessPhone;
		} else {
			BusinessPhone = "";
		}
	}
	public String getHomePhone() {
		return HomePhone;
	}
	public void setHomePhone(String homePhone) {
		if (homePhone != null) {
			HomePhone = homePhone;
		} else {
			HomePhone = "";
		}
	}
	public String getMobilePhone() {
		return MobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		if (mobilePhone != null) {
			MobilePhone = mobilePhone;
		} else {
			MobilePhone = "";
		}
	}
	public String getFaxNumber() {
		return FaxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		if (faxNumber != null) {
			FaxNumber = faxNumber;
		} else {
			FaxNumber = "";
		}
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		if (address != null) {
			Address = address;
		} else {
			Address = "";
		}
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		if (city != null ) {
			City = city;
		} else {
			City = "";
		}
	}
	public String getStateProvince() {
		return StateProvince;
	}
	public void setStateProvince(String stateProvince) {
		if (stateProvince != null){
			StateProvince = stateProvince;
		} else {
			StateProvince = "";
		}
	}
	public String getPostalCode() {
		return PostalCode;
	}
	public void setPostalCode(String postalCode) {
		if (postalCode != null) {
			PostalCode = postalCode;
		} else {
			PostalCode = "";
		}
	}
	public String getCountryRegion() {
		return CountryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		if (countryRegion != null) {
			CountryRegion = countryRegion;
		} else {
			CountryRegion = "";
		}
	}
	public String getWebPage() {
		return WebPage;
	}
	public void setWebPage(String webPage) {
		if (webPage != null) {
			WebPage = webPage;
		} else {
			WebPage = "";
		}
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		if (notes != null) {
			Notes = notes;
		} else {
			Notes = "";
		}
	}
	public String getAttachments() {
		return Attachments;
	}
	public void setAttachments(String attachments) {
		if (attachments != null) {
			Attachments = attachments;
		} else {
			Attachments = "";
		}
	}	
}
