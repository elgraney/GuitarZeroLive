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
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import static java.awt.Color.*;
import static java.awt.SystemColor.text;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.waiting;

/**
 * Created by Matthew 2 on 26/02/2019.
 */


public class View implements PropertyChangeListener  {
    private Model model;
    private JFrame frame;
    private JTextArea scoreArea;
    private JTextArea multiplierArea;
    private JTextArea streakArea;

    private Image whiteNote;
    private Image blackNote;
    private boolean notRunning =true;
    private ArrayList<Pair<Integer, Integer>> displayNotes =  new ArrayList<>();
    private HashMap<String, NoteObject> imageReferences = new HashMap<String, NoteObject>();
    private boolean waiting = true;

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

        setUpGraphics();

        model.addPropertyChangeListener( this );
        frame.revalidate();
    }

    private void setUpGraphics(){
        setUpScore();
        setUpMultiplier();
        setUpStreak();
        setUpSongDetails();
        setUpZeroPower();
        setUpCurrency();
    }
    private void setUpScore(){
        scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scoreArea.setRows(1);
        scoreArea.setColumns(5);
        scoreArea.setFont(new Font("Arial", Font.BOLD, 30));
        scoreArea.setSize(200, 40);
        scoreArea.setBackground(black);
        scoreArea.setForeground(white);
        scoreArea.setLocation(200, frame.getHeight()-200);
        setScore();
        frame.add(scoreArea);
    }
    private void setScore(){
        scoreArea.setText("Score: "+ Integer.toString(model.getScore()));
    }

    private void setUpMultiplier(){
        multiplierArea = new JTextArea();
        multiplierArea.setEditable(false);
        multiplierArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        multiplierArea.setRows(1);
        multiplierArea.setColumns(4);
        multiplierArea.setFont(new Font("Arial", Font.BOLD, 30));
        multiplierArea.setSize(200, 40);
        multiplierArea.setBackground(black);
        multiplierArea.setForeground(white);
        multiplierArea.setLocation(200, frame.getHeight()-300);
        setMultiplier();
        frame.add(multiplierArea);
    }
    private void setMultiplier(){
        multiplierArea.setText("Multiplier: "+ Integer.toString(model.getMultiplier()));
    }

    private void setUpStreak(){
        streakArea = new JTextArea();
        streakArea.setEditable(false);
        streakArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        streakArea.setRows(1);
        streakArea.setColumns(4);
        streakArea.setFont(new Font("Arial", Font.BOLD, 30));
        streakArea.setSize(200, 40);
        streakArea.setBackground(black);
        streakArea.setForeground(white);
        streakArea.setLocation(200, frame.getHeight()-400);
        setStreak();
        frame.add(streakArea);
    }
    private void setStreak(){
        streakArea.setText("Streak: "+ Integer.toString(model.getStreak()));
    }
    private void setUpZeroPower(){
        //create icon with visability set to false/greyed out until it is needed
    }
    private void setUpSongDetails(){
        //put holder for album art and song title in top left
    }
    private void setUpCurrency(){
        //have 5 icons, make as many visible as needed.
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
    }

    private void setLocation(NoteObject note){

        int chanel = note.getChanel();
        JPanel notePanel =note.getPanel();
        long timeUntilPlayed = note.getTime() - model.getTime();
        //SIZING IS DONE VERY BADLY - REWORK TO USE PROPORTIONS OF BACKGROUND IMAGE

        double y = (frame.getHeight()*0.55) - ((double) timeUntilPlayed)/2000 * (frame.getHeight()*0.45);
        double x = frame.getWidth()/2 - notePanel.getComponent(0).getWidth()/2;
        switch (chanel){
            case 0:
                x += frame.getWidth()/7.5 - ((double) timeUntilPlayed)/2000 * (frame.getHeight()*0.12);
                notePanel.setLocation((int) Math.round(x),(int) Math.round(y));
                break;
            case 1:
                notePanel.setLocation((int)Math.round(x), (int) Math.round(y));
                break;
            case 2:

                x -= frame.getWidth()/7.5 - ((double) timeUntilPlayed)/2000 * (frame.getHeight()*0.12);
                notePanel.setLocation ((int) Math.round(x), (int) Math.round(y));
                break;
            default:
                System.out.println("Something has gone horribly wrong somewhere....");
                break;
        }
    }

    private long timeUntilPlayed(long tick){
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
        if (waiting) {
            waiting=false;
            ArrayList<Pair<Integer, Integer>> newNotes = model.getHighwayNotes();
            for (Pair<Integer, Integer> note : newNotes) {
                if (displayNotes.contains(note)) {
                    redraw(note);
                } else {
                    drawNote(mapNote(note.getValue()), note.getValue(), note.getKey());
                    displayNotes.add(note);
                }
            }
            try { //debugging
                for (Pair<Integer, Integer> note : displayNotes) {
                    if (!newNotes.contains(note)) {
                        removeNotes(note);
                    }
                }
            }
            catch(ConcurrentModificationException e){
                //DO SOMETHING HERE LIKE WAIT AND TRY AGAIN
                try {
                    sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                propertyChange(null);
            }
            setScore();
            setMultiplier();
            setStreak();
            frame.revalidate();
            frame.repaint();
            waiting=true;
        }
    }
}
