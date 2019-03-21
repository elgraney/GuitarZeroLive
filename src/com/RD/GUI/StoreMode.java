package com.RD.GUI;

import com.RD.Client.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Matthew on 19/02/2019.
 * Edited by Joe 20/02/2019
 * Edited by Jordan on 17/03/2019 so that image are displayed in carousel
 */
public class StoreMode extends ModeTemplate {
    static String coverArtDirectory = "Server/ServerCoverArts";
    final static int cost = 1;

    public StoreMode(JFrame frame, SetUpGUI base) {
        super(frame, base);

        ArrayList<String> pathList = getImages(coverArtDirectory);

        //Display images from the coverArts folder to the carousel
        ArrayList<MenuItem> slashModeOptions = new ArrayList<>(Arrays.asList());
        for (int i=0; i<pathList.size();i++){
            String songTitle = pathList.get(i).substring(pathList.get(i).lastIndexOf("\\") + 1,
                    pathList.get(i).lastIndexOf("."));
            MenuItem newItem = new MenuItem(pathList.get(i), songTitle);
            slashModeOptions.add(newItem);
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

    public void onSelect(){
        MenuItem selectedSong = getViewOptions()[2];
        String songTitle = selectedSong.getTitle()+".png";

        //Check that currency is enough

        //Call Client download method
        Client.download(songTitle);
    }

    public static ArrayList<String> getImages (String path) {
        final File folder = new File(path);

        ArrayList<String> result = new ArrayList<>();

        for (final File imgFile: Objects.requireNonNull(folder.listFiles())) {
            if (imgFile.getName().endsWith(".png")){
                result.add(imgFile.getPath());
            }
        }

        return result;
    }



}