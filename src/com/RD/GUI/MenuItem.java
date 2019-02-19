package com.RD.GUI;

import java.awt.*;

/**
 * Created by Matthew 2 on 18/02/2019.
 */
public class MenuItem {
    private String imagePath;
    private String title;

    public MenuItem(String imagePath, String title){
        this.imagePath = imagePath;
        this.title = title;
    }


    public String getImage() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

}
