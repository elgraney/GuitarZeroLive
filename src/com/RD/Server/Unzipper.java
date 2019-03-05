package com.RD.Server;

import java.io.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;

public class Unzipper {
    public static void main(String[] args) {
        String zipName = "D:\\OneDrive - University of Exeter\\.Stage 2\\ECM2434 Group Software Engineering Project\\Project\\GZL\\GuitarZeroLive\\src\\com\\RD\\Server\\AI.zip";
        String bundleTitle;
        String bundleCoverArt;
        try (FileInputStream fis = new FileInputStream(zipName);
             ZipInputStream zis =
                     new ZipInputStream(new BufferedInputStream(fis))) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("Unzipping: " + entry.getName());

                int size;
                byte[] buffer = new byte[2048];

                try (FileOutputStream fos =
                             new FileOutputStream(entry.getName());
                     BufferedOutputStream bos =
                             new BufferedOutputStream(fos, buffer.length)) {

                    while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, size);
                    }
                    bos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}