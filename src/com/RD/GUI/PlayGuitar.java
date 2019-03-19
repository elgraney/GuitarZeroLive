package com.RD.GUI;

import java.awt.*;

import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SelectMode;
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


public class PlayGuitar {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int DELAY = 150;
    private ModeTemplate template;

    public PlayGuitar(ModeTemplate template){
        this.template = template;
    }

    public void setTemplate(ModeTemplate template) {
        this.template = template;
    }

    public void pollForeverPlay(Controller ctrl ) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while( true) {
            if (ctrl.poll()) {
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
                    vals[ i ] = cmps[ i ].getPollData();
                }
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* display */
                    float val = vals[ i ];

                    if (val == 1 || val == -1) {
                        switch(i) {
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
                                template.onEscape();
                                break;
                            case 12:
                                System.out.println("On/off button pressed");
                                break;
                            case 15:
                                if (val == 1) {
                                    System.out.println("Strum down");
                                    template.right();
                                }
                                else {
                                    System.out.println("Strum up");
                                    template.left();
                                }
                                break;
                            case 17:
                                System.out.println("Whammy bar pressed");
                                break;
                        }

                        if (vals[13] == 0.125 || vals[13] == 0.25 || vals[13] == 0.375 || vals[13] == 0.5) {
                            System.out.println("MenuGuitar Strum up");
                            // 0.25 up
                        }
                        else if (vals[13] == 0.625 || vals[13] == 0.75 || vals[13] == 0.875 || vals[13] == 1.0) {
                            System.out.println("MenuGuitar Strum down");
                            // call function for strummming
                            // 0.75 down
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

    public void run() {
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();

        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( GUITAR_HERO ) ) {

                Thread thread = new Thread("New Thread") {
                    public void run(){
                        pollForeverPlay( ctrl );
                    }
                };

                thread.start();
                System.out.println(thread.getName());
            }
            else {
                System.out.println( " controller not found" );
            }
        }



    }
}
