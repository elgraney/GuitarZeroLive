package com.RD.GUI;

/**
 * Created by Matthew on 18/02/2019.
 * All content by Matthew
 */
public class MenuItem {
    private String folder;
    private String imagePath;
    private String title;

    public MenuItem(String imagePath, String title){
        this.imagePath = imagePath;
        this.title = title;
    }
    public String getFolder() {
        return folder;
    }

    public String getImage() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

}
