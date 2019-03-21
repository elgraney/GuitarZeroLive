package com.RD.Game;

import java.awt.*;
import java.util.ArrayList;

import com.RD.GUI.Guitar;
import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SelectMode;
import javafx.util.Pair;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


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
    static final int DELAY = 150;
    private Model model;
    public PlayGuitar( Model model ){
        this.model = model;
    }

    public void pollForever(Controller ctrl) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while( true) {
            if (ctrl.poll()) {
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
                    vals[ i ] = cmps[ i ].getPollData();
                }
                if (model.getState() == Model.InputState.NORMAL) {
                    if ( vals[15] != 0 || vals [13] != 0){
                        String notes = "";
                        if (vals[0] != 0 ){
                            notes += "1";
                        }
                        if (vals[4] != 0 ){
                            notes += "2";
                        }if (vals[5] != 0 ){
                            notes += "3";
                        }if (vals[1] != 0 ){
                            notes += "4";
                        }if (vals[2] != 0 ){
                            notes += "5";
                        }if (vals[3] != 0 ){
                            notes += "6";
                        }
                        playNote(notes);
                    }


                    for (int i = 0; i < cmps.length; i = i + 1) { /* display */
                        float val = vals[i];

                        if (val == 1 || val == -1) {
                            switch (i) {
                                case 0:
                                case 4:
                                case 5:
                                    System.out.println("White note  " + i + "   pressed");
                                    break;
                                case 1:
                                case 2:
                                case 3:
                                    System.out.println("Black note  " + i + "   pressed");
                                    break;
                                case 8:
                                    System.out.println("Select/ Hero Power button pressed");
                                    break;
                                case 10:
                                    System.out.println("Escape button pressed");

                                    break;
                                case 12:
                                    System.out.println("On/off button pressed");
                                    break;
                                case 16:
                                    if (val == 1) {
                                        System.out.println("Strum down");

                                    } else {
                                        System.out.println("Strum up");

                                    }
                                    break;
                                case 17:
                                    System.out.println("Whammy bar pressed");
                                    break;
                            }

                            if (vals[13] == 0.125 || vals[13] == 0.25 || vals[13] == 0.375 || vals[13] == 0.5) {
                                System.out.println("MenuGuitar Strum up");
                                // 0.25 up
                            } else if (vals[13] == 0.625 || vals[13] == 0.75 || vals[13] == 0.875 || vals[13] == 1.0) {
                                System.out.println("MenuGuitar Strum down");
                                // call function for strummming
                                // 0.75 down
                            }
                        }
                    }
                }
                else if (model.getState() == Model.InputState.ZERO_POWER){

                }
            }

            try { /* delay */
                Thread.sleep( DELAY );
            } catch ( Exception exn ) {
                System.out.println( exn ); System.exit( 1 );
            }
        }
    }

    private void playNote(String notes) {
        // find out what notes should be played at this time and compare to note played 12
        // either call hitNote or missNote
        ArrayList<Pair<Integer, Integer>> highwayNotes = new ArrayList<>(model.getHighwayNotes());
        for (Pair<Integer, Integer> note : highwayNotes) {
            if (note.getKey() > model.getTime() - model.getTickPerSecond() * 0.1 && note.getKey() < model.getTime() + model.getTickPerSecond() * 0.1) {
                if (notes.contains(Integer.toString(note.getValue()))) {
                    model.hitNote(note);
                    return;
                }
            }
        }
        model.missNote();
        //in future put sound in own class using observer pattern
    }
}
