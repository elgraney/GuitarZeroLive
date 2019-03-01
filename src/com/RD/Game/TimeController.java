package com.RD.Game;

import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Matthew 2 on 26/02/2019.
 */
public class TimeController{
    private Model model;
    private Timer timer;

    public TimeController( Model model ) {
        this.model = model;
        this.timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                //some method that happens each tick?
                model.doTick();
            }
        }, 0, Constants.INTERVAL );
    }
}
