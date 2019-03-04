package com.RD.Game;

import javax.swing.*;

/**
 * Created by Matthew 2 on 04/03/2019.
 */
public class NoteObject {
    private int time;
    private int channel;
    private JPanel panel;

    public NoteObject(int time,  int channel, JPanel panel) {
        this.time = time;

        this.channel = channel;
        this.panel = panel;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
