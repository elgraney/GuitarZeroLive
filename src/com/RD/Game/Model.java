package com.RD.Game;

import javafx.util.Pair;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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
import static javafx.scene.input.KeyCode.L;
import static javax.sound.midi.Sequence.PPQ;

/**
 * Created by Matthew on 26/02/2019.
 */

public class Model {
    /**
    Model stores data only
    Must not depend on controller or view
    */
    private String notesPath;
    private String midiPath;
    private JFrame frame;
    private InputState state;
    private Sequencer sequencer;

    private ArrayList<Pair<Integer, Integer>> originalFutureNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> futureNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> highwayNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> passedNotes = new ArrayList<>();
    private PropertyChangeSupport support;
    private int streak =0;
    private int score;
    private int multiplier = 1;
    private int currency;
    private int currencyCounter = 0;
    private long time;
    private double tickPerSecond;

    /**
     * Define the state the of the play mode, to determine how inputs are handled
     */
    public enum InputState{
        NORMAL, ZERO_POWER, PAUSED, DISABLED
    }


    public long getTime() {
        return time;
    }

    public int getStreak() {
        return streak;
    }

    public ArrayList<Pair<Integer, Integer>> getHighwayNotes() {
        return highwayNotes;
    }

    public int getScore() {
        return score;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public InputState getState() {
        return state;
    }

    public void setState(InputState state) { this.state = state; }

    public JFrame getFrame() { return frame; }

    public double getTickPerSecond() {
        return tickPerSecond;
    }

    /**
     * Get currency from storage and set value in model
     */
    private void importCurrency() {
        //to be filled
    }



    /**
     * Set up UI and get MIDI and notes ready
     */
    public Model(JFrame frame, String midiFilePath, String notesFilePath){
        support = new PropertyChangeSupport( this );
        this.frame = frame;
        midiPath = midiFilePath;
        notesPath = notesFilePath;
        readNotes();

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

    /**
     * Set the up listener so that the view can react
     */
    public void addPropertyChangeListener( PropertyChangeListener pcl ) {
        support.addPropertyChangeListener( pcl );
    }

    /**
     * Load every note into an array and store with their timestamp of when to play them
     */
    private void readNotes(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    notesPath));
            String line = reader.readLine();
            line = reader.readLine();//skip first line (it doesn't contain useful information)
            while (line != null) {
                System.out.println(line);
                String[] parts = line.split(",");
                futureNotes.add(new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                originalFutureNotes.add(new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void calculateTPS(){
        double ticks = sequencer.getTickLength();
        double microseconds = sequencer.getMicrosecondLength();
        double seconds = microseconds / 1000000;
        tickPerSecond = ticks/seconds;
        System.out.println("TPS");
        System.out.println(tickPerSecond);
    }

    /**
     * play the midi file
     */
    public void begin() throws MidiUnavailableException, IOException, InvalidMidiDataException {
        //NOTE: REMEMBER to implement error handling for these errors ^
        state = NORMAL;
        sequencer = MidiSystem.getSequencer();

        sequencer.open();
        sequencer.setSequence(MidiSystem.getSequence( new File(midiPath)));

        sequencer.start();
        calculateTPS();
    }

    private void incrementStreak(){
        streak +=1;
    }

    private void resetStreak(){
        streak = 0;
    }

    /**
     * Calculate new score and also work out the currency
     */
    private void incrementScore() {
        score += multiplier; //assume notes are worth 1 by default
        currencyCounter += multiplier;
        if (currencyCounter>=500){
            currency+=1;
            if (currency>5){
                currency = 5;
            }
            currencyCounter -=500;
        }
    }

    private void calculateMultiplier(){
        int power = (int) ((double) streak / 10);
        multiplier = (int) Math.pow(2, power);
    }

    /**
     * Trigger a refresh in the View, and update various stats
     */
    public void hitNote(Pair<Integer, Integer> note){
        playSound("assets/BruhSoundEffect2.wav");
        highwayNotes.remove(note);
        passedNotes.add(note);
        support.firePropertyChange(null, null, null);
        incrementStreak();
        calculateMultiplier();
        incrementScore();
    }

    /**
     * Trigger a refresh in view and reset the streak
     */
    public void missNote(){
        playSound("assets/BruhSoundEffect2.wav");
        support.firePropertyChange(null, null, null);
        resetStreak();
    }

    private void playSound(String filename){
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        AudioStream as = null;
        try {
            as = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(as);
    }
    /**
     * Caused by the timer in the TimeController
     * Find and store the current tick in the song
     * Work out whether a note is on the highway, yet to play, or passed
     * Trigger the View to show any of these changes
     */
    public void doTick(){
        time = sequencer.getTickPosition();

        if (highwayNotes.size()>0){
            Pair<Integer, Integer> note = highwayNotes.get(0);
            while(note.getKey() < time - tickPerSecond *0.2){
                passedNotes.add(note);
                highwayNotes.remove(0);

                missNote();
                if (highwayNotes.size()>0) {
                    note = highwayNotes.get(0);
                }
                else{
                    support.firePropertyChange(null, null, null);
                    break;
                }
            }
        }
        if (futureNotes.size()>0) {
            Pair<Integer, Integer> note = futureNotes.get(0);
            while (note.getKey() < time + tickPerSecond * 3) {
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
