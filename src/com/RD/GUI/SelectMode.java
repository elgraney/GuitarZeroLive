package com.RD.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew on 19/02/2019.
 * Edited by Joe on 20/02/2019
 * Content by Joe
 */
public class SelectMode extends ModeTemplate {
    public SelectMode(JFrame frame, SetUpGUI base) {
        super(frame, base);

        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList(
                new SongMenuItem("assets/AnotherOneBitesTheDust.png", "Another One Bites The Dust", null, null),
                new SongMenuItem("assets/Layla.png", "Layla", null, null),
                new SongMenuItem("assets/LucyInTheSkyWithDiamonds.png", "Lucy In The Sky With Diamonds", null, null),
                new SongMenuItem("assets/MoneyForNothing.png", "Money For Nothing", null, null),
                new SongMenuItem("assets/SmokeOnTheWater.png", "Smoke On The Water", null, null),
                new SongMenuItem("assets/SweetChildOMine.png", "Sweet Child O' Mine", null, null)
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
