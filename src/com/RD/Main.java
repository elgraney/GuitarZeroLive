package com.RD;

import com.RD.GUI.SetUpGUI;
import com.RD.Server.Server;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Server.main();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
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