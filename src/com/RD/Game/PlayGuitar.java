package com.RD.Game;

import java.util.ArrayList;

import com.RD.GUI.ErrorWindow;
import com.RD.GUI.Guitar;

import javafx.util.Pair;
import net.java.games.input.Component;
import net.java.games.input.Controller;


/**
 * Controls gameplay with plastic guitar input
 * @ Sophia Wallgren
 * Edited by Matthew Crane
 */

public class PlayGuitar extends Guitar {
    static final int DELAY = 50;
    private Model model;
    public PlayGuitar( Model model ){
        this.model = model;
    }

    /**
     * Polls for changes in button values to indicate which notes are being pressed
     * @param ctrl
     */
    public void pollForever(Controller ctrl) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while (true) {
            if (ctrl.poll()) {
                for (int i = 0; i < cmps.length; i = i + 1) { /* store */
                    vals[i] = cmps[i].getPollData();
                }
                if (model.getState() == Model.InputState.NORMAL) {
                    if (vals[13] != 0) {
                        String notes = "";
                        if (vals[0] != 0) {
                            notes += "4";
                        }
                        if (vals[4] != 0) {
                            notes += "5";
                        }
                        if (vals[5] != 0) {
                            notes += "6";
                        }
                        if (vals[1] != 0) {
                            notes += "1";
                        }
                        if (vals[2] != 0) {
                            notes += "2";
                        }
                        if (vals[3] != 0) {
                            notes += "3";
                        }
                        playNote(notes);
                    }
                } else if (model.getState() == Model.InputState.ZERO_POWER) {
                    System.out.println("yes");
                    if (vals[1] == 0 && vals[2] == 0 && vals[3] == 0
                            && vals[4] == 0 && vals[5] == 0 && vals[6] == 0
                            && vals[13] == 0) { //excludes frets and strumming
                        System.out.println("yes");
                        if (vals[8] != 0 || vals[17] != 0) {//Hero button or whammy bar
                            System.out.println("got it");
                            playNote("123456");
                        }
                    }
                }
            }


            try { /* delay */
                Thread.sleep(DELAY);
            } catch (Exception exn) {
                new ErrorWindow("Guitar polling failed! Exiting...");
                System.exit(1);
            }
        }
    }

    /**
     * Finds out what notes should be played at this time and compare to note played
     * and either call hitNote or missNote
     * @param notes
     */
    private void playNote(String notes) {
        ArrayList<Pair<Integer, Integer>> highwayNotes = new ArrayList<>(model.getHighwayNotes());
        for (Pair<Integer, Integer> note : highwayNotes) {
            if (note.getKey() > model.getTime() - model.getTickPerSecond() * Constants.LENIENCY && note.getKey() < model.getTime() + model.getTickPerSecond() * Constants.LENIENCY) {
                if (notes.contains(Integer.toString(note.getValue()))) {
                    model.hitNote(note);
                    return;
                }
            }
        }
    }
}
