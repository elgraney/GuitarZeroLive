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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import static java.awt.SystemColor.text;
import static javafx.scene.input.KeyCode.J;
import static javafx.scene.input.KeyCode.T;

/**
 * Created by Matthew 2 on 15/02/2019.
 */
public class ModeTemplate extends JFrame {
    private Frame frame;
    private ArrayList<MenuItem> allOptions;
    private MenuItem[] viewOptions = new MenuItem[5];

    public ArrayList<MenuItem> getOptions() {
        return allOptions;
    }

    public void setOptions(ArrayList<MenuItem> options) {
        this.allOptions = options;
    }

    public MenuItem[] getViewOptions() {
        return viewOptions;
    }

    public void setViewOptions(MenuItem[] viewOptions) {
        this.viewOptions = viewOptions;
    }

    public void innitMenu(ArrayList<MenuItem> options){
        setOptions(options);
        for(int i=0; i<5; i++){
            viewOptions[i] = options.get(i);
        }
    }

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


    protected void setUpCarousel() throws IOException {


        JPanel container = new JPanel();

        JPanel option1 = setUpOption(viewOptions[0].getImage(),viewOptions[0].getTitle());
        JPanel option2 = setUpOption(viewOptions[1].getImage(),viewOptions[1].getTitle());
        JPanel option3 = setUpOption(viewOptions[2].getImage(),viewOptions[2].getTitle());
        JPanel option4 = setUpOption(viewOptions[3].getImage(),viewOptions[3].getTitle());
        JPanel option5 = setUpOption(viewOptions[4].getImage(),viewOptions[4].getTitle());

        option1.setBorder( BorderFactory.createLineBorder(Color.WHITE,5));

        Border highlightBorder2 = BorderFactory.createMatteBorder(0,0,0,3, Color.BLUE);
        Border padding2 = new EmptyBorder(0,0,0,2);
        option2.setBorder( new CompoundBorder(highlightBorder2, padding2));

        Border highlightBorder3 = BorderFactory.createMatteBorder(0,3,0,3, Color.BLUE);
        Border padding3 = new EmptyBorder(0,2,0,2);
        option3.setBorder(new CompoundBorder(highlightBorder3, padding3));

        Border highlightBorder4 = BorderFactory.createMatteBorder(0,3,0,0, Color.BLUE);
        Border padding4 = new EmptyBorder(0,2,0,0);
        option4.setBorder( new CompoundBorder(highlightBorder4, padding4));

        option5.setBorder( BorderFactory.createLineBorder(Color.WHITE,5));

        container.add(option1);container.add(option2);container.add(option3);container.add(option4);container.add(option5);
        container.setLayout(new GridLayout(1,0,0,0));

         ;

        container.setBorder(BorderFactory.createLineBorder(Color.BLUE,8));

        container.setBackground(Color.white);

        frame.setLayout(new GridBagLayout());

        frame.add(container, new GridBagConstraints());

    }



    public ModeTemplate(Frame frame) {
        this.frame = frame;
    }


}
