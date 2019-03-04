package com.RD.Game;

import javafx.util.Pair;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;

import static com.RD.Game.Model.InputState.NORMAL;

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
    private ArrayList<Pair<Integer, Integer>> passedNotes = new ArrayList<>();

    private int streak;
    private int score;
    private int multiplier;

    public int getTime() {
        return time;
    }

    public int getStreak() {
        return streak;
    }

    public ArrayList<Pair<Integer, Integer>> getHighwayNotes() {
        return highwayNotes;
    }

    private PropertyChangeSupport support;
    public int getScore() {
        return score;
    }

    public int getMultiplier() {
        return multiplier;
    }

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
        support = new PropertyChangeSupport( this );
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

    public void addPropertyChangeListener( PropertyChangeListener pcl ) {
        support.addPropertyChangeListener( pcl );
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
        sequencer.setSequence(MidiSystem.getSequence( new File("Midi/Bohemian_Rhapsody.mid")));

        float tempo =  sequencer.getTempoInBPM();

        sequencer.start();

        System.out.println("tempo");
        System.out.println(tempo);
        System.out.println(sequencer.getTempoInBPM());
        //do notes file
    }

    private void incrementStreak(){
        streak +=1;
    }
    private void resetStreak(){
        streak = 0;
    }
    private void incrementScore(){
        score += multiplier; //assume notes are worth 1 by default
    }
    private void calculateMultiplier(){
        multiplier = 2^(streak / 10);
    }

    public void hitNote(){
        incrementStreak();
        calculateMultiplier();
        incrementScore();
    }
    //or
    public void missNote(){
        resetStreak();
    }
    //i guess i never missNote, huh

    public void doTick(){
        int interval = 1; //like the time for each tick
        time += interval;

        if (highwayNotes.size()>0){
            Pair<Integer, Integer> note = highwayNotes.get(0);
            while(note.getKey() < time - 200){
                for(Pair<Integer, Integer> notez : highwayNotes){
                    System.out.println("item");
                    System.out.println(notez.getKey());
                }
                System.out.println("add to play");
                passedNotes.add(note);
                missNote();
                System.out.println("before");
                System.out.println(highwayNotes.size());
                highwayNotes.remove(0);
                System.out.println("after");
                System.out.println(highwayNotes.size());
                if (highwayNotes.size()>0) {
                    System.out.println("Repeat");
                    note = highwayNotes.get(0);
                }
                else{
                    break;
                }
            }
        }
        if (futureNotes.size()>0) {
            Pair<Integer, Integer> note = futureNotes.get(0);
            while (note.getKey() < time + 3000) {
                System.out.println("add to highway");
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

        if(highwayNotes.size() > 0) {
            support.firePropertyChange(null, null, null);
        }

    }
}
