package com.tutorial.boreas;


import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.*;

@ManagedBean(name = "languageBean")
@SessionScoped
public class PrimeLanguageBean {
  // Bean defaults to RequestScoped.
  // BIG NOTE - changing pages works to carry data forward ONLY when the page FORWARDS
  //          - the requestScoped bean lasts to allow the forwarded page to run the getters  
  //          IF the page is REDIRECTED, the new page re-runs the getters, but a new bean is created
  //          a ViewScoped bean also fails for the same reason - the view goes out of context.  
    
    
    
    
  // 100 most popular programming languages, according to 
  // http://www.tiobe.com/index.php/content/paperinfo/tpci/index.html
  // The first half are in order of popularity, the second half
  // are in alphabetical order.
  private static final String languageString = 
    "Java,C,C++,PHP,C#,Python,Visual Basic,Objective-C,Perl,Ruby,JavaScript,Delphi," +
    "Lisp,SQL,Pascal,Ada,NXT-G,SAS,RPG,Lua,ABAP,Object Pascal,Go,Scheme,Fortran," +
    "Tc,D,COBOL,Logo,CL,APL,JavaFX Script,R,JScript.NET,C shell,ActionScript,Scratch," +
    "IDL,Haskell,Alice,Prolog,Erlang,Smalltalk,Forth,Awk,ML,Scala,ABC,Algol,Applescript," +
    "Bash,bc,Beta,CFML,cg,Clean,Clipper,Cobra,cT,Curl,Dylan,Eiffel,Euphoria,F#,Factor," +
    "Groovy,Icon,Io,J,LabVIEW,LabWindows/CVI,MAD,MAX/MSP,Modula-2,Modula-3,MUMPS,Natural," +
    "Oberon,Objective Caml,Occam,Oz,PL/I,Postscript,PowerShell,Q,REALbasic,S,SIGNAL,SPSS," +
    "Squirrel,Verilog,VHDL,XBase,XSLT,Z shell";
  private static final String[] languageArray = languageString.split(",");
  
  private String language;
  private List<String> languages;

  private static String PRIME_FACES_TRUE = "?faces-redirect=true";
  
  public String getLanguage() {
    return(language);
  }

  public void setLanguage(String language) {
    this.language = language;
  }
  
  public List<String> getLanguages() {
    return(languages);
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public List<String> completeLanguage(String languagePrefix) {
    List<String> matches = new ArrayList<String>();
    for(String possibleLanguage: languageArray) {
      if(possibleLanguage.toUpperCase().startsWith(languagePrefix.toUpperCase())) {
        matches.add(possibleLanguage);
      }
    }
    return(matches);
  }
  
  public String register() {
    return("show-language" + PRIME_FACES_TRUE);
  }
  
  public String register2() {
    return("show-languages"); // + PRIME_FACES_TRUE);
  }
  
  public void selectListener(SelectEvent event) {  
    String itemSelected = event.getObject().toString();
    String message =
      String.format("Added '%s' to selections", itemSelected);
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, new FacesMessage(message));
  }  
  
  public void unselectListener(UnselectEvent event) {  
    String itemSelected = event.getObject().toString();
    String message =
      String.format("Removed '%s' from selections", itemSelected);
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, new FacesMessage(message));
  }  
  public PrimeLanguageBean() {
      // TODO Auto-generated constructor stub
  }

}
