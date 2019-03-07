package com.RD.GUI;

import java.awt.*;

import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SelectMode;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/*
 * Plastic guitar test
 *
 * @Sophia Wallgren
 * @ edited March 7th

 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac PlasicGuitar.java
 *   $ java -Djava.library.path=. PlasticGuitar
 */


public class Guitar {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int DELAY = 150;
    private ModeTemplate template;
    
    public static void pollForever( Controller ctrl ) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while( true) {
            if (ctrl.poll()) {
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
                    vals[ i ] = cmps[ i ].getPollData();
                }
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* display */
                    float val = vals[ i ];
                    Color col;
                    if ( val == 0.0 ) {
                        col = Color.WHITE;
                    } else if ( val == 1.0 || val == -1.0 ) {
                        col = Color.BLUE;
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
                            //ModeTemplate.onEscape();

                        }
                        if (i ==12) {
                            System.out.println("On/off button pressed");
                        }

                        if(vals[15] == 1){
                            System.out.println("Strum down");
                            //template.right();
                            //non static field cant be refernced from static context
                        }
                        else if(vals[15] == -1){
                            System.out.println("Strum up");
                            //ModeTemplate.left();
                            //doing a non static method call
                        }
                        else if (vals[17]>0.1) {
                        System.out.println("Whammy bar pressed");
                        }
                        else if(i == 16){
                            System.out.println("Guitar is standing up");
                        }
                        
                    } else {
                        col = Color.YELLOW;
                        if (vals[13] == 0.125 || vals[13] == 0.25 || vals[13] == 0.375 || vals[13] == 0.5) {
                            System.out.println("Guitar Strum up");
                            // call function for strummming
                        }
                        if (vals[13] == 0.625 || vals[13] == 0.75 || vals[13] == 0.875 || vals[13] == 1.0) {
                            System.out.println("Guitar Strum down");
                            // call function for strummming
                        }
                    }
                }
            }
           
            try { /* delay */
                Thread.sleep( DELAY );
            } catch ( Exception exn ) {
                System.out.println( exn ); System.exit( 1 );
            }
        }   
    }
    
    public static void run() {
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();
        
        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( GUITAR_HERO ) ) {
                pollForever( ctrl );
            }
        }
        
        System.out.println( " controller not found" );
        System.exit( 1 );
    }
}
