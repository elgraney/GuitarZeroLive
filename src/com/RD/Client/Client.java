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
    final static String downloadFilePath = "src\\com\\RD\\Client\\Downloads\\";
    final static int BUFFER_SIZE = 1024;
    static String FILE_TO_DOWNLOAD;



    public static void download(String songTitle) {
        try {
            Socket sck = new Socket(HOST, PORT);
            DataInputStream dis = new DataInputStream(sck.getInputStream());
            DataOutputStream dos = new DataOutputStream(sck.getOutputStream());


            //read the number of files from the client
            int number = dis.readInt();
            System.out.println("Number of Files to be received: " + number);

            ArrayList<CoverArt> coverArts = new ArrayList<>();

            //Add image files download image files
            for(int i = 0; i< number;i++){
                String name = dis.readUTF();
                System.out.println("Downloading... "+ name);
                FileOutputStream fos = new FileOutputStream(downloadFilePath + name);
                coverArts.add(new CoverArt(name.substring(0,name.length()-4)));
                System.out.println("Check");
                Long fileSize = dis.readLong();
                int length = 0;
                byte[]buff = new byte[BUFFER_SIZE];

                while (fileSize > 0 && (length = dis.read(buff,0, (int)Math.min(buff.length, fileSize))) != -1){
                    fos.write(buff, 0, length);
                    fileSize = fileSize - length;
                }

                fos.flush();
                fos.close();

            }

            getZips(songTitle, coverArts, dos, dis);


            sck.close();
        } catch (Exception exn) {
            System.out.println(exn); System.exit(1);
        }
    }


    public static  void getZips(String songToDownload, ArrayList<CoverArt> coverArts, DataOutputStream dos, DataInputStream dis){


            try{
                //Get zips
                String bundleTitle = FILE_TO_DOWNLOAD;
                //Check if file exists in zips. if not then return error.
                assert coverArts.contains(bundleTitle): "File does not exist on the server";
                System.out.println("File to download..."+ bundleTitle);
                dos.writeUTF(bundleTitle);
                FileOutputStream fout = new FileOutputStream(downloadFilePath + bundleTitle + ".zip");

                Long fileSize = dis.readLong();
                int length = 0;
                byte[]buff = new byte[BUFFER_SIZE];

                while (fileSize > 0 && (length = dis.read(buff,0, (int)Math.min(buff.length, fileSize))) != -1){
                    fout.write(buff, 0, length);
                    fileSize = fileSize - length;
                }
                fout.flush();
                fout.close();
            } catch (Exception exn) {
                System.out.println(exn); System.exit(1);
            }

        }

    }


