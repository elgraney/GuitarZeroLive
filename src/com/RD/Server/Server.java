package com.RD.Server;

import java.net.ServerSocket;
import java.net.Socket;
/*
 * Server
 */
public class Server {
    public final static int PORT = 8888;

    public static void main( String[] argv ) {
        try {
            final ServerSocket ssck = new ServerSocket( PORT );

            while ( true ) {
                final Socket sck = ssck.accept();
                final Worker wkr = new Worker( sck );
                new Thread(wkr).start();
            }
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}

