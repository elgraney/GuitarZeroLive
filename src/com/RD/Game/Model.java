package com.RD.Game;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.io.*;

/**
 * Created by Matthew 2 on 26/02/2019.
 */
public class Model {
    //stores data only
    //must not depend on controller or view

    private String notesPath;
    private String midiPath;
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public Model(JFrame frame, String midiFilePath, String notesFilePath){
        this.frame = frame;
        midiPath = midiFilePath;


        //begin won't be here
        try {
            begin();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            //DO SOMETHING
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            //DO SOMETHING ELSE
        }


    }

    public void begin() throws MidiUnavailableException, IOException, InvalidMidiDataException {
        //REMEMBER implement error handling for these errors ^
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        InputStream is = new BufferedInputStream(new FileInputStream(new File("Midi/Undertale_-_Megalovania.mid")));
        sequencer.setSequence(is);
        float tempo =  sequencer.getTempoInBPM();
        //for some reason the tempo is about half what it should be?
        sequencer.setTempoInBPM(240);
        System.out.println(sequencer.getTempoInBPM());
        sequencer.start();


        //do notes file
    }
}
