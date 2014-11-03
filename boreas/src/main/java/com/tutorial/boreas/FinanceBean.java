package com.tutorial.boreas;

import javax.faces.bean.*;

@ApplicationScoped
@ManagedBean
public class FinanceBean {

    public String getCoreservlets() {
        return("956.92 (+43.55%)");
    }
      
    public String getPrime() {
        return("887.48 (+37.78%)");
    }

    public String getGoogle() {
        return("651.22 (+1.01%)");
    }
      
    public String getFacebook() {
        return("22.24 (+1.30%)");
    }
      
    public String getOracle() {
        return("30.05 (+0.75%)");
    }

    public FinanceBean() {
        // TODO Auto-generated constructor stub
    }

}
