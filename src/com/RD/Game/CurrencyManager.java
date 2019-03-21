package com.RD.Game;
import com.RD.GUI.ErrorWindow;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Sophia 19/03/2019
 */
public class CurrencyManager {

    static String file = "Currency/file.txt";

    public static int readFile() {
        /***
         * Method that reads the currency int value of the text file
         * and saves the int to the currency value in model
         */

        Scanner scan = null;
        try {
            scan = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            new ErrorWindow("Currency file cant be read");
            e.printStackTrace();

        }
        int temp = scan.nextInt();
        scan.close();
        return temp;
    }


    public static void saveFile(int integer) {
        /**
         * Method that gets the int value of currency from model
         * and writes it to the currency text file
         */
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(Integer.toString(integer));
            writer.close();
        } catch (IOException e){
            System.out.println("IOException" + e);
            new ErrorWindow("Currency file not found! Exiting program...");
            System.exit(1);

        }


    }

}
