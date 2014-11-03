package com.tutorial.boreas;

import javax.faces.bean.*;

@ApplicationScoped
@ManagedBean
public class NewsBean {
    private String template = 
        "%s made %s announcement today! World leaders responded with %s!";
    private String[] leaders =
        { "President Obama", "Prime Minister Putin", "Angela Merkel", "President Xi Jinping" };
    private String[] adjectives = 
        { "a surprising", "an outrageous", "an expected", "an unexpected" };
    private String[] responses =
        { "anger", "caution", "outrage", "relief", "praise" };

    public String getHeadline() {
        String headline =
          String.format(template, 
                        RandomUtils.randomElement(leaders),
                        RandomUtils.randomElement(adjectives),
                        RandomUtils.randomElement(responses));
        return(headline);
    }
      
    public String getFontSize() {
        int pixels = 20 + (int)(Math.random() * 60);
        return(String.format("%spx", pixels));
    }

    public NewsBean() {
        // TODO Auto-generated constructor stub
    }

}
