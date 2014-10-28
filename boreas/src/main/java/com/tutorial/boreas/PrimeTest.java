package com.tutorial.boreas;

import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean (name = "testBean", eager = true)
public class PrimeTest {

    public PrimeTest() {
        // TODO Auto-generated constructor stub
    }
    private double number;
    private Date date;
    private int f=32;

    public int getF() {
        return f;
    }
    public int getC() {
        return ((int)((f - 32)*(5.0/9.0)));
    }
    public void setF(int f) {
        this.f = f;
    }
    public double getNumber() {
        return (number);
    }
    public void setNumber(double number) {
        this.number = number;
    }
    public Date getDate() {
        return(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String doAction() {
        return("show-test-data");
    }
    public String getStatus() {
        return(String.format("%s&deg;F = %s&deg;C",  f, getC()));
     }
        
}
