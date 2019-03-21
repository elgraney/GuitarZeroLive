package com.RD.Game;

import javax.swing.*;

/**
 * Created by Matthew 2 on 04/03/2019.
 */
public class NoteObject {
    private int time;
    private int chanel;
    private JPanel panel;

    /**
     * Note object constructor.
     * @param time timing.
     * @param chanel the channel it is in.
     * @param panel the panel it appears.
     */
    public NoteObject(int time,  int chanel, JPanel panel) {
        this.time = time;

        this.chanel = chanel;
        this.panel = panel;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getChanel() {
        return chanel;
    }

    public void setChanel(int channel) {
        this.chanel = chanel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
