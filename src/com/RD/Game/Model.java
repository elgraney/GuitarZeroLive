package com.RD.Game;

import javafx.util.Pair;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import static com.RD.Game.Model.InputState.NORMAL;
import static javafx.scene.input.KeyCode.M;
import static javafx.scene.input.KeyCode.S;

/**
 * Created by Matthew on 26/02/2019.
 */

public class Model {
    //stores data only
    //must not depend on controller or view

    private String notesPath;
    private String midiPath;
    private JFrame frame;
    private InputState state;

    private int time = 0;

    private ArrayList<Pair<Integer, Integer>> originalFutureNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> futureNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> highwayNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> playNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> passedNotes = new ArrayList<>();

    public InputState getState() {
        return state;
    }

    public enum InputState{
        NORMAL, ZERO_POWER, PAUSED, DISABLED

    }

    public void setState(InputState state) {
        this.state = state;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Model(JFrame frame, String midiFilePath, String notesFilePath){
        this.frame = frame;
        midiPath = midiFilePath;

        readNotes();
        //begin won't be here in the future
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

    private void readNotes(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "Midi/test.txt"));
            String line = reader.readLine();
            line = reader.readLine();//skip first line
            while (line != null) {
                System.out.println(line);
                String[] parts = line.split(", ");
                futureNotes.add(new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                originalFutureNotes.add(new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void begin() throws MidiUnavailableException, IOException, InvalidMidiDataException {
        //REMEMBER implement error handling for these errors ^
        state = NORMAL;
        Sequencer sequencer = MidiSystem.getSequencer();

        sequencer.open();
        sequencer.setSequence(MidiSystem.getSequence( new File("Midi/Undertale_-_Spear_Of_Justice.mid")));

        float tempo =  sequencer.getTempoInBPM();
        //for some reason the tempo is about half what it should be?
        //sequencer.setTempoInBPM(150);

        sequencer.start();

        System.out.println("tempo");
        System.out.println(tempo);
        System.out.println(sequencer.getTempoInBPM());
        //do notes file
    }

    public void hitNote(){

    }
    //or
    public void missNote(){

    }
    //i guess i never missNote, huh

    public void doTick(){
        int interval = 1; //like the time for each tick
        time += interval;

        if (futureNotes.size()>0) {
            Pair<Integer, Integer> note = futureNotes.get(0);
            while (note.getKey() < time + 3000) {
                highwayNotes.add(note);
                futureNotes.remove(0);
                if (futureNotes.size()>0) {
                    note = futureNotes.get(0);
                }
                else{
                    break;
                }
            }
        }
        if (highwayNotes.size()>0){
            Pair<Integer, Integer> note = highwayNotes.get(0);
            while(note.getKey() < time + 200){
                playNotes.add(note);
                highwayNotes.remove(0);
                if (highwayNotes.size()>0) {
                    note = highwayNotes.get(0);
                }
                else{
                    break;
                }
            }
        }
        if (playNotes.size()>0) {
            Pair<Integer, Integer> note = playNotes.get(0);
            while (note.getKey() < time) {
                passedNotes.add(note);

                playNotes.remove(0);

                if (playNotes.size()>0) {
                    note = playNotes.get(0);
                }
                else{
                    break;
                }
            }
        }
    }
}
