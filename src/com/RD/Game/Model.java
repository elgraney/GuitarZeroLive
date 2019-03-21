package com.RD.Game;

import com.RD.GUI.SetUpGUI;
import javafx.util.Pair;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.midi.*;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;

import static com.RD.Game.Model.InputState.NORMAL;
import static com.RD.Game.Model.InputState.ZERO_POWER;
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

    private ArrayList<Pair<Integer, Integer>> futureNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> highwayNotes = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> passedNotes = new ArrayList<>();
    private PropertyChangeSupport support;
    private int streak =0;
    private int score;
    private int multiplier = 1;
    private int currency = 1;
    private int currencyCounter = 0;
    private long time;
    private double tickPerSecond;
    private boolean contentChanged = false;
    private long zeroPowerOnTime;
    private long zeroPowerOffTime;

    private final int MAX_CURRENCY = 5;
    private final int HIGHWAY_TIME = 3;
    private final int SCORE_FOR_CURRENCY = 400;

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

    public int getCurrency() {
        return currency;
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
            System.out.println(e); System.exit(1);
        } catch (IOException e) {
            System.out.println(e); System.exit(1);
            //DO SOMETHING
        } catch (InvalidMidiDataException e) {
            System.out.println(e); System.exit(1);
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
                try {
                    String[] parts = line.split(",");
                    futureNotes.add(new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));}
                catch(NumberFormatException E){

                    if (line.contains( "START")){
                        zeroPowerOnTime = futureNotes.get(futureNotes.size()-1).getKey() + 1;
                    }
                    else if(line.contains("END")){
                        zeroPowerOffTime = futureNotes.get(futureNotes.size()-1).getKey() + 1;
                    }
                    else{
                        System.out.println("Critical error in notes files. Problem line:");
                        System.out.println(line);
                        System.exit(1);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e); System.exit(1);
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
        currency = CurrencyManager.readFile();

        state = NORMAL;
        sequencer = MidiSystem.getSequencer();

        sequencer.open();
        sequencer.setSequence(MidiSystem.getSequence( new File(midiPath)));

        sequencer.start();
        calculateTPS();
        sequencer.addMetaEventListener(
                new MetaEventListener() {
                    public void meta(MetaMessage event) {
                        if (event.getType() == 47) {
                            CurrencyManager.saveFile(currency);
                            support.firePropertyChange("end", null, null);
                        }
                    }
                });
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
        if (currencyCounter>=SCORE_FOR_CURRENCY){
            currency+=1;
            if (currency>MAX_CURRENCY){
                currency = MAX_CURRENCY;
            }
            currencyCounter -=SCORE_FOR_CURRENCY;
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
        contentChanged = true;
        playSound("assets/pop.wav");
        highwayNotes.remove(note);
        passedNotes.add(note);
        incrementStreak();
        calculateMultiplier();
        incrementScore();
    }

    /**
     * Trigger a refresh in view and reset the streak
     */
    public void missNote(){
        contentChanged = true;
        playSound("assets/BruhSoundEffect2.wav");
        resetStreak();
    }

    private void playSound(String filename){
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        } catch (FileNotFoundException exn) {
            System.out.println(exn); System.exit(1);
        }


        AudioStream as = null;
        try {
            as = new AudioStream(in);
        } catch (IOException exn) {
            System.out.println(exn); System.exit(1);
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

        time = sequencer.getTickPosition();
        if(time>zeroPowerOnTime && time<zeroPowerOffTime){
            state = ZERO_POWER;
        }
        else{
            state = NORMAL;
        }


        if (highwayNotes.size()>0){
            Pair<Integer, Integer> note = highwayNotes.get(0);
            while(note.getKey() < time - tickPerSecond *Constants.LENIENCY){
                passedNotes.add(note);
                highwayNotes.remove(0);

                missNote();
                if (highwayNotes.size()>0) {
                    note = highwayNotes.get(0);
                }
                else{
                    support.firePropertyChange(null, null, null);
                    contentChanged = false;
                    break;
                }
            }
        }
        if (futureNotes.size()>0) {
            Pair<Integer, Integer> note = futureNotes.get(0);
            while (note.getKey() < time + tickPerSecond * HIGHWAY_TIME) {
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
            contentChanged = false;
        }
        else{
            support.firePropertyChange(null, null, null);
        }
        if(contentChanged){
            System.out.println("contentChanged");
            support.firePropertyChange(null, null, null);
        }
        contentChanged = false;
    }

}
