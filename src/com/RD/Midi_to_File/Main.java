package com.RD.Midi_to_File;

import javax.sound.midi.MidiSystem;
import java.io.*;
import java.util.*;
import javax.sound.midi.*;
import javax.sound.midi.Sequence;


/**
 * Display MIDI file and add in Zero Power Mode.
 *
 * @author  David Wakeling
 * @version 1.00, January 2019.
 *
 * Extended By James To Create the Guitar Button Timings and Selection
 *
 * Zero Power mode added and general cleanup by Joe
 *
 * Duplicate removing by Joe
 */
public class Main {

    // final static String FILE = "midifile.mid";
    final static String FILE = "Midi/MIDIlovania.mid";
    final int MINIMUM_ZERO_POWER_NOTES_FREQUENCY = 10;
    /**
     * MinMaxFrequency Written by James
     * Returns the Lowest frequency and the highest frequency played by a guitar
     * It also returns the most frequently used guitar channel
       */

    public static List<Integer> MinMaxFrequency(Track trk){
        int Min = 128;
        int Max = 0;
        int guitarFirst = 24;
        int guitarLast = 33;

        List<Integer> guitarlist = new ArrayList<>();
        List<Integer> Channellist = new ArrayList<>();
        List<Integer> MinMax = new ArrayList<>();

        for ( int i = 0; i < trk.size(); i = i + 1 ) {

            MidiEvent evt = trk.get(i);
            MidiMessage msg = evt.getMessage();

            if (msg instanceof ShortMessage) {
                final ShortMessage smsg = (ShortMessage) msg;
                final int chan = smsg.getChannel();
                final int cmd = smsg.getCommand();
                final int dat1 = smsg.getData1();
                switch (cmd) {
                    case ShortMessage.PROGRAM_CHANGE:

                        //the values between 25 and 32 are all guitars
                        if (dat1 > guitarFirst && dat1 < guitarLast) {
                            guitarlist.add(chan);

                        }
                        break;
                    case ShortMessage.NOTE_ON:
                        if (guitarlist.contains(chan)) {
                            Channellist.add(chan);
                            if (dat1 > Max) {
                                Max = dat1;
                            }
                            if (dat1 < Min) {
                                Min = dat1;
                            }
                        }
                        break;

                    default:
                        break;
                }


            }
        }

            MinMax.add(Min);
            MinMax.add(Max);
            MinMax.add(mostFrequent(Channellist));


     return MinMax;
    }



    public static Integer mostFrequent(List<Integer> list) {

        if (list.isEmpty())
            return null;

        Map<Integer, Integer> counterMap = new HashMap<Integer, Integer>();
        Integer maxValue = 0;
        Integer mostFrequentValue = null;

        for(Integer valueAsKey : list) {
            Integer counter = counterMap.get(valueAsKey);
            counterMap.put(valueAsKey, counter == null ? 1 : counter + 1);
            counter = counterMap.get(valueAsKey);
            if (counter > maxValue) {
                maxValue = counter;
                mostFrequentValue = valueAsKey;
            }
        }
        return mostFrequentValue;
    }


