package com.RD.Game;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Sophia 19/03/2019
 */
public class CurrencyManager {

    static String file = "Currency/file.txt";

    public static int readFile() throws IOException {
        /***
         * Method that reads the currency int value of the text file
         * and saves the int to the currency value in model
         */

        Scanner scan = new Scanner(new File(file));
        int temp = scan.nextInt();
        scan.close();
        return temp;
    }


    public static void saveFile(int integer) throws IOException{
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

        }


    }

}
