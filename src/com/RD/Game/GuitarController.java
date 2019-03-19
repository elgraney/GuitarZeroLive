package com.RD.Game;

import com.RD.GUI.GUIControls;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matthew on 26/02/2019.
 * Currently almost entirely a stand in for the real guitar controls; for testing.
 */
public class GuitarController implements KeyListener {
    private Model model;
    private final Set<Integer> pressed = new HashSet<Integer>(); //for multiple buttons (not sure if relevant for guitar or not)
    public GuitarController(Model model){
        this.model = model;
    }
    public void keyReleased( KeyEvent evt ) {
        pressed.remove(evt.getKeyCode());
    }

    public void keyTyped( KeyEvent evt ) { /* nothing */ }

    public void keyPressed( KeyEvent evt ) {
        System.out.println("Press");
        pressed.add(evt.getKeyCode());
        if (model.getState() == Model.InputState.NORMAL) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_SPACE: //stand-in for strum
                    if (pressed.size() > 1 && (pressed.contains(KeyEvent.VK_1)
                            || pressed.contains(KeyEvent.VK_2)
                            || pressed.contains(KeyEvent.VK_3)
                            || pressed.contains(KeyEvent.VK_4)
                            || pressed.contains(KeyEvent.VK_5)
                            || pressed.contains(KeyEvent.VK_6))) {
                        String notes = "";
                        for (Integer key : pressed){
                            switch (key){
                                case 49:
                                    notes += "1";
                                    break;
                                case 50:
                                    notes += "2";
                                    break;
                                case 51:
                                    notes += "3";
                                    break;
                                case 52:
                                    notes += "4";
                                    break;
                                case 53:
                                    notes += "5";
                                    break;
                                case 54:
                                    notes += "6";
                                    break;
                            }
                        }
                        playNote(notes);
                        break; //will also need to pass other buttons pressed at same time
                    } else {
                        model.missNote();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    //pause music
                    //pause menu
                    //set state paused
                    model.setState(Model.InputState.PAUSED);
                    break;
            }
        }
        else if(model.getState() == Model.InputState.ZERO_POWER){
            //MAKE SURE TO ADD ZERO POWER HERE
        }
    }


    private void playNote(String notes) throws ConcurrentModificationException{
        // find out what notes should be played at this time and compare to note played 12
        // either call hitNote or missNote
        ArrayList<Pair<Integer, Integer>> highwayNotes = model.getHighwayNotes();
            for (Pair<Integer, Integer> note : highwayNotes) {
                if (note.getKey() > model.getTime() - model.getTickPerSecond()*0.1 && note.getKey() < model.getTime() + model.getTickPerSecond()*0.1) {
                    if (notes.contains(Integer.toString(note.getValue()))) {
                        model.hitNote(note);
                    }
                    else {
                        model.missNote();
                    }
                }
                else{
                    model.missNote();
                }
        }
    }
    //in future put sound in own class using observer pattern
}
