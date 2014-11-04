package com.tutorial.boreas;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class GrowlBean {
    public String makeMessages() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message1 = 
            new FacesMessage("Something happened");
        // Default severity is INFO
        context.addMessage(null, message1);
        FacesMessage message2 = 
            new FacesMessage("You were naughty");
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage(null, message2);
        return(null);
    }

    public GrowlBean() {
        // TODO Auto-generated constructor stub
    }

}
