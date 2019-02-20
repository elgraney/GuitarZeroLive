package com.RD.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Matthew 2 on 15/02/2019.
 */
public class ModeTemplate extends JFrame{
    protected JFrame frame;
    protected SetUpGUI base;

    private ArrayList<MenuItem> allOptions;
    private MenuItem[] viewOptions = new MenuItem[5];
    private int startIndex = 0;
    private JPanel container;

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
        for(int i=startIndex; i<5; i++){
            viewOptions[i] = options.get(i);
        }
    }

    public void left(){

        startIndex -=1;
        if (startIndex<0){
            startIndex = allOptions.size()-1;
        }
        for(int i=0, j=startIndex; i<5; i++, j++){
            viewOptions[i] = allOptions.get(j%allOptions.size());;
        }
        frame.remove(container);
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.revalidate();

    }
    public void right(){
        startIndex +=1;
        if (startIndex<0){
            startIndex = allOptions.size()-1;
        }
        for(int i=0, j=startIndex; i<5; i++, j++){
            viewOptions[i] = allOptions.get(j%allOptions.size());;
        }
        frame.remove(container);
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.revalidate();
    }


    public void onSelect(){}


    public void tearDown(){
        frame.remove(container);
        base.removeListener();
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

        container = new JPanel();

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


        container.setBorder(BorderFactory.createLineBorder(Color.BLUE,8));

        container.setBackground(Color.white);

        frame.setLayout(new GridBagLayout());

        frame.add(container, new GridBagConstraints());
    }


    public ModeTemplate(JFrame frame, SetUpGUI base) {
        this.frame = frame;
        this.base = base;
    }
}