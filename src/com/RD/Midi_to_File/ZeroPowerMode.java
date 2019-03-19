package com.RD.Midi_to_File;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZeroPowerMode {

    public static List<String> ZeroPowerModeCalculator(File MidiTxt) throws IOException {

        final int MINIMUM_ZERO_POWER_NOTES_FREQUENCY = 10;

        BufferedReader notesFile = null;
        try {
            notesFile = new BufferedReader(new FileReader(MidiTxt));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("File is not found!");
            System.exit(0);
        }

        List<String> temporaryList = new ArrayList<String>();
        List<String> zeroPowerList = new ArrayList<String>();
        String line;

        int n =0;
        String firstLine = notesFile.readLine();

        //Splitting the notes file to find the note being played.
        while((line=notesFile.readLine())!=null) {
            if (n!=0) {
                zeroPowerList.add(line);
                String[] parts = line.split(",");
                temporaryList.add(parts[1]);
            }
            n++;

        }

        int currentNumber;
        int currentNumberCount = 0;

        List<Integer> frequency = new ArrayList<>();
        for(int i = 0; i < temporaryList.size(); i++){
            currentNumber = Integer.parseInt(temporaryList.get(i));
            if(i == 0 || currentNumber == Integer.parseInt(temporaryList.get(i - 1))){
                currentNumberCount += 1;
            }else{
                frequency.add(currentNumberCount);
                currentNumberCount = 0;
            }
        }

        int maxFreq = Collections.max(frequency);
        int lastIndex = frequency.lastIndexOf(maxFreq);

        int currentIndex = 0;
        int startIndexOfNotes = 0;

        for(int i = 0; i < temporaryList.size(); i++){
            currentNumber = Integer.parseInt(temporaryList.get(i));
            if(i == 0 || currentNumber == Integer.parseInt(temporaryList.get(i - 1))) {
                continue;
            }else{
                currentIndex += 1;
                if(currentIndex == lastIndex){
                    startIndexOfNotes = i;
                    break;
                }
            }
        }

        if(maxFreq > MINIMUM_ZERO_POWER_NOTES_FREQUENCY) {
            zeroPowerList.add(startIndexOfNotes - 10, "START");
            zeroPowerList.add(startIndexOfNotes + maxFreq + 12, "END");
        }

        notesFile.close();

        FileWriter writer = new FileWriter("notes\\file.txt");
        writer.write(firstLine + "\n");

        int size = zeroPowerList.size();

        for (int i=0;i<size;i++) {
            String str = zeroPowerList.get(i);
            writer.write(str);
            if(i < size-1) {
                writer.write("\n");
            }
        }

        writer.close();

        return zeroPowerList;
    }

    public static void main(String[] args) throws IOException {
        File file = new File ("notes\\file.txt");
        System.out.println(ZeroPowerModeCalculator(file));
    }

}
