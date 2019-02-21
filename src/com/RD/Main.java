package com.RD;

import com.RD.GUI.SetUpGUI;
import javax.swing.*;
import java.awt.*;



public class Main {

    public static void main(String[] args) {
        JFrame frame = new SetUpGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setMinimumSize(new Dimension(800, 400));
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

}