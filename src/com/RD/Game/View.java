package com.RD.Game;

import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.awt.Color.*;

/**
 * Created by Matthew 2 on 26/02/2019.
 */


public class View implements PropertyChangeListener  {
    private Model model;
    private JFrame frame;

    private Image whiteNote;
    private Image blackNote;
    private boolean notRunning =true;
    private ArrayList<Pair<Integer, Integer>> displayNotes =  new ArrayList<>();
    private HashMap<String, NoteObject> imageReferences = new HashMap<String, NoteObject>();

    public View(Model model){
        this.model = model;
        frame = model.getFrame();
        frame.setLayout(null);

        try {
            whiteNote = new ImageIcon(ImageIO.read(new File("assets/whiteNote.png"))).getImage();
            blackNote = new ImageIcon(ImageIO.read(new File("assets/blackNote.png"))).getImage();
        } catch (IOException e) {
            System.out.println("Critical error - unable to load assets");
            //do something else, like crash horribly
        }

        model.addPropertyChangeListener( this );
        frame.revalidate();
    }

    private boolean mapNote(Integer note){
        if (note>3){
            return true;
        }
        else {
            return false;
        }
    }
    private int mapChannel(Integer note){
        return (note % 3);
    }

    private void drawNote(boolean white, int note, int time ){
        Image noteIcon;
        int channel = mapChannel(note);
        if (white){
            noteIcon = whiteNote;
        }
        else{
            noteIcon=blackNote;
        }
        JPanel notePane = new JPanel();
        notePane.add(new JLabel(new ImageIcon(noteIcon)));
        notePane.setSize(new Dimension(100, 100));
        notePane.setOpaque(false);

        NoteObject noteObject = new NoteObject(time, channel, notePane);
        setLocation(noteObject);
        frame.add(notePane);
        imageReferences.put(Integer.toString(time)+Integer.toString(note), new NoteObject(time, channel, notePane));
        System.out.println("Added new note Object");
        System.out.println(noteObject.getTime());
    }

    private void setLocation(NoteObject note){
        int channel = note.getChannel();
        JPanel notePanel =note.getPanel();
        int timeUntilPlayed = note.getTime() - model.getTime();
        switch (channel){
            case 0:
                notePanel.setLocation(frame.getWidth()/2 -frame.getWidth()/8 - 50, 100);
            case 1:
                double y = (frame.getHeight()*0.55) - ((double) timeUntilPlayed)/2000 * (frame.getHeight()*0.45);
                notePanel.setLocation(frame.getWidth()/2 - 50, (int) Math.round(y));
                System.out.println( (((float) timeUntilPlayed)/2000) * (frame.getHeight()*0.45));
                break;
            case 2:
                notePanel.setLocation(frame.getWidth()/2 +frame.getWidth()/8 - 50, 100);
                break;
            default:
                System.out.println("Something has gone horribly wrong somewhere....");
                break;
        }
    }

    private int timeUntilPlayed(int tick){
        return (tick - model.getTime());
    }

    public void redraw(Pair<Integer, Integer> note) {
        String key = Integer.toString(note.getKey())+Integer.toString(note.getValue());
        NoteObject noteObject = imageReferences.get(key);
        setLocation(noteObject);
    }

    private void removeNotes(Pair<Integer, Integer> note){
        displayNotes.remove(note);
        String key = Integer.toString(note.getKey())+Integer.toString(note.getValue());
        NoteObject noteObject = imageReferences.get(key);
        frame.remove(noteObject.getPanel());
        imageReferences.remove(key);
    }

    public  void propertyChange( PropertyChangeEvent evt ) {
        ArrayList<Pair<Integer, Integer>> newNotes = model.getHighwayNotes();
        for (Pair<Integer, Integer> note : newNotes){
            if (displayNotes.contains(note)){
               redraw(note);
            }
            else{
               drawNote(mapNote(note.getValue()), note.getValue(), note.getKey());
               displayNotes.add(note);
           }
        }
        for(Pair<Integer, Integer> note : displayNotes){
            if (!newNotes.contains(note)){
                removeNotes(note);
           }
        }
        System.out.println("paint");
        frame.revalidate();
        frame.repaint();
    }
}
