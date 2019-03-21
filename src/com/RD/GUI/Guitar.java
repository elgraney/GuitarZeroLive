package com.RD.GUI;

import java.awt.*;

import com.RD.GUI.ModeTemplate;
import com.RD.GUI.SelectMode;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


/*
 * Menu guitar test
*/


public class Guitar {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int LOADING_TIME = 100;

    public void pollForever(Controller ctrl ) throws NullPointerException {
    }

    public void run() {
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();

        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( GUITAR_HERO ) ) {


                Thread thread = new Thread("New Thread") {
                    public void run(){
                        try {
                            try {
                                sleep(LOADING_TIME);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pollForever(ctrl);
                        }
                        catch (NullPointerException e){
                            System.out.println("loading new menu");
                            //continue, as its not a serious problem
                        }
                    }
                };

                thread.start();
                System.out.println(thread.getName());
                break;
            }
            else {
                System.out.println( " controller not found" );
            }
        }
    }
}
