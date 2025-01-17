package com.RD.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

import static java.awt.Color.black;
import static java.awt.Color.white;

/**
 * Created by Matthew on 17/02/2019.
 * Content by Matthew
 */
public class SetUpGUI extends JFrame {

    KeyListener currentListener;
    SongMenuItem currentBundle = new SongMenuItem("clientzips/demo/FeelGOodInc.png",
                                            "demo",
                                            "clientzips/demo/GORILLAZ_-_Feel_Good_Inc.mid",
                                            "clientzips/demo/file.txt");
    String backgroundImage = "assets/background1.png";
    String highwayImage = "assets/betterHighway.png";
    Guitar guitar;

    public SetUpGUI(){
        setTitle( "Guitar Zero Live" );
        setContentPane( new BackgroundImage(backgroundImage, highwayImage));

        setLayout( null );

        //sets up the carousel and assigns a listener to the frame
        ModeTemplate slashMode = new SlashMode(this, this);
        setListener(new GUIControls(this, slashMode));

        // creates guitar instance which polls for button presses
        guitar = new MenuGuitar(slashMode);
        guitar.run();
    }

    public void nullGuitar() {
        guitar =null;
    }

    public void setGuitar(Guitar guitar) {
        this.guitar = guitar;
        guitar.run();
    }

    public Guitar getGuitar() {
        return guitar;
    }

    public SongMenuItem getCurrentBundle() {
        return currentBundle;
    }

    public void setCurrentBundle(SongMenuItem currentBundle) {
        this.currentBundle = currentBundle;
    }

    //for setting and removing listeners when changing between modes
    public void setListener(KeyListener listener){
        currentListener = listener;
        this.addKeyListener(currentListener);
    }
    public void removeListener(){
        this.removeKeyListener(currentListener);
    }
}
