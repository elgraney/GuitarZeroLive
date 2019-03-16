package com.RD.GUI;

/**
 * Created by Joe on 20/02/2019.
 * All content by Joe
 */
public class SongMenuItem extends MenuItem{

    private String midiFilePath;
    private String notesFilePath;

    public SongMenuItem(String imagePath, String title, String midiFilePath, String notesFilePath){
        super(imagePath, title);
        this.midiFilePath = midiFilePath;
        this.notesFilePath = notesFilePath;
    }

    public String getMidiFilePath() {
        return midiFilePath;
    }

    public String getNotesFilePath() {
        return notesFilePath;
    }

}