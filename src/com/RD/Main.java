package com.RD;

import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SetUpGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;


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

        //have guitar highway set aspect ratio locked, to fit screen vertically, centered
        //have high res background image
        //have container holding 5 things locked center, relative size.
    }

}