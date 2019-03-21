package com.RD.GUI;

import com.RD.Game.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew on 18/02/2019.
 * All content by Matthew
 */
public class SlashMode extends ModeTemplate {

    Model model ;
    TimeController timeController;
    View view;

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

    /**
     * controls action taken when select is pressed for each menu item
     */
    public void onSelect(){
        MenuItem selected = getViewOptions()[2];
        switch (selected.getTitle()){
            case "Exit":
                frame.dispose();
                break;

            case "Play":
                tearDown();
                base.nullGuitar();
                String midiPath = base.getCurrentBundle().getMidiFilePath();
                String notesPath = base.getCurrentBundle().getNotesFilePath();
                model = new Model(frame, midiPath, notesPath);
                GuitarKeyController guitarKeyController =  new GuitarKeyController(model);
                base.setListener(guitarKeyController);
                timeController = new TimeController(model);
                view = new View(model, this);
                PlayGuitar playGuitar = new PlayGuitar(model);
                base.setGuitar(playGuitar);

                System.out.println("Brave, but foolish my old friend, you're impossibly outnumbered");

                break;

            case "Select":
                tearDown();
                ModeTemplate selectMode = new SelectMode(frame, base);
                base.setListener(new GUIControls(frame, selectMode));
                MenuGuitar selectGuitar = new MenuGuitar(selectMode);
                base.setGuitar(selectGuitar);
                break;

            case "Store":
                tearDown();
                ModeTemplate storeMode = new StoreMode(frame, base);
                base.setListener(new GUIControls(frame, storeMode));
                MenuGuitar storeGuitar = new MenuGuitar(storeMode);
                base.setGuitar(storeGuitar);
                break;

            case "Tutorial":
                System.out.println("Tutorial - NOT IMPLEMENTED ");
        }
        frame.revalidate();
    }

    public void onEscape() {
        System.out.println("End Program");
        frame.dispose();
        System.exit(1);
    }

    public void returnToMenu(){
        base.removeListener();
        model = null;
        view = null;
        timeController = null;

        ModeTemplate selectMode = new SlashMode(frame, base);
        base.setListener(new GUIControls(frame, selectMode));
        MenuGuitar menuGuitar = new MenuGuitar(selectMode);
        base.setGuitar(menuGuitar);
        frame.revalidate();
        frame.repaint();
    }

}
