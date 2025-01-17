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

/**
 * Created by Matthew on 15/02/2019.
 * All content by Matthew
 */
public class ModeTemplate extends JFrame{
    JFrame frame;
    SetUpGUI base;

    private ArrayList<MenuItem> allOptions;
    private MenuItem[] viewOptions = new MenuItem[5];
    private int startIndex = 0;
    private JPanel container;

    public final int CAROUSEL_SIZE = 5;

    public ArrayList<MenuItem> getOptions() {
        return allOptions;
    }

    private void setOptions(ArrayList<MenuItem> options) {
        this.allOptions = options;
    }

    MenuItem[] getViewOptions() {
        return viewOptions;
    }

    public void setViewOptions(MenuItem[] viewOptions) {
        this.viewOptions = viewOptions;
    }

    /**
     *  Sets up the 5 starting items for a menu mode
     */
    void innitMenu(ArrayList<MenuItem> options){
        setOptions(options);
        for(int i=startIndex; i<CAROUSEL_SIZE; i++){
            viewOptions[i] = options.get(i);
        }
    }

    /**
    *When left key is pressed, change the positions of the menu items and refresh the screen
    */
    void left(){
        startIndex -=1;
        if (startIndex<0){
            startIndex = allOptions.size()-1;
        }
        for(int i=0, j=startIndex; i<CAROUSEL_SIZE; i++, j++){
            viewOptions[i] = allOptions.get(j%allOptions.size());
        }
        frame.remove(container);
        try {
            setUpCarousel();
        } catch (IOException e) {
            new ErrorWindow("No files for carousel found! Exiting...");
            e.printStackTrace();
            System.exit(1);
        }
        frame.revalidate();

    }

    /**
     *When left key is pressed, change the positions of the menu items and refresh the screen
     */
    void right(){
        startIndex +=1;
        if (startIndex<0){
            startIndex = allOptions.size()-1;
        }
        for(int i=0, j=startIndex; i<CAROUSEL_SIZE; i++, j++){
            viewOptions[i] = allOptions.get(j%allOptions.size());
        }
        frame.remove(container);
        try {
            setUpCarousel();
        } catch (IOException e) {
            new ErrorWindow("No files for carousel found! Exiting...");
            e.printStackTrace();
            System.exit(1);
        }
        frame.revalidate();
    }

    /**
     *Following two methods are overridden in the subclasses so that the appropriate action can be taken
     */
    public void onSelect(){}

    public void onEscape(){}

    /**
     * Destroys a menu mode interface and removes its listener
     */
    public void tearDown(){
        frame.remove(container);
        ((MenuGuitar) base.getGuitar()).disconnect();
        base.nullGuitar();
        base.removeListener();
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Defines the layout of a single menu item in the carousel
     */
    private JPanel setUpOption(String imagePath, String text){
        JPanel option = new JPanel();
        option.setLayout(new BorderLayout());
        try {
            option.add(new JLabel(new ImageIcon(ImageIO.read(new File(imagePath)))), BorderLayout.CENTER);
        } catch (IOException e) {
            new ErrorWindow("No files for carousel found! Exiting...");
            e.printStackTrace();
            System.exit(1);
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

    /**
     *  sets the layout of the carousel
     */
    void setUpCarousel() throws IOException {
        container = new JPanel();
        JPanel option1 = setUpOption(viewOptions[0].getImage(),viewOptions[0].getTitle());
        JPanel option2 = setUpOption(viewOptions[1].getImage(),viewOptions[1].getTitle());
        JPanel option3 = setUpOption(viewOptions[2].getImage(),viewOptions[2].getTitle());
        JPanel option4 = setUpOption(viewOptions[3].getImage(),viewOptions[3].getTitle());
        JPanel option5 = setUpOption(viewOptions[4].getImage(),viewOptions[4].getTitle());

        option1.setBorder( BorderFactory.createLineBorder(Color.WHITE,5));

        option2.setBorder( BorderFactory.createLineBorder(Color.WHITE,5));

        option3.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.BLUE));

        option4.setBorder( BorderFactory.createLineBorder(Color.WHITE,5));

        option5.setBorder( BorderFactory.createLineBorder(Color.WHITE,5));

        container.add(option1);container.add(option2);container.add(option3);container.add(option4);container.add(option5);
        container.setLayout(new GridLayout(1,0,0,0));
        container.setBorder(BorderFactory.createLineBorder(Color.BLUE,8));
        container.setBackground(Color.white);

        frame.setLayout(new GridBagLayout());

        frame.add(container, new GridBagConstraints());
    }

    ModeTemplate(JFrame frame, SetUpGUI base) {
        this.frame = frame;
        this.base = base;
    }
}
