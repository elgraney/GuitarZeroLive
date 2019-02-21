package SWPlasticGuitar;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;



/*
 * Plastic guitar test
 *
 * @SW
 * @version
 *
 *   $ CLASSPATH=jinput-2.0.9.jar:.
 *   $ export CLASSPATH
 *   $ javac PlasicGuitar.java
 *   $ java -Djava.library.path=. PlasticGuitar
 */

// use keypressed for now and then switch it for buttons

public class GuitarController {
    static final String GUITAR_HERO = "Guitar Hero";
    static final int DELAY = 150;
    private static JButton[] buttons;
    
    /*
     * Make a frame of buttons for controller components.
     */
    private static JFrame makeFrame( Controller ctrl ) {
        JFrame frm = new JFrame();
        JPanel pan = new JPanel( new GridLayout( 0, 2 ) );
        
        Component[] cmps = ctrl.getComponents();
        buttons = new JButton[ cmps.length ];
        
        for ( int i = 0; i < buttons.length; i = i + 1 ) {
            JButton button = new JButton();
            button.setPreferredSize( new Dimension( 120, 40 ) );
            buttons[ i ] = button;
            pan.add( button );
        }
        
        frm.add( pan );
        frm.pack();
        
        return frm;
    }
    
    public static void pollForever( Controller ctrl ) {
        Component[] cmps = ctrl.getComponents();
        float[] vals = new float[cmps.length];
        while( true) {
            if (ctrl.poll()) {
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store */
                    vals[ i ] = cmps[ i ].getPollData();
                }
                for ( int i = 0; i < cmps.length; i = i + 1 ) { /* display */
                    float val = vals[ i ];
                    Color col;
                    if ( val == 0.0 ) {
                        col = Color.WHITE;
                    } else if ( val == 1.0 || val == -1.0 ) {
                        col = Color.BLUE;
                        if( i==0 || i == 4 || i == 5) {
                            System.out.println("White note  " + i + "   pressed");
                        }
                        if( i==1 || i == 2 || i == 3) {
                            System.out.println("Black note  " + i + "   pressed");
                        }
                        if (i==8) {
                            System.out.println("Hero Power button pressed");
                        }
                        if (i==9){
                            System.out.println("Button 9 presssed");
                        }
                        if (i==10){
                            System.out.println("Escape button pressed");
                        }
                        if (i ==12) {
                            System.out.println("On/off button pressed");
                        }
                        

                        if(vals[15] == 1){
                            System.out.println("Strum down");
                        }
                        else if(vals[15] == -1){
                            System.out.println("Strum up");
                        }
                        else if (vals[17]>0.1) {
                        System.out.println("Whammy bar pressed");
                        }
                        else if(i == 16){
                            System.out.println("Guitar is standing up");
                        }
                        
                    } else {
                        col = Color.YELLOW;
                        if (vals[13] == 0.125 || vals[13] == 0.25 || vals[13] == 0.375 || vals[13] == 0.5) {
                            System.out.println("Guitar Strum up");
                        }
                        if (vals[13] == 0.625 || vals[13] == 0.75 || vals[13] == 0.875 || vals[13] == 1.0) {
                            System.out.println("Guitar Strum down");
                        }
                    }
                    buttons[ i ].setBackground( col );
                    //buttons[ i ].setText( "" + val + "   button   "+ i);
                    buttons[ i ].setOpaque( true );
                    buttons[ i ].repaint();
                }
            }
            // button names
            buttons[ 15 ].setText(  "  Strum bar       " + 15);
            buttons[ 8 ].setText (  "  Hero Power Button " + 8);
            buttons[ 10 ].setText(  "  Escape button   " + 10 );
            buttons[ 12 ].setText(  "  On/off button   " + 12 );
            buttons[ 13 ].setText(  "  Strum button " + 13);
            buttons[ 17 ].setText(  "  Whammy bar  " + 17 );
            buttons[ 16 ].setText(  "  Guitar position  " + 16 );
            
            
            buttons[ 1 ].setText(  "  Black note 1   " + 1 );
            buttons[ 2 ].setText(  "  Black note 2   " + 2 );
            buttons[ 3 ].setText(  "  Black note 3   " + 3 );
            buttons[ 0 ].setText(  "  White note 1   " + 0 );
            buttons[ 4 ].setText(  "  White note 2   " + 4 );
            buttons[ 5 ].setText(  "  White note 3   " + 5 );
            
            
            try { /* delay */
                Thread.sleep( DELAY );
            } catch ( Exception exn ) {
                System.out.println( exn ); System.exit( 1 );
            }
        }
        
    }
    
    public static void main(String[] argv) {
        ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
        Controller[]          ctrls = cenv.getControllers();
        
        for ( Controller ctrl : ctrls ) {
            if ( ctrl.getName().contains( GUITAR_HERO ) ) {
                JFrame frm = makeFrame( ctrl );
                frm.setVisible( true );
                pollForever( ctrl );
            }
        }
        
        System.out.println( " controller not found" );
        System.exit( 1 );
    }
    
}
