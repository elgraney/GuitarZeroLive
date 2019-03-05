import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ZeroPowerMode {
    public static List<String> TextFileToArray(File MidiTxt){
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
        String s;
        while((s=abc.readLine())!=null) {
            data.add(s);
            System.out.println(s);
        }
        abc.close();


        return TextFileArray;
    }

}
