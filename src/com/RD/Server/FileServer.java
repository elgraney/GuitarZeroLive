package com.RD.Server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.*;


public class FileServer extends Thread {

    public final static int PORT = 8888;

    public static void main(String[] args) throws IOException, URISyntaxException {

        try {
            final ServerSocket ssck = new ServerSocket(PORT);
            while (true) {
                try {
                    Socket clientSock = ssck.accept();
                    final Worker wkr = new Worker(clientSock);
                    new Thread(wkr).start();
                    try {
                        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
                        FileOutputStream fos = new FileOutputStream("Bohemian_Rhapsody.zip");
                        byte[] buffer = new byte[4096];

                        int filesize = 97312; // Send file size in separate msg
                        int read = 0;
                        int totalRead = 0;
                        int remaining = filesize;
                        while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                            totalRead += read;
                            remaining -= read;
                            System.out.println("read " + totalRead + " bytes.");
                            fos.write(buffer, 0, read);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        }
    }

