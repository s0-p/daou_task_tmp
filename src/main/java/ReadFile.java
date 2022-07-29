
import org.json.simple.JSONArray;

import java.io.*;
import java.util.ArrayList;

import static method.DagsLogPreprocessing.readDags;
import static method.DemsLogPreprocessing.readFile;


public class ReadFile {

    public static void main(String[] args) {
        String path = "C:/Users/User/Desktop/인턴과제_자료_20220722/dags1/dags_feign.2022-07-14.log";

        // String path = "C:/Users/User/Desktop/인턴과제_자료_20220722/dems1/trace/dems.2022-07-14.log";
        String fileType = "dags";
        ArrayList resultArray = readDags(path);


    }
}
