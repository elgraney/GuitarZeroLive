package com.RD.GUI;

import java.awt.*;

import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SelectMode;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/*
 * Menu guitar test
 *
 * @Sophia Wallgren
 * @ edited March 7th
 * Edited by Matthew Crane

 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac PlasicGuitar.java
 *   $ java -Djava.library.path=. PlasticGuitar
 */


public class MenuGuitar extends Guitar {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int DELAY = 150;
    private ModeTemplate template;

    public MenuGuitar(ModeTemplate template){
        this.template = template;
    }

    public void setTemplate(ModeTemplate template) {
        this.template = template;
    }

    public void pollForever(Controller ctrl ) throws NullPointerException {
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
                                template.onSelect();
                                break;
                            case 10:
                                System.out.println("Escape button pressed");
                                template.onEscape();
                                disconnect();
                                break;
                            case 12:
                                System.out.println("On/off button pressed");
                                break;
                            case 16:
                                if (val == 1) {
                                    System.out.println("Strum down");
                                    template.right();
                                }
                                else {
                                    System.out.println("Strum up");
                                    template.left();
                                }
                                checkTemplate();
                                break;
                            case 17:
                                System.out.println("Whammy bar pressed");
                                break;
                        }
                    }
                    if (vals[13] == 0.125 || vals[13] == 0.25 || vals[13] == 0.375 || vals[13] == 0.5) {
                        System.out.println("MenuGuitar Strum up");
                        template.left();
                        break;
                        // 0.25 up
                    } else if (vals[13] == 0.625 || vals[13] == 0.75 || vals[13] == 0.875 || vals[13] == 1.0) {
                        System.out.println("MenuGuitar Strum down");
                        template.right();
                        break;
                        // call function for strummming
                        // 0.75 down
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

    public void disconnect(){
        this.template = null;
    }
    public void checkTemplate(){
        System.out.println(template.getClass().getName());
    }
}
