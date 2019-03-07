package com.RD.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthew on 15/02/2019.
 * All content by Matthew
 */
public class BackgroundImage extends JPanel {
    private int baseWidth;
    private int baseHeight;

    private Image img1;
    private Image img2;

    /**
     * initializes images
     *
     * @param img1
     * @param img2
     */
    public BackgroundImage(String img1, String img2) {
        this(new ImageIcon(img1).getImage(), new ImageIcon(img2).getImage());
    }

    /**
     * sets default background images size and position
     * img1 is a background image, img2 is the highway
     */
    public BackgroundImage(Image img1, Image img2) {
        this.img1 = img1;
        Dimension size = new Dimension(img1.getWidth(null), img1.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);

        this.img2 = img2;
        baseWidth = img2.getWidth(null);
        baseHeight = img2.getHeight(null);
        Dimension size2 = new Dimension(baseWidth, baseHeight);
        setPreferredSize(size2);
        setMinimumSize(size2);
        setMaximumSize(size2);
        setSize(size2);
        setLayout(null);
    }


    /**
     * Redraws background images to fit screen resolution
     * @param g
     */
    public void paintComponent(Graphics g) {
        g.drawImage(img1, 0, 0, getWidth(), getHeight(), null);

        Rectangle r = this.getBounds();
        int height = r.height;
        float multiplier = (float) height / (float) baseHeight;
        int width = Math.round(baseWidth * multiplier);
        g.drawImage(img2, (getWidth() / 2) - (width / 2), 0, width, getHeight(), null);
    }
}