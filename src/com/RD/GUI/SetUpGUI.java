package com.RD.GUI;

import javax.swing.*;

/**
 * Created by Matthew 2 on 17/02/2019.
 */
public class SetUpGUI extends JFrame {

    public SetUpGUI(){
        setTitle( "Guitar Zero Live" );

        setContentPane( new com.RD.GUI.BackgroundImage("assets/background1.png", "assets/betterHighway.png"));
        setLayout( null );


        ModeTemplate modeTest = new SlashMode(this); //will be instance of play menu in future, when implemented
        this.addKeyListener(new GUIControls(this, modeTest));
    }

}
