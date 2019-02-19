package com.RD.GUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew 2 on 18/02/2019.
 */
public class SlashMode extends ModeTemplate {
    public SlashMode(Frame frame) {
        super(frame);

        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList(
                new MenuItem("assets/Exit.png", "Exit"),
                new MenuItem("assets/Play.png", "Play"),
                new MenuItem("assets/Select.png", "Select"),
                new MenuItem("assets/Store.png", "Store"),
                new MenuItem("assets/tutorial.png", "Tutorial")
        ));
        innitMenu(slashModeOptions);
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
