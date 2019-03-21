package com.RD.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.*;


/**
 * Error Window
 * @ Sophia Wallgren
 */

public class ErrorWindow extends JFrame{

    String errorImage = "assets/.....jpg";

    public ErrorWindow(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
    }





}
