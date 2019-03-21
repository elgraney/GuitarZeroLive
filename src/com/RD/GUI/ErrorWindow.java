package com.RD.GUI;


import javax.swing.*;


/**
 * Error Window
 * @ Sophia Wallgren
 */

public class ErrorWindow extends JFrame{

    public ErrorWindow(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }


}
