package com.RD.Game;

import com.RD.GUI.GUIControls;
import javafx.scene.input.KeyCode;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matthew 2 on 26/02/2019.
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
        pressed.add(evt.getKeyCode());
        switch ( evt.getKeyCode() ) {
            case KeyEvent.VK_SPACE : //standin for strum
                if (pressed.size() > 1 && (pressed.contains(KeyEvent.VK_1)
                        || pressed.contains(KeyEvent.VK_2)
                        || pressed.contains(KeyEvent.VK_3)
                        || pressed.contains(KeyEvent.VK_4)
                        || pressed.contains(KeyEvent.VK_5)
                        || pressed.contains(KeyEvent.VK_6)))
                { //if strum and holding note (using placeholder keys)
                    playNote();
                    break; //will also need to pass other buttons pressed at same time
                }
                else{
                    model.missNote();
                }
            case KeyEvent.VK_ESCAPE :
                //pause music
                //pause menu
                //set state paused
                model.setState(Model.InputState.PAUSED);
        }
    }


    private void playNote(){
        Model.InputState state = model.getState();
        switch(state){
            case NORMAL:
                checkNote(); //check against model to see if this note shoul have been played
                break;
            case ZERO_POWER:
                model.missNote(); //act as missed note, break streak etc
        }
    }

    private void checkNote(){
        System.out.println("Note:");
        System.out.println(pressed);
        playSound("assets/BruhSoundEffect2.wav");
        /// /find out what notes should be played at this time and compare to note played
        // either call hitNote or missNote
    }


    //in future put sound in own class using observer pattern
    private void playSound(String filename){
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        AudioStream as = null;
        try {
            as = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(as);

    }
}
