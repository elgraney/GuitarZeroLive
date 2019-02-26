package com.RD.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Matthew 2 on 26/02/2019.
 */
public class View{
    private Model model;
    private JFrame frame;
    public View(Model model){
        this.model = model;
        frame = model.getFrame();
        frame.setLayout(new GridBagLayout());
        try {
            frame.add(new JLabel(new ImageIcon(ImageIO.read(new File("assets/sans.png")))), new GridBagConstraints());
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.revalidate();
    }

}