    /**
     * Returns the name of nth instrument in the current MIDI soundbank.
     *
     * @param n the instrument number
     * @return  the instrument name
     */
    public static String instrumentName( int n ) {

        try {

            final Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            final Instrument[] instrs = synth.getAvailableInstruments();
            synth.close();
            return instrs[ n ].getName();

        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 ); return "";
        }
    }


    /**
     * Used to Return the name of nth note.
     * Now it returns the fret button that is to be pressed
     * @param n the note number
     * @param trk the current track
     * @return  the note name
     */
    public static String noteName( int n, Track trk) {

        final String[] NAMES =
                { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        final String[] BUTTONS =
                { "1", "2", "3", "4", "5", "6"};
        final int octave = (n / 12) - 1;
        final int note   = n % 12;
        int button = 0;
        Sequence seq = null;

        try {
            seq = MidiSystem.getSequence( new File( FILE ) );
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.out.println("invalidMidiData");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOexception with Midi get sequence");
        }

        int Min = MinMaxFrequency(trk).get(0);
        int Max = MinMaxFrequency(trk).get(1);
        int ivl = (Max - Min) /6;
        if(Min<n && n<Min + ivl){
            button = 0;
        }
        else if(Min+ivl<n && n<Min +(ivl*2)){
            button =1;
        }
        else if(Min+(ivl*2)<n && n<Min+(ivl*3)){
            button =2;
        }
        else if(Min+(ivl*3)<n && n<Min+(ivl*4)){
            button =3;
        }
        else if(Min+(ivl*4)<n && n<Min+(ivl*5)){
            button =4;
        }
        else if(Min+(ivl*5)<n){
            button =5;
        }

        return BUTTONS[ button ];
    }


    /**
     * Display a MIDI track.
     * Edited By James
     * writes into a file the notes to be played next to the ticks
     * @param trk the current track
     */

    public static void displayTrack( Track trk ){
        File file = new File ("notes/file.txt");
        PrintWriter printWriter = null;
        List<Integer> guitarlist = new ArrayList<>();
        try {
            printWriter = new PrintWriter("notes/file.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File is not Found Try a different file");
        }
        printWriter.println (FILE.toString());

        Sequence seq = null;

        try {
            seq = MidiSystem.getSequence( new File( FILE ) );
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.out.println("The Midi File is invalid. Pick a different File");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception");
        }

        final int MainPart = MinMaxFrequency(trk).get(2);
        for ( int i = 0; i < trk.size(); i = i + 1 ) {
            MidiEvent evt = trk.get(i);
            MidiMessage msg = evt.getMessage();
            if (msg instanceof ShortMessage) {

                final long tick = evt.getTick();
                final ShortMessage smsg = (ShortMessage) msg;
                final int chan = smsg.getChannel();
                final int cmd = smsg.getCommand();
                final int dat1 = smsg.getData1();

                switch (cmd) {
                    case ShortMessage.PROGRAM_CHANGE:

                          if(chan == MainPart){
                              guitarlist.add(chan);
                        }
                        break;
                    case ShortMessage.NOTE_ON:

                        if (chan == MainPart) {
                            printWriter.println("" + tick + "," + noteName(dat1, trk));
                        }

                    default:
                        break;
                }

            }

        }
        if(guitarlist.size() == 0){
            printWriter.println("Reject");
        }
        printWriter.close();
    }


    /**
     * Display a MIDI sequence.
     * The song isnt always on Track 0 which took a while to discover so this
     * makes sure it finds the track the song is on.
     */
    public static void displaySequence( Sequence seq ){

        Track[] trks = seq.getTracks();

        for ( int i = 0; i < trks.length; i++ ) {
            try {displayTrack( trks[ i ]);}
            catch(NullPointerException e){
                System.out.println("Track " + i + " Doesnt Exist" );
                continue;
            }

            File file = new File ("notes/file.txt");
            BufferedReader brTest = null;

            try {
                brTest = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace(); System.exit(1);
            }

            String text = null;

            try {
                text = brTest .readLine();
            } catch (IOException e) {
                e.printStackTrace(); System.exit(1);
            }

            System.out.println("Firstline is : " + text);
            if(text.contains(FILE)){

                return;
            }
        }
    }

    /**
     * Calculates the Zero Power Mode start and stop times for the notes file.
     * Content by Joe
     * @param MidiTxt
     * @throws IOException
     */

    public static void ZeroPowerModeCalculator(File MidiTxt) throws IOException {

        //Amount of concurrent notes required to be eligible for a zero power mode section
        final int MINIMUM_ZERO_POWER_NOTES_FREQUENCY = 5;

        BufferedReader notesFile = null;
        try {
            notesFile = new BufferedReader(new FileReader(MidiTxt));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("File is not found!");
            System.exit(0);
        }

        List<String> notesList = new ArrayList<String>();
        List<String> ticksList = new ArrayList<String>();
        List<String> zeroPowerList = new ArrayList<String>();
        String line;

        int n =0;
        String firstLine = notesFile.readLine();

        //Splitting the notes file to find the note being played.
        while((line=notesFile.readLine())!=null) {
            if (n!=0) {
                zeroPowerList.add(line);
                String[] parts = line.split(",");
                notesList.add(parts[1]);
                ticksList.add(parts[0]);
            }
            n++;

        }

        int currentNumber;
        int currentNumberCount = 1;

        //Creating a list of notes frequency before changing to another note.
        List<Integer> frequency = new ArrayList<>();
        for(int i = 0; i < notesList.size(); i++){
            currentNumber = Integer.parseInt(notesList.get(i));
            if(i == 0 || currentNumber == Integer.parseInt(notesList.get(i - 1))){
                currentNumberCount += 1;
            }else{
                frequency.add(currentNumberCount);
                currentNumberCount = 1;
            }
        }
        int maxFreq = Collections.max(frequency);

        //If multiple maximum frequencies of the same number, use the last one to occur.
        int lastIndex = frequency.lastIndexOf(maxFreq);

        int currentIndex = 0;
        int startIndexOfNotes = 0;

        for(int i = 0; i < notesList.size(); i++){
            currentNumber = Integer.parseInt(notesList.get(i));
            if(i == 0 || currentNumber == Integer.parseInt(notesList.get(i - 1))) {
                //do nothing, needed for i==0 and array out of bounds.
            }else{
                currentIndex += 1;
                if(currentIndex == lastIndex){
                    startIndexOfNotes = i;
                    break;
                }
            }
        }

        //Adding in a brief period of notes before and after the main zero power mode starts.
        if(maxFreq > MINIMUM_ZERO_POWER_NOTES_FREQUENCY) {
            zeroPowerList.add(startIndexOfNotes - 10, "START");
            zeroPowerList.add(startIndexOfNotes + maxFreq + 12, "END");
            System.out.println("Zero Power mode added");
        }else{
            System.out.println("Zero Power Mode not added, no high point.");
        }


        //Cleanup of file; removing duplicate / too close together notes.
        for(int i = 1; i < ticksList.size() - 1; i++){
            if(i + 1> ticksList.size()){
                break;
            }
            if(Integer.parseInt(ticksList.get(i + 1)) - Integer.parseInt(ticksList.get(i)) < 5 || Integer.parseInt(ticksList.get(i)) - Integer.parseInt(ticksList.get(i - 1)) < 5){
                ticksList.remove(i);
                zeroPowerList.remove(i);
            }
        }

        System.out.println("Duplicates Removed");


        notesFile.close();

        //Recreating the file with the new start and stop and cleanup.
        FileWriter writer = new FileWriter("notes\\file.txt");
        writer.write(firstLine + "\n");

        int size = zeroPowerList.size();

        for (int i=0;i<size;i++) {
            String str = zeroPowerList.get(i);
            writer.write(str);
            if(i < size-1) {
                writer.write("\n");
            }
        }

        writer.close();

    }

    /*
     * Main.
     *
     * @param argv the command line arguments
     */
    public static void main( String[] argv ) {
        try {
            Sequence seq = MidiSystem.getSequence( new File( FILE ) );
            displaySequence( seq );
            File file = new File ("notes\\file.txt");
            ZeroPowerModeCalculator(file);

        } catch ( Exception exn ) {

                System.out.println("getSequence of file not working");
            exn.printStackTrace(); System.exit( 1 );
        }
    }
}
