package com.tutorialspoint.test;

import java.net.URI;
import java.net.URISyntaxException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.tutorialspoint.test.UrlConverter")
public class UrlConverter
  implements Converter
{
  public Object getAsObject(FacesContext facesContext, UIComponent component, String value)
  {
    StringBuilder url = new StringBuilder();
    if (!value.startsWith("http://", 0)) {
      url.append("http://");
    }
    url.append(value);
    try
    {
      new URI(url.toString());
    }
    catch (URISyntaxException e)
    {
      FacesMessage msg = new FacesMessage("Error converting URL", 
        "Invalid URL format");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ConverterException(msg);
    }
    UrlData urlData = new UrlData(url.toString());
    return urlData;
  }
  
  public String getAsString(FacesContext facesContext, UIComponent component, Object value)
  {
    return value.toString();
  }
}
