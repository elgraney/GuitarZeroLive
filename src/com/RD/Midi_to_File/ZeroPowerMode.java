package com.RD.Midi_to_File;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ZeroPowerMode {
    public static List<String> TextFileToArray(File MidiTxt) throws IOException {
        List<String> TextFileArray = new ArrayList<>();
        BufferedReader abc = null;
        try {
            abc = new BufferedReader(new FileReader(MidiTxt));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file is not found");
            System.exit(0);
        }
        List<String> data = new ArrayList<String>();
        String Line;
        int n =0;
        while((Line=abc.readLine())!=null) {
            if (n!=0) {
                data.add(Line);
            }
            n++;

        }
        abc.close();

        return data;
    }

    public static void main(String[] args) throws IOException {
        File file = new File ("C:\\Users\\840\\IdeaProjects\\midi\\src\\file.txt");
        System.out.println(TextFileToArray(file));
        System.out.println(TextFileToArray(file).get(0));
        System.out.println(TextFileToArray(file).get(1));
    }

}
