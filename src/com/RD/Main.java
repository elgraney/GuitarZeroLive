package com.RD;

import com.RD.GUI.ModeTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Main {

    public static void main(String[] args) {
        System.out.println("Test works?");
        JFrame frame = new ModeTemplate();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

}