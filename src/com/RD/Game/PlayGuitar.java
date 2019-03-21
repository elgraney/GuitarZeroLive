package com.RD.Game;

import java.util.ArrayList;

import com.RD.GUI.Guitar;

import javafx.util.Pair;
import net.java.games.input.Component;
import net.java.games.input.Controller;


/*
 * Play Guitar class
 *
 * @Sophia Wallgren
 * @ edited March 7th

 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac PlasicGuitar.java
 *   $ java -Djava.library.path=. PlasticGuitar
 */


public class PlayGuitar extends Guitar {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int DELAY = 50;
    private Model model;
    public PlayGuitar( Model model ){
        this.model = model;
    }

    public void pollForever(Controller ctrl) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while (true) {
            if (ctrl.poll()) {
                for (int i = 0; i < cmps.length; i = i + 1) { /* store */
                    vals[i] = cmps[i].getPollData();
                }
                if (model.getState() == Model.InputState.NORMAL) {
                    System.out.println(vals[15]);
                    System.out.println(vals[13]);
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
                    } else if (model.getState() == Model.InputState.ZERO_POWER) {

                    }
                }
            }

            try { /* delay */
                Thread.sleep(DELAY);
            } catch (Exception exn) {
                System.out.println(exn);
                System.exit(1);
            }
        }
    }

    private void playNote(String notes) {
        // find out what notes should be played at this time and compare to note played 12
        // either call hitNote or missNote
        ArrayList<Pair<Integer, Integer>> highwayNotes = new ArrayList<>(model.getHighwayNotes());
        for (Pair<Integer, Integer> note : highwayNotes) {
            if (note.getKey() > model.getTime() - model.getTickPerSecond() * 0.3 && note.getKey() < model.getTime() + model.getTickPerSecond() * 0.1) {
                if (notes.contains(Integer.toString(note.getValue()))) {
                    model.hitNote(note);
                    return;
                }
            }
        }


        //in future put sound in own class using observer pattern
    }
}
