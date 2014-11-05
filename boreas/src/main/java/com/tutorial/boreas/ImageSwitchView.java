package com.tutorial.boreas;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class ImageSwitchView {
    
    private List<String> images;

    @PostConstruct
    public void init() {
       images = new ArrayList<String>();
        
       images.add("resources/images/nature1.jpg");
       images.add("resources/images/nature2.jpg");
       images.add("resources/images/nature3.jpg");
       images.add("resources/images/nature4.jpg");
    }

    public List<String> getImages() {
        return images;
    }

    public ImageSwitchView() {
        // TODO Auto-generated constructor stub
    }

}
