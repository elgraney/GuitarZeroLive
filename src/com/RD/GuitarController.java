package com.RD;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/*
 * Plastic Guitar Controls
 *
 * Created by Sophia
 */


public class GuitarController implements KeyListener{
    private JFrame frame;

    public GuitarController(JFrame frame){
        this.frame = frame;
    }

    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_Q:
                System.out.println("Button 0 pressed, White Note");
                // call method in model for 1st white note played

            case KeyEvent.VK_W:
                System.out.println("Button 4 pressed, White Note");
                // call method in model for 2nd white note played

            case KeyEvent.VK_E:
                System.out.println("Button 5 pressed, White Note");
                // call method in model for 3rd white note played

            case KeyEvent.VK_A:
                System.out.println("Button 1 pressed, Black Note");
                // call method in model for 1st black note played

            case KeyEvent.VK_S:
                System.out.println("Button 2 pressed, Black Note");
                // call method in model for 2nd black note played

            case KeyEvent.VK_D:
                System.out.println("Button 3 pressed, Black Note");
                // call method in model for 3rd black note played
            case KeyEvent.VK_UP:
                System.out.println("Strum bar pressed up");
                // call method in model for strum played up
            case KeyEvent.VK_DOWN:
                System.out.println("Strum bar pressed down");
                // call method in model for strum played down
            case KeyEvent.VK_P:
                System.out.println("Button 8 Hero Power pressed");
                // call method in model for Hero Power

            case KeyEvent.VK_O:
                System.out.println("Button 9 pressed");
                // call method in model for button 9
            case KeyEvent.VK_DELETE:
                System.out.println("Button 10 pressed, Escape button");
                // call method in model for escape
            case KeyEvent.VK_K:
                System.out.println("Button 12 pressed, on button");
                // call method in model for turning on
            case KeyEvent.VK_L:
                System.out.println("Button 13 pressed, Strum button");
                // call method in model for strumming to a note, 8 positions
            case KeyEvent.VK_J:
                System.out.println("Whammy bar pressed");
                // call method in model for whammy bar
        }

    }
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        System.out.println( );
    }


}