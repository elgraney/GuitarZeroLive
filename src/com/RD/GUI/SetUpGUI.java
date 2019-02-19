package com.RD.GUI;

import javax.swing.*;
import java.awt.event.KeyListener;

/**
 * Created by Matthew 2 on 17/02/2019.
 */
public  class SetUpGUI extends JFrame {

    KeyListener currentListener;

    public SetUpGUI(){
        setTitle( "Guitar Zero Live" );

        setContentPane( new com.RD.GUI.BackgroundImage("assets/background1.png", "assets/betterHighway.png"));
        setLayout( null );


        ModeTemplate modeTest = new SlashMode(this, this); //will be instance of play menu in future, when implemented
        setListener(new GUIControls(this, modeTest));

    }
    public void setListener(KeyListener listener){
        currentListener = listener;
        this.addKeyListener(currentListener);
    }
    public void removeListener(){
        this.removeKeyListener(currentListener);
    }
}
