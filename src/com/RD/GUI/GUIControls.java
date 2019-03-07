package com.RD.GUI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matthew on 19/02/2019.
 * All content by Matthew
 */
public class GUIControls implements KeyListener {
    private JFrame frame;
    private ModeTemplate template;

    public GUIControls(JFrame frame, ModeTemplate template){
        this.frame = frame;
        this.template = template;
    }

    public void UpdateMode(ModeTemplate template){
        this.template = template;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * //calls the appropriate method for each button for each type of menu
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                System.out.println("escaped?");
                template.onEscape();
                break;

            case KeyEvent.VK_LEFT:
                System.out.println("left");
                template.left();
                break;

            case KeyEvent.VK_RIGHT :
                System.out.println("right");
                template.right();
                break;

            case KeyEvent.VK_ENTER:
                System.out.println("Enter");
                template.onSelect();
        }
    }


    @Override
    public void keyReleased(KeyEvent e){
    }
}
