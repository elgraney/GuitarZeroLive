package com.RD.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Worker.
 *
 * @author  @author Jordan

 */
public class Worker implements Runnable {

    private Socket sck;
    private String bundlesDir = Server.bundlesDir;
    private String coverArtDir = Server.coverArtDir;


    Worker(Socket sck ) {
        this.sck = sck;
    }


    public void run() {
        try {

            final OutputStream os = sck.getOutputStream();
            final InputStream in = sck.getInputStream();
            DataOutputStream dataOut = new DataOutputStream(os);
            DataInputStream dataIn = new DataInputStream(in);

            //Find number of image files to be sent
            File dir = new File(coverArtDir);
            int noFiles = dir.list().length;
            dataOut.writeInt(noFiles);
            dataOut.flush();


            //Send zip to Client
            String bundleTitle = dataIn.readUTF();
            String bundlePath = bundlesDir + "\\" + bundleTitle + ".zip";
            File bundleZip = new File(bundlePath);
            DataInputStream dataIs = new DataInputStream(new FileInputStream(bundleZip));

            Long fileSize = bundleZip.length();
            dataOut.writeLong(fileSize);

            byte[] buf = new byte[Server.BUFFER_SIZE];

            System.out.println("Sending bundle zip file: " + bundleZip);
            int length;
            while (fileSize > 0 && (length = dataIs.read(buf,0, (int)Math.min(buf.length, fileSize))) != -1){
                dataOut.write(buf,0,length);
                fileSize = fileSize - length;
            }

            dataOut.flush();
            dataOut.close();
            dataIn.close();
            sck.close();


        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
