package com.tutorialspoint.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="userData", eager=true)
@SessionScoped
public class UserData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public Date date;
  public UrlData data;
  private String name;
  private String dept;
  private int age;
  private double salary;
  
  public Date getDate()
  {
    return this.date;
  }
  
  public void setDate(Date date)
  {
    this.date = date;
  }
  
  public UrlData getData()
  {
    return this.data;
  }
  
  public void setData(UrlData data)
  {
    this.data = data;
  }
  
  private static final ArrayList<Employee> employees = new ArrayList(Arrays.asList(new Employee[] {
    new Employee("John", "Marketing", 30, 2000.0D), 
    new Employee("Robert", "Marketing", 35, 3000.0D), 
    new Employee("Mark", "Sales", 25, 2500.0D), 
    new Employee("Chris", "Marketing", 33, 2500.0D), 
    new Employee("Peter", "Customer Care", 20, 1500.0D) }));
  
  public ArrayList<Employee> getEmployees()
  {
    return employees;
  }
  
  public String addEmployee()
  {
    Employee employee = new Employee(this.name, this.dept, this.age, this.salary);
    employees.add(employee);
    return null;
  }
  
  public String deleteEmployee(Employee employee)
  {
    employees.remove(employee);
    return null;
  }
  
  public String editEmployee(Employee employee)
  {
    employee.setCanEdit(true);
    return null;
  }
  
  public String saveEmployees()
  {
    for (Employee employee : employees) {
      employee.setCanEdit(false);
    }
    return null;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getDepartment()
  {
    return this.dept;
  }
  
  public void setDepartment(String department)
  {
    this.dept = department;
  }
  
  public int getAge()
  {
    return this.age;
  }
  
  public void setAge(int age)
  {
    this.age = age;
  }
  
  public double getSalary()
  {
    return this.salary;
  }
  
  public void setSalary(double salary)
  {
    this.salary = salary;
  }
}
