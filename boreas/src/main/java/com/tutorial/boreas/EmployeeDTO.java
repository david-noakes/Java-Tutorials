package com.tutorial.boreas;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class EmployeeDTO extends HashMap{
	// Northwind Employee Table - these fields are needed by JSF to pass updated values to the setters
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
      private String UserId;
      private String Password;
    
    public static String BLANK = " ";
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
    private static final String UserId_name   = "UserId"; 
    private static final String Password_name   = "Password"; 
    
    private static final String SET_PREFIX = "set";
    
    // Database field names
    public static final String db_ID_name = "ID";
    public static final String db_Company_name = "Company";
    public static final String db_LastName_name = "Last Name";
    public static final String db_FirstName_name = "First Name";
    public static final String db_EmailAddress_name = "E-mail Address";
    public static final String db_JobTitle_name = "Job Title";
    public static final String db_BusinessPhone_name = "Business Phone";
    public static final String db_HomePhone_name = "Home Phone";
    public static final String db_MobilePhone_name = "Mobile Phone";
    public static final String db_FaxNumber_name = "Fax Number";
    public static final String db_Address_name = "Address";
    public static final String db_City_name = "City";
    public static final String db_StateProvince_name = "State/Province";
    public static final String db_PostalCode_name = "ZIP/Postal Code";
    public static final String db_CountryRegion_name = "Country/Region";
    public static final String db_WebPage_name = "Web Page";
    public static final String db_Notes_name = "Notes";
    public static final String db_Attachments_name = "Attachments";
    public static final String db_StartDate_name = "Start Date";
    public static final String db_UserId_name   = "UserID"; 
    public static final String db_Password_name   = "Password"; 
    
    
    // mappings - add new fields in here *****
    
    private static final HashMap fieldList = new HashMap() {{
	    put(ID_name, db_ID_name);
	    put(Company_name, db_Company_name);
	    put(LastName_name, db_LastName_name);
	    put(FirstName_name, db_FirstName_name);
	    put(EmailAddress_name, db_EmailAddress_name);
	    put(JobTitle_name, db_JobTitle_name);
	    put(BusinessPhone_name, db_BusinessPhone_name);
	    put(HomePhone_name, db_HomePhone_name);
	    put(MobilePhone_name, db_MobilePhone_name);
	    put(FaxNumber_name, db_FaxNumber_name);
	    put(Address_name, db_Address_name);
	    put(City_name, db_City_name);
	    put(StateProvince_name, db_StateProvince_name);
	    put(PostalCode_name, db_PostalCode_name);
	    put(CountryRegion_name, db_CountryRegion_name);
	    put(WebPage_name, db_WebPage_name);
	    put(Notes_name, db_Notes_name);
	    put(Attachments_name, db_Attachments_name);
	    put(StartDate_name, db_StartDate_name);
        put(UserId_name, db_UserId_name);
        put(Password_name, db_Password_name);
    }};
    
    private static HashMap fieldSetters = new HashMap();

    //Constructors - be sure to use setters
    public EmployeeDTO() {
		super();
		setID(-1);
		setCompany(BLANK);
		setLastName(BLANK);
		setFirstName(BLANK);
		setEmailAddress(BLANK);
		setJobTitle(BLANK);
		setBusinessPhone(BLANK);
		setHomePhone(BLANK);
		setMobilePhone(BLANK);
		setFaxNumber(BLANK);
		setAddress(BLANK);
		setCity(BLANK);
		setStateProvince(BLANK);
		setPostalCode(BLANK);
		setCountryRegion(BLANK);
		setWebPage(BLANK);
		setNotes(BLANK);
		setAttachments(BLANK);
		setStartDate(new Date(0));
		setUserId(BLANK);
		setPassword(BLANK);
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
		    dbFieldName = (String) fieldList.get(UserId_name);
		    setUserId(result.getString(dbFieldName));
		    dbFieldName = (String) fieldList.get(Password_name);
		    setPassword(result.getString(dbFieldName));
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
				    if (this.get(key).getClass().isPrimitive()) {
                        // TODO - add any other primitive types here.
				        // no longer possible - hashmap stores primitives using object wrappers
				    	if (this.get(key).getClass().equals(Integer.TYPE)) {
				    		int i = result.getInt(keyIdx);
				    		setter.invoke(this, i);
				    	}
				    } else {
				    	if (this.get(key).getClass().equals(String.class)) {
    				    	fieldValue = result.getString(keyIdx);
    				    	if (fieldValue == null) {
                                setter.invoke(this, BLANK);
    				    	} else { 
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
    				    	}
    				        setter.invoke(this, fieldValue);
				    	} else if (this.get(key).getClass().equals(Date.class)) {
					        setter.invoke(this, result.getDate(keyIdx));
					    } else if (this.get(key).getClass().equals(Integer.class)) {
	                            int i = result.getInt(keyIdx);
	                            setter.invoke(this, i);
	                        }
	                        // TODO - add any other primitive types here
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
//				} catch (NoSuchFieldException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
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
    
    public String toUpdateSQLString(){
        String updateClause = "UPDATE employees SET";
        String sep = " ";
        String key = "";
        String dbFieldName = "";
        Object obj;
        // create update string
        Iterator pe = (Iterator) fieldList.keySet().iterator();
        int i = 1; // params start at 1
        while (pe.hasNext()) {
            key = (String) pe.next();
            dbFieldName  = (String)fieldList.get(key);
            if (!dbFieldName.equals(db_ID_name) && !dbFieldName.equals(db_Attachments_name)) {
                updateClause = updateClause + sep + "[" + dbFieldName + "] = ";
                obj = this.get(key);
                if (obj == null) {
                    updateClause = updateClause + "' '";  
                  } else if (obj.getClass()==String.class) {
                      updateClause = updateClause + "'" + obj.toString() + "'";
                  } else if (obj.getClass()==java.sql.Date.class) {
                      updateClause = updateClause + "'" + ((java.sql.Date) obj).toString() +"'";
                  } else if (obj.getClass()==Date.class) {
                      updateClause = updateClause + "'" + ((Date) obj).toString() +"'";
                  }
                if (i==1){
                    sep = " , ";
                }
                i++;
            }
        }
        updateClause = updateClause + " WHERE ["+db_ID_name+"]="+getID();
        return updateClause;
       
    }
    public PreparedStatement toUpdateSQLStatement(){
        String updateClause = "UPDATE employees SET";
        String sep = " ";
        SessionData session;
        String key = "";
        String dbFieldName = "";
        PreparedStatement pst = null;
        HashMap parms = new HashMap();
        try {
            
            // create update string
            Iterator pe = (Iterator) fieldList.keySet().iterator();
            int i = 1; // params start at 1
            while (pe.hasNext()) {
                key = (String) pe.next();
                dbFieldName  = (String)fieldList.get(key);
                if (!dbFieldName.equals(db_ID_name) && !dbFieldName.equals(db_Attachments_name)) {
                    updateClause = updateClause + sep + "[" + dbFieldName + "] = ?";
                    parms.put(i, this.get(key));
                    if (i==1){
                        sep = " , ";
                    }
                    i++;
                }
            }
            updateClause = updateClause + " WHERE ["+db_ID_name+"]="+getID();
            session = SessionData.getInstance();
            Connection conn = session.getConnection();
            pst = conn.prepareStatement(updateClause);
            // add parameter values
            Object obj;
            java.sql.Date jDate = new java.sql.Date(0);
            for (int j = 1;j<i;j++){
                obj = parms.get(j);
                if (obj == null) {
                  pst.setNull(j, java.sql.Types.VARCHAR);  
                } else if (obj.getClass()==String.class) {
                    pst.setString(j, (String) obj);
                } else if (obj.getClass()==Date.class) {
                    jDate.setTime(((Date) obj).getTime());
                    pst.setDate(j, jDate);
                } else if (obj.getClass()==java.sql.Date.class) {
                    jDate.setTime(((java.sql.Date) obj).getTime());
                    pst.setDate(j, jDate);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pst;
    }
    
    public PreparedStatement toInsertSQLStatement(){
        String insertClause = "INSERT INTO employees (";
        String valuesClause = ") VALUES (";
        String sep = " ";
        SessionData session;
        String key = "";
        String dbFieldName = "";
        PreparedStatement pst = null;
        HashMap parms = new HashMap();
        try {
            
            // create update string
            Iterator pe = (Iterator) fieldList.keySet().iterator();
            int i = 1; // params start at 1
            while (pe.hasNext()) {
                key = (String) pe.next();
                dbFieldName  = (String)fieldList.get(key);
                if (!dbFieldName.equals(db_ID_name) && !dbFieldName.equals(db_Attachments_name)) {
                    insertClause = insertClause + sep + "[" + dbFieldName + "] ";
                    valuesClause = valuesClause + sep + "? ";
                    parms.put(i, this.get(key));
                    if (i==1){
                        sep = " , ";
                    }
                    i++;
                }
            }
            insertClause = insertClause + valuesClause +" )";
            session = SessionData.getInstance();
            Connection conn = session.getConnection();
            pst = conn.prepareStatement(insertClause);
            // add parameter values
            Object obj;
            java.sql.Date jDate = new java.sql.Date(0);
            for (int j = 1;j<i;j++){
                obj = parms.get(j);
                if (obj == null) {
                  pst.setNull(j, java.sql.Types.VARCHAR);  
                } else if (obj.getClass()==String.class) {
                    pst.setString(j, (String) obj);
                } else if (obj.getClass()==Date.class) {
                    jDate.setTime(((Date) obj).getTime());
                    pst.setDate(j, jDate);
                } else if (obj.getClass()==java.sql.Date.class) {
                    jDate.setTime(((java.sql.Date) obj).getTime());
                    pst.setDate(j, jDate);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pst;
    }
    public PreparedStatement toDeleteSQLStatement(){
        String deleteClause = "DELETE * FROM employees";
        String sep = " ";
        SessionData session;
        String key = "";
        String dbFieldName = "";
        PreparedStatement pst = null;
        deleteClause = deleteClause + " WHERE ["+db_ID_name+"]="+getID();
        try {
            session = SessionData.getInstance();
            Connection conn = session.getConnection();
            pst = conn.prepareStatement(deleteClause);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pst;
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
    			Class<EmployeeDTO> cls = (Class<EmployeeDTO>) Class.forName("com.tutorial.boreas.EmployeeDTO");
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
                method = cls.getDeclaredMethod(SET_PREFIX+UserId_name, paramString);
                fieldSetters.put(UserId_name, method);
                method = cls.getDeclaredMethod(SET_PREFIX+Password_name, paramString);
                fieldSetters.put(Password_name, method);
		    } catch  (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    	
		    } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
    
    @Override
    public String toString() {
        Iterator pe = (Iterator) fieldList.keySet().iterator();
        String dbFieldName;
        String sep = "";
        String key;
        String toString = "EmployeeDTO [";
        while (pe.hasNext()) {
            key = (String) pe.next();
            dbFieldName  = (String)fieldList.get(key);
            toString = toString + sep + dbFieldName + " = " + this.get(key);
            sep = " , ";
        }
        toString = toString + "]";

//        return "EmployeeDTO [ID=" + getID() + ", Company=" + getCompany() 
//                + ", LastName=" + getLastName() + ", FirstName=" 
//                + getFirstName() + ", EmailAddress=" + getEmailAddress() 
//                + ", JobTitle=" + getJobTitle() + ", BusinessPhone=" + getBusinessPhone() 
//                + ", HomePhone=" + getHomePhone() + ", MobilePhone=" + getMobilePhone() 
//                + ", FaxNumber=" + getFaxNumber() + ", Address=" + getAddress() 
//                + ", City=" + getCity() + ", StateProvince=" + getStateProvince()
//                + ", PostalCode=" + getPostalCode() + ", CountryRegion=" + getCountryRegion() 
//                + ", WebPage=" + getWebPage() + ", Notes=" + getNotes() 
//                + ", Attachments=" + getAttachments() + ", StartDate=" + getStartDate() + "]";
        return toString;
    }

    // Setters and Getters
    // note - although the public type is int, the hashmap stores it as Integer
	public int getID() {
		return  (Integer) this.get(ID_name);
	}
	public void setID(int iD) {
		this.put(ID_name, iD);
	}
	public String getCompany() {
		return (String) this.get(Company_name);
	}
	public void setCompany(String company) {
		if (company != null) {
		    this.put(Company_name,company);
		} else {
            this.put(Company_name,BLANK);
		}
	}
	public String getLastName() {
		return (String) this.get(LastName_name);
	}
	public void setLastName(String lastName) {
		if (lastName != null) {
			this.put(LastName_name, lastName);
		} else {
            this.put(LastName_name, BLANK);
		}
	}
	public String getFirstName() {
		return (String) this.get(FirstName_name);
	}
	public void setFirstName(String firstName) {
		if (firstName != null) {
			this.put(FirstName_name, firstName);
		} else {
		    this.put(FirstName_name, BLANK);
		}
	}
	public String getEmailAddress() {
		return (String) this.get(EmailAddress_name);
	}
	public void setEmailAddress(String emailAddress) {
		if (emailAddress != null) {
		    this.put(EmailAddress_name, emailAddress);
		} else {
		    this.put(EmailAddress_name, BLANK);
		}
	}
	public String getJobTitle() {
		return (String) this.get(JobTitle_name);
	}
	public void setJobTitle(String jobTitle) {
		if (jobTitle != null) {
		    this.put(JobTitle_name, jobTitle);
		} else {
		    this.put(JobTitle_name, BLANK);
		}
	}
	public String getBusinessPhone() {
		return (String) this.get(BusinessPhone_name);
	}
	public void setBusinessPhone(String businessPhone) {
		if (businessPhone != null) {
		    this.put(BusinessPhone_name, businessPhone);
		} else {
		    this.put(BusinessPhone_name, BLANK);
		}
	}
	public String getHomePhone() {
		return (String) this.get(HomePhone_name);
	}
	public void setHomePhone(String homePhone) {
		if (homePhone != null) {
		    this.put(HomePhone_name, homePhone);
		} else {
		    this.put(HomePhone_name, BLANK);
		}
	}
	public String getMobilePhone() {
		return (String) this.get(MobilePhone_name);
	}
	public void setMobilePhone(String mobilePhone) {
		if (mobilePhone != null) {
		    this.put(MobilePhone_name, mobilePhone);
		} else {
		    this.put(MobilePhone_name, BLANK);
		}
	}
	public String getFaxNumber() {
		return (String) this.get(FaxNumber_name);
	}
	public void setFaxNumber(String faxNumber) {
		if (faxNumber != null) {
			this.put(FaxNumber_name, faxNumber);
		} else {
		    this.put(FaxNumber_name, BLANK);
		}
	}
	public String getAddress() {
		return (String) this.get(Address_name);
	}
	public void setAddress(String address) {
		if (address != null) {
		    this.put(Address_name, address);
		} else {
		    this.put(Address_name, BLANK);
		}
	}
	public String getCity() {
		return (String) this.get(City_name);
	}
	public void setCity(String city) {
		if (city != null ) {
		    this.put(City_name, city);
		} else {
		    this.put(City_name, BLANK);
		}
	}
	public String getStateProvince() {
		return (String) this.get(StateProvince_name);
	}
	public void setStateProvince(String stateProvince) {
		if (stateProvince != null){
		    this.put(StateProvince_name, stateProvince);
		} else {
		    this.put(StateProvince_name, BLANK);
		}
	}
	public String getPostalCode() {
		return (String) this.get(PostalCode_name);
	}
	public void setPostalCode(String postalCode) {
		if (postalCode != null) {
		    this.put(PostalCode_name, postalCode);
		} else {
		    this.put(PostalCode_name, BLANK);
		}
	}
	public String getCountryRegion() {
		return (String) this.get(CountryRegion_name);
	}
	public void setCountryRegion(String countryRegion) {
		if (countryRegion != null) {
		    this.put(CountryRegion_name, countryRegion);
		} else {
		    this.put(CountryRegion_name, BLANK);
		}
	}
	public String getWebPage() {
		return (String) this.get(WebPage_name);
	}
	public void setWebPage(String webPage) {
		if (webPage != null) {
		    this.put(WebPage_name, webPage);
		} else {
		    this.put(WebPage_name, BLANK);
		}
	}
	public String getNotes() {
		return (String) this.get(Notes_name);
	}
	public void setNotes(String notes) {
		if (notes != null) {
		    this.Notes = notes;
		} else {
		    this.Notes = BLANK;
		}
        this.put(Notes_name, this.Notes);
	}
	public String getAttachments() {
		return (String) this.get(Attachments_name);
	}
	public void setAttachments(String attachments) {
		if (attachments != null) {
		    this.put(Attachments_name, attachments);
		} else {
		    this.put(Attachments_name, BLANK);
		}
	}

	public Date getStartDate() {
	    Object obj = this.get(StartDate_name);
	    if (obj.getClass()==Date.class) {
	        return (Date) obj;
	    }
        if (obj.getClass()==java.sql.Date.class) {
            return new Date(((java.sql.Date) obj).getTime());
        }
        if (obj.getClass()==String.class) {
            String sDate = (String) obj;
            SimpleDateFormat dt1 = null;
            if (sDate.indexOf("-")==2) {
                dt1 = new SimpleDateFormat("dd-MM-yyyy");
            } else {
                dt1 = new SimpleDateFormat("yyyy-MM-dd");
            }
            Date date = null;
            try {
                date = dt1.parse((String) obj);
            } catch (Exception e) {
                date = new Date(0);
            }
            setStartDate(date);
            return date;
        }
	    return new Date(0);
	}

	public void setStartDate(Date startDate) {
	    if (startDate != null) {
	        this.put(StartDate_name, startDate);
	    } else {
	        this.put(StartDate_name, new Date(0));
	    }
	    this.StartDate = getStartDate();
	}	
    public void setStartDate(java.sql.Date startDate) {
        if (startDate != null) {
            this.StartDate = new Date(startDate.getTime());
        } else {
            this.StartDate = new Date(0);
        }
        this.put(StartDate_name, this.StartDate);
    }

    public String getUserId() {
        return (String) this.get(UserId_name);
    }

    public void setUserId(String userId) {
        UserId = userId;
        this.put(UserId_name, userId);
    }

    public String getPassword() {
        return (String) this.get(Password_name);
    }

    public void setPassword(String password) {
        Password = password;
        this.put(Password_name, password);
    }   
}
