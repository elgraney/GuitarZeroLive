package com.RD.GUI;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew 2 on 18/02/2019.
 */
public class SlashMode extends ModeTemplate {
    public SlashMode(JFrame frame, SetUpGUI base) {
        super(frame, base);

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
    public void onSelect(){
        MenuItem selected = getViewOptions()[2];
        switch (selected.getTitle()){
            case "Exit":
                System.out.println("Exiting");
                frame.dispose();
                break;

            case "Play":
                System.out.println("Play- NOT IMPLEMENTED");
                break;

            case "Select":
                System.out.println("Goto Select mode");
                tearDown();
                ModeTemplate selectMode = new SelectMode(frame, base);
                frame.addKeyListener(new GUIControls(frame, selectMode));
                break;

            case "Store":
                System.out.println("Goto store mode");
                tearDown();
                ModeTemplate storeMode = new StoreMode(frame, base);
                frame.addKeyListener(new GUIControls(frame, storeMode));
                break;

            case "Tutorial":
                System.out.println("Tutorial - NOT IMPLEMENTED ");
        }
        frame.revalidate();
    }
}