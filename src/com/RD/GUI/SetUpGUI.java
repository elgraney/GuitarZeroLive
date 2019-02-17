package com.RD.GUI;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Matthew 2 on 17/02/2019.
 */
public class SetUpGUI extends JFrame {

    private void tempEscapeExit(){
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {  // handler
                if(ke.getKeyCode() == ke.VK_ESCAPE) {
                    System.out.println("escaped ?");
                    SetUpGUI.this.dispose();
                }
                else {
                    System.out.println("not escaped");
                }
            }
        });
    }

    public SetUpGUI(){
        setTitle( "Guitar Zero Live" );

        setContentPane( new com.RD.GUI.BackgroundImage("assets/background1.png", "assets/betterHighway.png"));
        setLayout( null );

        tempEscapeExit();

        ModeTemplate modeTest = new ModeTemplate(); //will be instance of play menu in future, when implemented
    }
}
