package com.RD.GUI;

import java.awt.*;

/**
 * Created by Matthew 2 on 18/02/2019.
 */
public class MenuItem {
    private Image image;
    private String title;

    public MenuItem(Image image, String title){
        this.image = image;
        this.title = title;
    }


    public Image getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

}
