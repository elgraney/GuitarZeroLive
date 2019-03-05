package com.RD.Server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/*
 * Worker.
 *
 * @author  David Wakeling
 * @version 1.00, January 2019.
 */
public class Worker implements Runnable {
    final static String HTML = "<html>Hello World!</html>";

    private Socket sck;

    Worker( Socket sck ) {
        this.sck = sck;
    }

    public void run() {
        try {
            final InputStream  in  = sck.getInputStream();
            final OutputStream out = sck.getOutputStream();

            final BufferedReader reader =
                    new BufferedReader( new InputStreamReader( in ) );
            final PrintWriter    writer =
                    new PrintWriter( out );

            final String content = HTML;

            writer.print( "HTTP/1.1 200 OK\r\n" );
            writer.print( "Content-Type: text/html\r\n" );
            writer.print( "Content-Length: " + content.length() + "\r\n" );
            writer.print( "\r\n" );
            writer.print( content );
            writer.print( "\n" );
            writer.flush();

//            sck.close();
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
