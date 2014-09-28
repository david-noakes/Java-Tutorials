package com.tutorial.boreas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class EmployeeDTO {
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
    private Date   StartDate;
    
    
    // Attribute names
    private static final String ID_name = "ID";
    private static final String Company_name = "Company";
    private static final String LastName_name = "LastName";
    private static final String FirstName_name = "FirstName";
    private static final String EmailAddress_name = "EmailAddress";
    private static final String JobTitle_name = "JobTitle";
    private static final String BusinessPhone_name = "BusinessPhone";
    private static final String HomePhone_name = "HomePhone";
    private static final String MobilePhone_name = "MobilePhone";
    private static final String FaxNumber_name = "FaxNumber";
    private static final String Address_name = "Address";
    private static final String City_name = "City";
    private static final String StateProvince_name = "StateProvince";
    private static final String PostalCode_name = "PostalCode";
    private static final String CountryRegion_name = "CountryRegion";
    private static final String WebPage_name = "WebPage";
    private static final String Notes_name = "Notes";
    private static final String Attachments_name = "Attachments";
    private static final String StartDate_name   = "StartDate"; 
    
    private static final String SET_PREFIX = "set";
    
    // Database field names
    
    
    // mappings
    
    private static final HashMap fieldList = new HashMap() {{
	    put(ID_name, "ID");
	    put(Company_name, "Company");
	    put(LastName_name,"Last Name");
	    put(FirstName_name,"First Name");
	    put(EmailAddress_name,"E-mail Address");
	    put(JobTitle_name, "Job Title");
	    put(BusinessPhone_name,"Business Phone");
	    put(HomePhone_name,"Home Phone");
	    put(MobilePhone_name,"Mobile Phone");
	    put(FaxNumber_name,"Fax Number");
	    put(Address_name,"Address");
	    put(City_name,"City");
	    put(StateProvince_name, "State/Province");
	    put(PostalCode_name, "ZIP/Postal Code");
	    put(CountryRegion_name, "Country/Region");
	    put(WebPage_name, "Web Page");
	    put(Notes_name, "Notes");
	    put(Attachments_name, "Attachments");
	    put(StartDate_name, "Start Date");
    }};
    
    private static HashMap fieldSetters = new HashMap();

    //Constructors
    public EmployeeDTO() {
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
	    if (fieldSetters.size() == 0) {
	    	loadSetters();
	    }
	}

    /* This is hand coded, but about the same effort as the one using reflection
     * 
     * 
     */
    public void morphFromDB(ResultSet result) {
    	String dbFieldName;
		try {
			dbFieldName  = (String)fieldList.get(ID_name);
		    setID(result.getInt(dbFieldName));
		    dbFieldName  = (String)fieldList.get(Company_name);    
		    setCompany(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(LastName_name);    
		    setLastName(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(FirstName_name);
		    setFirstName(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(EmailAddress_name);
		    setEmailAddress(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(JobTitle_name);
		    setJobTitle(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(BusinessPhone_name);
		    setBusinessPhone(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(HomePhone_name);
		    setHomePhone(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(MobilePhone_name);
		    setMobilePhone(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(FaxNumber_name);
		    setFaxNumber(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(Address_name);
		    setAddress(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(City_name);
		    setCity(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(StateProvince_name);
		    setStateProvince(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(PostalCode_name);
		    setPostalCode(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(CountryRegion_name);
		    setCountryRegion(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(WebPage_name);
    		// for some reason, the field repeats with #
    		String splits[] = result.getString(dbFieldName).split("#");
    		if (splits.length>1) {
    			if (splits[0].length() != 0) {
    				setWebPage(splits[0]);
    			} else {
    				setWebPage(splits[1]);
    			}
    		} else {
    			setWebPage(splits[0]);
    		}
		    dbFieldName  = (String)fieldList.get(Notes_name);
		    setNotes(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(Attachments_name);
		    setAttachments(result.getString(dbFieldName));
		    dbFieldName  = (String)fieldList.get(StartDate_name);
		    setStartDate(result.getDate(dbFieldName));
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
    
    public void morphUsingReflection(ResultSet result) {
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
					
				    setter = (Method) fieldSetters.get(key);	
				    if (this.getClass().getDeclaredField(key).getType().isPrimitive()) {
				    	if (this.getClass().getDeclaredField(key).getType().equals(Integer.TYPE)) {
				    		int i = result.getInt(keyIdx);
				    		setter.invoke(this, i);
				    	}
				    	// TODO - add any other primitive types here
				    } else {
				    	if (this.getClass().getDeclaredField(key).getType().equals(String.class)) {
				    	fieldValue = result.getString(keyIdx);
				    	if (key.equals(WebPage_name)) {
				    		// for some reason, the field repeats with #
				    		String splits[] = fieldValue.split("#");
				    		if (splits.length>1) {
				    			if (splits[0].length() != 0) {
				    				fieldValue = splits[0];
				    			} else {
				    				fieldValue = splits[1];
				    			}
				    		} else {
				    			setWebPage(splits[0]);
				    		}
				    	}
				        setter.invoke(this, fieldValue);
				    	} else if (this.getClass().getDeclaredField(key).getType().equals(Date.class)) {
					        setter.invoke(this, result.getDate(keyIdx));
					    }
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
    
    
    // this is used to help with morphUsingReflection()
    private void loadSetters() {
	    Class noparams[] = {};
		Class[] paramString = new Class[1];	
		paramString[0] = String.class;
		Class[] paramDate = new Class[1];
		paramDate[0] = Date.class;
		Class[] paramInt = new Class[1];	
		paramInt[0] = Integer.TYPE;
		Method method;
    	if (fieldSetters.size() == 0) {
    		// TODO - we have the list in a map, try using a loop (from getMethods()
    		//      - note the need to pass the correct paramtype
    		try {
    			Class<EmployeeDTO> cls = (Class<EmployeeDTO>) Class.forName("com.tutorial.boreas.Employee");
	    		method = cls.getDeclaredMethod(SET_PREFIX+ID_name, paramInt);
	    		fieldSetters.put(ID_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+Company_name, paramString);
	    		fieldSetters.put(Company_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+LastName_name, paramString);
	    		fieldSetters.put(LastName_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+FirstName_name, paramString);
	    		fieldSetters.put(FirstName_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+EmailAddress_name, paramString);
	    		fieldSetters.put(EmailAddress_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+JobTitle_name, paramString);
	    		fieldSetters.put(JobTitle_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+BusinessPhone_name, paramString);
	    		fieldSetters.put(BusinessPhone_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+HomePhone_name, paramString);
	    		fieldSetters.put(HomePhone_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+MobilePhone_name, paramString);
	    		fieldSetters.put(MobilePhone_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+FaxNumber_name, paramString);
	    		fieldSetters.put(FaxNumber_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+Address_name, paramString);
	    		fieldSetters.put(Address_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+City_name, paramString);
	    		fieldSetters.put(City_name,method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+StateProvince_name, paramString);
	    		fieldSetters.put(StateProvince_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+PostalCode_name, paramString);
	    		fieldSetters.put(PostalCode_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+CountryRegion_name, paramString);
	    		fieldSetters.put(CountryRegion_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+WebPage_name, paramString);
	    		fieldSetters.put(WebPage_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+Notes_name, paramString);
	    		fieldSetters.put(Notes_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+Attachments_name, paramString);
	    		fieldSetters.put(Attachments_name, method);
	    		method = cls.getDeclaredMethod(SET_PREFIX+StartDate_name, paramDate);
	    		fieldSetters.put(StartDate_name, method);
		    } catch  (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    	
		    } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}	
}
