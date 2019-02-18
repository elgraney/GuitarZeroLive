package com.RD.GUI;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import static java.awt.SystemColor.text;
import static javafx.scene.input.KeyCode.T;

/**
 * Created by Matthew 2 on 15/02/2019.
 */
public class ModeTemplate extends JFrame {
    Frame frame;
    ArrayList<Object> options;
    Object[] viewOptions;

    private JPanel setUpOption(String imagePath, String text){
        JPanel option = new JPanel();
        option.setLayout(new BorderLayout());
        try {
            option.add(new JLabel(new ImageIcon(ImageIO.read(new File(imagePath)))), BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(2);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        option.add(textArea, BorderLayout.SOUTH);
        return option;
    }


    protected void setUpCarousel(){

        JPanel container = new JPanel();


        JPanel option1 = setUpOption("assets/red.png", "Hey look new line works 2 lines before getting weird");
        JPanel option2 = setUpOption("assets/green.png", "placeholder2");
        JPanel option3 = setUpOption("assets/blue.png", "placeholder3");
        JPanel option4 = setUpOption("assets/yellow.png", "placeholder4");
        JPanel option5 = setUpOption("assets/orange.png", "placeholder5");


        container.add(option1);container.add(option2);container.add(option3);container.add(option4);container.add(option5);
        container.setLayout(new GridLayout(1,5,5,5));
        container.setBorder(BorderFactory.createLineBorder(Color.white,5));


        container.setBackground(Color.white);
        frame.setLayout(new GridBagLayout());

        frame.add(container, new GridBagConstraints());
    }



    public ModeTemplate(Frame frame) {
        this.frame = frame;
        setUpCarousel();
    }


}
