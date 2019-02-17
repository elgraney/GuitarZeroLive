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



    public ModeTemplate() {
        setUpCarousel();

    }

}
