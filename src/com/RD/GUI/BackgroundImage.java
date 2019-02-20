package com.RD.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthew 2 on 15/02/2019.
 */
public class BackgroundImage extends JPanel{

        private Image img;

        public  BackgroundImage(String img) {
            this(new ImageIcon(img).getImage());
        }

        public  BackgroundImage(Image img) {
            this.img = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        }
    }