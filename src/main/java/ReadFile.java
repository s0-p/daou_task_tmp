import org.json.simple.JSONArray;

import java.util.ArrayList;

import static method.DagsLogPreprocessing.readDags;

public class ReadFile {

    public static void main(String[] args) {
        String path = "C:/Users/User/Desktop/test/test_dags.log";
        String fileType = "dags";
        ArrayList resultArray = readDags(path);


    }
}
