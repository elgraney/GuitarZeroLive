package com.RD.GUI;

import com.RD.Game.GuitarController;
import com.RD.Game.Model;
import com.RD.Game.TimeController;
import com.RD.Game.View;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew on 18/02/2019.
 * All content by Matthew
 */
public class SlashMode extends ModeTemplate {
    public SlashMode(JFrame frame, SetUpGUI base) {
        super(frame, base);
        //initializing carousel with fixed menu options
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

    //controls action taken when select is pressed for each menu item
    public void onSelect(){
        MenuItem selected = getViewOptions()[2];
        switch (selected.getTitle()){
            case "Exit":
                System.out.println("Exiting");
                frame.dispose();
                break;

            case "Play":
                System.out.println("Play- NOT IMPLEMENTED");
                tearDown();
                Model model = new Model(frame, "MidiFile.mid", "Placeholder");
                GuitarController guitarController =  new GuitarController(model);
                base.setListener(guitarController);
                TimeController timeController = new TimeController(model);
                View view = new View(model);
                break;

            case "Select":
                System.out.println("Goto Select mode");
                tearDown();
                ModeTemplate selectMode = new SelectMode(frame, base);
                base.setListener(new GUIControls(frame, selectMode));
                break;

            case "Store":
                System.out.println("Goto store mode");
                tearDown();
                ModeTemplate storeMode = new StoreMode(frame, base);
                base.setListener(new GUIControls(frame, storeMode));
                break;

            case "Tutorial":
                System.out.println("Tutorial - NOT IMPLEMENTED ");
        }
        frame.revalidate();
    }

    public void onEscape() {
        System.out.println("End Program");
        frame.dispose();
    }

}
