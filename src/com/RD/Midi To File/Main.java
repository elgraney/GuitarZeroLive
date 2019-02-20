import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.*;
import javax.swing.plaf.synth.SynthCheckBoxMenuItemUI;

/**
 * Display MIDI file.
 *
 * @author  David Wakeling
 * @version 1.00, January 2019.
 *
 * Extended By James To Create the Guitar Button Timings and Selection
 */
public class Main {

    // final static String FILE = "midifile.mid";
    final static String FILE = "MidiFile.mid";
    /**
     * MinMaxFrequency Written by James
       */

    public static List<Integer> MinMaxFrequency(Track trk){
        int Min = 128;
        int Max = 0;
        List<Integer> guitarlist = new ArrayList<>();
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
                    case 192:
                        if (dat1 > 24 && dat1 < 33) {
                            guitarlist.add(chan);
                        }
                        break;
                    case 144:
                        if (guitarlist.contains(chan)){
                            if (dat1>Max){
                                Max=dat1;
                            }
                            if(dat1<Min){
                                Min=dat1;
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
     return MinMax;
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
     * Returns the name of nth note.
     *
     * @param n the note number
     * @return  the note name
     */
    public static String noteName( int n ) {
        final String[] NAMES =
                { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        final String[] BUTTONS =
                { "   1", "   2", "   3", "   4", "   5", "   6"};
        final int octave = (n / 12) - 1;
        final int note   = n % 12;
        int button = 0;
        Sequence seq = null;
        try {
            seq = MidiSystem.getSequence( new File( FILE ) );
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Track[] trks = seq.getTracks();
        int Min = MinMaxFrequency(trks[0]).get(0);
        int Max = MinMaxFrequency(trks[0]).get(1);
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

        //return NAMES[ note ] + octave + BUTTONS[ button ];
        return BUTTONS[ button ];
    }

    /**
     * Display a MIDI track.
     * Edited By James
     */
    public static void displayTrack( Track trk ){
        File file = new File ("C:/Users/840/Desktop/file.txt");
        PrintWriter printWriter = null;
        List<Integer> guitarlist = new ArrayList<>();
        try {
            printWriter = new PrintWriter("file.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        printWriter.println (FILE.toString());

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
                    case 192:
                        printWriter.println("@" + tick + ", " + "Channel " + chan + ", " + "Program change: " + instrumentName(dat1));
                        if (dat1 > 24 && dat1 < 33) {
                            guitarlist.add(chan);
                        }
                        printWriter.println("" + guitarlist + dat1);
                        break;
                    case 144:
                        if (guitarlist.contains(chan)){
                        printWriter.println("@" + tick + ", " + "Channel " + chan + ", " + "Note on:  " + noteName(dat1));
                            }
                        break;
                    case 128:
                        if(guitarlist.contains(chan)) {
                            printWriter.println("@" + tick + ", " + "Channel " + chan + ", " + "Note off: " + noteName(dat1));
                        }
                        break;
                    default:
                        break;
                }


            }

        }
        printWriter.close();
    }


    /**
     * Display a MIDI sequence.
     */
    public static void displaySequence( Sequence seq ) throws IOException {
        Track[] trks = seq.getTracks();
        for ( int i = 0; i < trks.length; i++ ) {
            displayTrack( trks[ i ] );

        }
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

        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
