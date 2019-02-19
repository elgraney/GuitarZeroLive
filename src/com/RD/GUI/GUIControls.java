package com.RD.GUI;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matthew 2 on 19/02/2019.
 */
public class GUIControls implements KeyListener {
    private JFrame frame;
    private ModeTemplate template;
    public GUIControls(JFrame frame, ModeTemplate template){
        this.frame = frame;
        this.template = template;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key typed");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                System.out.println("escaped?");
                frame.dispose();
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                template.left();
                break;
            case KeyEvent.VK_RIGHT :
                System.out.println("right");
                template.right();
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
    }
}
