package com.RD.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew 2 on 19/02/2019.
 */
public class SelectMode extends ModeTemplate {
    public SelectMode(JFrame frame, SetUpGUI base) {
        super(frame, base);

        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList(
                new MenuItem("assets/red.png", "These"),
                new MenuItem("assets/orange.png", "Are"),
                new MenuItem("assets/yellow.png", "All"),
                new MenuItem("assets/green.png", "Song (to play)"),
                new MenuItem("assets/blue.png", "placeholders")
        ));
        innitMenu(slashModeOptions);
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
