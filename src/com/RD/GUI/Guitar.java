package com.RD.GUI;

import java.awt.*;

import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SelectMode;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/*
 * Class which uses input from the real guitar controller to be incorporated into the game
 *
 * @Sophia Wallgren
 * @ edited March 7th
 *
 *
 */

public class Guitar {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int DELAY = 150;
    private ModeTemplate template;

    public Guitar(ModeTemplate template) {
        this.template = template;
    }

    public void setTemplate(ModeTemplate template){
        this.template = template;
    }
    
    public void pollForever( Controller ctrl ) {
        /**
         * method that takes parameters of a controller which is the guitar
         * and listens for changes in values
         *
         */
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while( true) {

            if (ctrl.poll()) {

                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
                    vals[ i ] = cmps[ i ].getPollData();
                }

                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* displays */
                    float val = vals[ i ];

                     if ( val == 1.0 || val == -1.0 ) {
                        if( i==0 || i == 4 || i == 5) {
                            System.out.println("White note  " + i + "   pressed");
                            //Playmode.function();
                        }

                        if( i==1 || i == 2 || i == 3) {
                            System.out.println("Black note  " + i + "   pressed");
                        }

                        if (i==8) {
                            System.out.println("Select/ Hero Power button pressed");
                            // call hero power button function
                            //SelectMode.onSelect();
                        }

                        if (i==9){
                            System.out.println("Button 9 presssed");
                        }

                        if (i==10){
                            System.out.println("Escape button pressed");
                            template.onEscape();
                        }

                        if (i ==12) {
                            System.out.println("On/off button pressed");
                        }

                        if(vals[16] == 1){
                            System.out.println("Strum down");
                            template.right();

                        }

                        else if(vals[16] == -1){
                            System.out.println("Strum up");
                            template.left();
                        }

                        else if (vals[17]>0.1) {
                            System.out.println("Whammy bar pressed");
                        }

                        
                    } else {
                        if (vals[13] == 0.125 || vals[13] == 0.25 || vals[13] == 0.375 || vals[13] == 0.5) {
                            System.out.println("Guitar Strum up");
                            // call function for strummming
                            template.left();
                        }
                        if (vals[13] == 0.625 || vals[13] == 0.75 || vals[13] == 0.875 || vals[13] == 1.0) {
                            System.out.println("Guitar Strum down");
                            // call function for strummming
                            template.right();
                        }
                    }
                }
            }
           
            try { /* delay */
                Thread.sleep( DELAY );
            } catch ( Exception exn ) {
                exn.printStackTrace();
                System.exit( 1 );
            }
        }   
    }
    
    public void run() {
        /**
         * Method used to run pollForever
         */
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();
        
        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( GUITAR_HERO ) ) {

                Thread thread = new Thread("New Thread") {
                    public void run() {
                        pollForever(ctrl);
                    }
                };
            }
            else {
                System.out.println( " controller not found" );
                System.exit( 1 );

            }
        }
    }
}
