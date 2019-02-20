package com.RD.GUI;

/**
 * Created by Joe on 20/02/2019.
 */
public class SongMenuItem extends MenuItem{
    private String imagePath;
    private String title;
    private String midiFilePath;
    private String notesFilePath;

    public SongMenuItem(String imagePath, String title, String midiFilePath, String notesFilePath){
        super(imagePath, title);
        this.midiFilePath = midiFilePath;
        this.notesFilePath = notesFilePath;
    }


    public String getImage() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getMidiFilePath() {
        return midiFilePath;
    }

    public String getNotesFilePath() {
        return notesFilePath;
    }

}