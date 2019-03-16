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
    SongMenuItem currentBundle = new SongMenuItem("clientzips/AnotherOneBitesTheDust/AnotherOneBitesTheDust.png",
                                            "AnotherOneBitesTheDust",
                                            "clientzips/AnotherOneBitesTheDust/Queen_-_Another_One_Bites_the_Dust.mid",
                                            "clientzips/AnotherOneBitesTheDust/file.txt");
    String backgroundImage = "assets/background1.png";
    String highwayImage = "assets/betterHighway.png";

    public SetUpGUI(){
        setTitle( "Guitar Zero Live" );
        setContentPane( new BackgroundImage(backgroundImage, highwayImage));

        setLayout( null );

        //sets up the carousel and assigns a listener to the frame
        ModeTemplate modeTest = new SlashMode(this, this);
        setListener(new GUIControls(this, modeTest));
        Guitar guitar = new Guitar(modeTest);
        guitar.run();
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
