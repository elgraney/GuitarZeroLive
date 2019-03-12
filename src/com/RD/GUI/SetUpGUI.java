package com.RD.GUI;

import javax.swing.*;
import java.awt.event.KeyListener;

/**
 * Created by Matthew on 17/02/2019.
 * Content by Matthew
 */
public  class SetUpGUI extends JFrame {

    KeyListener currentListener;

    public SetUpGUI(){
        setTitle( "Guitar Zero Live" );
        setContentPane( new BackgroundImage("assets/background1.png", "assets/betterHighway.png"));
        setLayout( null );

        //sets up the carousel and assigns a listener to the frame
        ModeTemplate modeTest = new SlashMode(this, this);
        setListener(new GUIControls(this, modeTest));
        Guitar guitar = new Guitar(modeTest);
        guitar.run();

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
