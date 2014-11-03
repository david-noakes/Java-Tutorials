package com.tutorial.boreas;


import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.*;

// Code adapted from that shown in the PrimeFaces showcase.

@SessionScoped
@ManagedBean
public class DashboardBean implements Serializable {  
    private DashboardModel model;  
      
    public DashboardBean() {  
      model = new DefaultDashboardModel();  
      DashboardColumn column1 = new DefaultDashboardColumn();  
      DashboardColumn column2 = new DefaultDashboardColumn();  
      DashboardColumn column3 = new DefaultDashboardColumn();  
      column1.addWidget("sports");   // Matches id attribute, not header attribute
      column1.addWidget("finance");   
      column2.addWidget("lifestyle");
      column2.addWidget("weather");    
      column3.addWidget("politics");  
      model.addColumn(column1);  
      model.addColumn(column2);  
      model.addColumn(column3);  
    }  
    
    public DashboardModel getModel() {  
      return(model);  
    } 
      
    public void handleReorder(DashboardReorderEvent event) {  
      String messageTemplate =
        "Moved '%s' from column %s to column %s, row %s.";
      String widgetId = event.getWidgetId();
      Integer oldCol = event.getSenderColumnIndex();
      Integer newCol = event.getColumnIndex();
      if (oldCol == null) {
        oldCol = newCol;
      }
      Integer newRow = event.getItemIndex();
      String message =
        String.format(messageTemplate, widgetId, oldCol, newCol, newRow);
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage(message)); 
    }  

}
