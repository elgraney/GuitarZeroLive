package com.RD.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthew 2 on 18/02/2019.
 */
public class Carousel {

    public Carousel(Frame frame){
        Rectangle r = frame.getBounds();
        int h = r.height;
        int w = r.width;
        System.out.println(w);
        JPanel container = new JPanel();

        container.setBounds(450, 300, 1000,  200);//make dynamic

        container.setBackground(Color.black);
        frame.add(container);
    }
}
