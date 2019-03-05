package com.RD.Server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Client
 */
public class Client {
    final static String HOST = "localhost";
    final static int    PORT = 8888;

    public static void main( String[] argv ) {
        try {
            Socket sck = new Socket( HOST, PORT );

            InputStream  in  = sck.getInputStream();
            OutputStream out = sck.getOutputStream();

            BufferedReader reader =
                    new BufferedReader( new InputStreamReader( in ) );
            PrintWriter writer =
                    new PrintWriter( out );

            writer.print( "GET / HTTP/1.1\r\n" );
            writer.print( "Host : " + HOST + "\r\n" );
            writer.print( "Connection: close\r\n" );
            writer.print( "\r\n" );
            writer.flush();

            int ch;
            while ( ( ch = reader.read() ) != -1 ) {
                System.out.write( (char) ch );
            }
            System.out.flush();

            sck.close();
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
