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
 * Assisted by Jordan
 */

/**
 * Select Mode Class
 * Sets up carousel with bundles stored on the client side. Extends the carousel class ModeTemplate. Retrieves each bundle from the folder
 * and unzips them to then access the files inside for the carousel paths. Displays them in alphabetical order.
 */

public class SelectMode extends ModeTemplate {
    final int PNG = 0;
    final int NAME = 1;
    final int MIDI = 2;
    final int TXT = 3;

    public SelectMode(JFrame frame, SetUpGUI base) {

        super(frame, base);
        checkZips();
        ArrayList<ArrayList<String>> pathList = fileLists();

        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList());
        for (int i = 0; i < pathList.size(); i++) {
            slashModeOptions.add(new SongMenuItem(pathList.get(i).get(PNG), pathList.get(i).get(NAME), pathList.get(i).get(MIDI), pathList.get(i).get(TXT)));
        }

        innitMenu(slashModeOptions);
        try {
            setUpCarousel();
        } catch (IOException e) {
            System.out.println(e); System.exit(1);
        }
    }

    /*Return back to main menu*/
    public void onEscape() {
        System.out.println("Go back to main");
        tearDown();
        ModeTemplate slashMode = new SlashMode(frame, base);
        base.setListener(new GUIControls(this, slashMode));
        MenuGuitar slashGuitar = new MenuGuitar(slashMode);
        base.setMenuGuitar(slashGuitar);
        frame.revalidate();
    }

    public void onSelect(){
        System.out.println("play song");
        SongMenuItem currentBundle =(SongMenuItem)getViewOptions()[2];
        base.setCurrentBundle(currentBundle);
        onEscape();

    }

    /**
     * Find the image path, name, midi file path and the notes file path for use in the carousel.
     * Written by Joe
     * @return paths, a list of the paths to be used.
     */
    public ArrayList<ArrayList<String>> fileLists() {
        final File folder = new File("clientzips");

        ArrayList<ArrayList<String>> paths = new ArrayList<>((Arrays.asList()));
        try {
            for (final File fileEntry : folder.listFiles()) {
                ArrayList<String> singlePaths = new ArrayList<>(Arrays.asList("", "", "", ""));
                if (fileEntry.isDirectory()) {
                    final File[] entries = fileEntry.listFiles();

                    for (final File entry : entries) {
                        if (entry.toString().contains((".png"))) {
                            singlePaths.set(PNG, entry.toString());
                        } else if (entry.toString().contains(".mid")) {
                            singlePaths.set(MIDI, entry.toString());
                        } else if (entry.toString().contains(".txt")) {
                            singlePaths.set(TXT, entry.toString());
                        } else {
                            System.out.println("Wrong type of file detected in " + fileEntry + " called " + entry);
                            onEscape();
                        }
                    }
                    singlePaths.set(NAME, fileEntry.toString().substring(11));
                    paths.add(singlePaths);

                } else {
                    System.out.println("Not a directory, skipping over " + fileEntry);
                }
            }

        } catch (NullPointerException e) {
            System.out.println("No files found! Please enter files into clientzips folder.");
            onEscape();
        }

        return paths;

    }

    /**
     * Unzips the files found on the client side to a folder, which can then be used.
     * Written by Joe
     *
     */
    public void checkZips() {
        final File folder = new File("clientzips");
        final int BUFFER = 1024;

        for(final File fileEntry : folder.listFiles()){
            if(fileEntry.toString().contains(".zip")){
                try{

                    File directory = new File(fileEntry.toString().substring(11, fileEntry.toString().length() - 4));

                    if(! directory.exists()) {
                        new File("clientzips/" + directory).mkdir();
                        byte[] buffer = new byte[BUFFER];
                        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileEntry));
                        ZipEntry ze = zis.getNextEntry();

                        while (ze != null) {
                            String fileName = ze.getName();
                            File newFile = new File("clientzips/" + directory + File.separator + fileName);
                            new File(newFile.getParent()).mkdirs();
                            FileOutputStream fos = new FileOutputStream(newFile);

                            int length;

                            while ((length = zis.read(buffer)) > 0) {
                                fos.write(buffer, 0, length);
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


