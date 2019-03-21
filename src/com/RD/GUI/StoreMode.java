package com.RD.GUI;

import com.RD.Client.Client;
import com.RD.Game.CurrencyManager;

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
    static String coverArtDirectory = "src/com/RD/Server/ServerCoverArts";
    final static int cost = 1;
    int currency;

    public StoreMode(JFrame frame, SetUpGUI base) {
        super(frame, base);
        currency = CurrencyManager.readFile();

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
        System.out.println(pathList.size());
        System.out.println(pathList.get(0));
        try {
            setUpCarousel();
        } catch (IOException e) {
            e.printStackTrace();
            new ErrorWindow("File not found! Going back to main...");
            onEscape();

        }
    }

    /**
     * Backup to the slash mode and save the currency to the file,
     * as may have been altered by buying songs.
     */
    public void onEscape() {
        CurrencyManager.saveFile(currency);
        System.out.println("Go back to main");
        tearDown();
        ModeTemplate slashMode = new SlashMode(frame, base);
        base.setListener(new GUIControls(this, slashMode));
        frame.revalidate();
        MenuGuitar slashGuitar = new MenuGuitar(slashMode);
        base.setGuitar(slashGuitar);
    }

    /**
     * Method for attempting to buy songs, including if the user
     * has enough currency.
     */
    public void onSelect(){
        MenuItem selectedSong = getViewOptions()[2];
        String songTitle = selectedSong.getTitle();

        //Check that currency is enough by calling currencyManager's readFile()
    //CurrencyManager.getFile()
        if (currency >= cost) {
            //Call Client download method
            Client.download(songTitle);
            currency --;
            onEscape();
        } else {
            JOptionPane.showMessageDialog(this,  " Unable to download "+songTitle +"! Not enough currency!");
        }
    }

    /**
     * Navigate to the server folder to get the pictures for the carousel
     * @param path
     * @return
     */
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