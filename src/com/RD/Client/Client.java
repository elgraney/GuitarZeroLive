package com.RD.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Server
 * @ Jordan
 * assisted by Joe
 */

class CoverArt {

    String bundleTitle;
    File coverArtPath;

    public CoverArt(String bundleTitle) {
        this.bundleTitle = bundleTitle;
    }

    public String getBundleTitle() {
        return bundleTitle;
    }

    public File getCoverArtPath() {
        return coverArtPath;
    }
}

public class Client {
    final static String HOST = "localhost";
    final static int PORT = 8888;
    final static String downloadFilePath = "clientzips";
    final static int BUFFER_SIZE = 1024;


    public static void download(String songTitle) {
        try {
            Socket sck = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(sck.getInputStream());
            DataOutputStream dos = new DataOutputStream(sck.getOutputStream());


            //read the number of files from the client
            int number = dis.readInt();
            System.out.println("Number of Files to be received: " + number);

            ArrayList<CoverArt> coverArts = new ArrayList<>();

            getZips(songTitle, coverArts, dos, dis);


            sck.close();
        } catch (Exception exn) {
            System.out.println(exn);
            System.exit(1);
        }
    }

    public static void getZips(String songTitle, ArrayList<CoverArt> coverArts, DataOutputStream dos, DataInputStream dis) {
        try {
            //Get zips
            System.out.println(coverArts);
            String bundleTitle = songTitle;
            System.out.println(bundleTitle);

            System.out.println();
            System.out.println("File to download..." + bundleTitle);
            dos.writeUTF(bundleTitle);
            FileOutputStream fout = new FileOutputStream(downloadFilePath + bundleTitle + ".zip");

            Long fileSize = dis.readLong();
            int length = 0;
            byte[] buff = new byte[BUFFER_SIZE];

            while (fileSize > 0 && (length = dis.read(buff, 0, (int) Math.min(buff.length, fileSize))) != -1) {
                fout.write(buff, 0, length);
                fileSize = fileSize - length;
            }
            System.out.println(bundleTitle + " download complete");
            fout.flush();
            fout.close();
        } catch (Exception exn) {
            System.out.println(exn);
            System.exit(1);
        }

    }
}


