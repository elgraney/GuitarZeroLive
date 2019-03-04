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

import static java.awt.Color.*;

/**
 * Created by Matthew 2 on 26/02/2019.
 */


public class View implements PropertyChangeListener  {
    private Model model;
    private JFrame frame;
    private JPanel graphicsPanel;
    private JPanel notesPanel;

    private Image whiteNote;
    private Image blackNote;
    private boolean notRunning =true;
    private ArrayList<Pair<Integer, Integer>> displayNotes =  new ArrayList<>();

    public View(Model model){
        this.model = model;
        frame = model.getFrame();
        frame.setLayout(null);
        graphicsPanel = new JPanel();
        notesPanel = new JPanel();
        notesPanel.setSize(new Dimension(frame.getWidth(), frame.getHeight()));

        notesPanel.setLayout(null);
        graphicsPanel.setLayout(null);
        graphicsPanel.add(notesPanel);
        graphicsPanel.setSize(new Dimension(frame.getWidth(), frame.getHeight()));

        frame.add(graphicsPanel);
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

    private void drawNote(boolean white, int channel, int time ){
        Image noteIcon;
        if (white){
            noteIcon = whiteNote;

        }
        else{
            noteIcon=blackNote;
        }
        JPanel notePane = new JPanel();
        notePane.setBackground(BLACK);
        notePane.setSize(new Dimension(100, 100));

        switch (channel){
            case 0:
                break;
            case 1:
                notePane.setBackground(BLACK);
                notePane.setLocation(200,200 + time/2);
                break;
            case 2:
                notePane.setBackground(WHITE);
                notePane.setLocation(400,400 + time/2);
                break;
            default:
                System.out.println("Something has gone horribly wrong somewhere....");
                break;
        }
        notesPanel.add(notePane);
        graphicsPanel.revalidate();
        graphicsPanel.repaint();
    }

    private int timeUntilPlayed(int tick){
        return (tick - model.getTime());
    }

    public void redraw() {
        displayNotes = model.getHighwayNotes();

        for(Pair<Integer, Integer> note:displayNotes){

            drawNote(mapNote(note.getValue()), mapChannel(note.getValue()), timeUntilPlayed(note.getKey()));
        }
        displayNotes = null;

    }

    public  void propertyChange( PropertyChangeEvent evt ) {
        if (notRunning){
            notRunning = false;
            notesPanel.removeAll();
            redraw();
            notRunning = true;
        }
    }
}
