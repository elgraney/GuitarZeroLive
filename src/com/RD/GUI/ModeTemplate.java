package com.RD.GUI;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * Created by Matthew 2 on 15/02/2019.
 */
public class ModeTemplate extends JFrame {


    protected void setUpCarousel(){
        Rectangle r = rootPane.getBounds();
        int h = r.height;
        int w = r.width;
        JPanel container = new JPanel();
        System.out.println(w);
        container.setBounds(450, 300, 1000,  300);//make dynamic

        container.setBackground(Color.black);
        this.add(container);
    }

    private void tempEscapeExit(){
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {  // handler
                if(ke.getKeyCode() == ke.VK_ESCAPE) {
                    System.out.println("escaped ?");
                    ModeTemplate.this.dispose();
                }
                else {
                    System.out.println("not escaped");
                }
            }
        });
    }

    public ModeTemplate() {
        setTitle( "Guitar Zero Live" );

        setContentPane( new com.RD.GUI.BackgroundImage("assets/betterHighway.png"));
        setLayout( null );

        setUpCarousel();

        tempEscapeExit();


    }

}
