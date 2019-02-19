package com.RD;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/*
 * Plastic guitar test (Sony PS3).
 *
 * @author SW
 * @version
 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac PlasicGuitar.java
 *   $ java -Djava.library.path=. PlasticGuitar
 */


public class Controller {

    public static void pollForever( Controller ctrl ) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];

        for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
            vals[ i ] = cmps[ i ].getPollData();
        }

        // fret buttons 6 of them
        //for button 0 to 5 each
        // if i has val 0, button not pressed
        // if i has val 1, button is pressed model.note


        float btn10 = vals[10];
        float btn16 = vals[16];

        if ( btn16 == -1 || btn16 == 1) {
            // call method strum in model class
        }

        if ( btn10 == 1 ) {
            // call escape method in model class
        }


    }
}