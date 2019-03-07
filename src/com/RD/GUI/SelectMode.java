package com.RD.GUI;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


/**
 * Created by Matthew on 19/02/2019.
 * Edited by Joe on 20/02/2019
 * New Content by Joe on 3/03/19
 * Content by Joe
 */
public class SelectMode extends ModeTemplate {
    public SelectMode(JFrame frame, SetUpGUI base) {

        super(frame, base);

        checkZips();

        ArrayList<ArrayList<String>> pathList = fileLists();

        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList());
        for (int i = 0; i < pathList.size(); i++) {
            slashModeOptions.add(new SongMenuItem(pathList.get(i).get(0), pathList.get(i).get(1), pathList.get(i).get(2), pathList.get(i).get(3)));
        }

        innitMenu(slashModeOptions);
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEscape() {
        System.out.println("Go back to main");
        tearDown();
        ModeTemplate slashMode = new SlashMode(frame, base);
        base.setListener(new GUIControls(this, slashMode));
        frame.revalidate();
    }

    public ArrayList<ArrayList<String>> fileLists() {
        final File folder = new File("clientzips");

        for(final File fileEntry : folder.listFiles()){
            if(fileEntry.toString().contains(".zip")){
                File directory = new File(fileEntry.toString().substring(11, fileEntry.toString().length() - 4));
                if(! directory.exists()){
                    directory.mkdir();
                }
            }
        }
        ArrayList<ArrayList<String>> paths = new ArrayList<>((Arrays.asList()));
        try {
            for (final File fileEntry : folder.listFiles()) {
                ArrayList<String> singlePaths = new ArrayList<>(Arrays.asList("", "", "", ""));
                if (fileEntry.isDirectory()) {
                    final File[] entries = fileEntry.listFiles();

                    for (final File entry : entries) {
                        if (entry.toString().contains((".png"))) {
                            singlePaths.set(0, entry.toString());
                        } else if (entry.toString().contains(".mid")) {
                            singlePaths.set(2, entry.toString());
                        } else if (entry.toString().contains(".txt")) {
                            singlePaths.set(3, entry.toString());
                        } else {
                            System.out.println("Wrong type of file detected in " + fileEntry);
                            System.exit(0);
                        }
                    }
                    singlePaths.set(1, fileEntry.toString().substring(11));
                    paths.add(singlePaths);
                } else {
                    System.out.println("Not a directory, skipping over " + fileEntry);
                    continue;
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return paths;

    }

    public void checkZips() {
        final File folder = new File("clientzips");

        for(final File fileEntry : folder.listFiles()){
            if(fileEntry.toString().contains(".zip")){
                try{
                    ZipFile zipFile = new ZipFile(fileEntry);
                    File directory = new File(fileEntry.toString().substring(11, fileEntry.toString().length() - 4));
                    if(! directory.exists()) {
                        new File("clientzips/" + directory).mkdir();
                        byte[] buffer = new byte[1024];
                        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileEntry));
                        ZipEntry ze = zis.getNextEntry();
                        while (ze != null) {
                            String fileName = ze.getName();
                            File newFile = new File("clientzips/" + directory + File.separator + fileName);
                            new File(newFile.getParent()).mkdirs();
                            FileOutputStream fos = new FileOutputStream(newFile);
                            int len;
                            while ((len = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                            fos.close();
                            ze = zis.getNextEntry();
                        }
                        zis.closeEntry();
                        zis.close();
                        }
                }catch(IOException e) {
                    System.out.println("File does not exist! Reverting back to main menu");
                    onEscape();
                }
            }
        }
    }
}


