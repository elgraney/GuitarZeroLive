package com.RD.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew on 19/02/2019.
 * Edited by Joe 20/02/2019
 *
 */
public class StoreMode extends ModeTemplate {
    public StoreMode(JFrame frame, SetUpGUI base) {
        super(frame, base);

        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList(
                new MenuItem("assets/red.png", "These"),
                new MenuItem("assets/orange.png", "Are"),
                new MenuItem("assets/yellow.png", "All"),
                new MenuItem("assets/green.png", "Song (to buy)"),
                new MenuItem("assets/blue.png", "placeholders")
        ));
        innitMenu(slashModeOptions);
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEscape() {
        System.out.println("Go back to main");
        tearDown();
        ModeTemplate slashMode = new SlashMode(frame, base);
        base.setListener(new GUIControls(this, slashMode));
        frame.revalidate();
    }

}
