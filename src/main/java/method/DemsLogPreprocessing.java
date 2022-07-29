package method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DemsLogPreprocessing {
    public static JSONObject typeCastToJson(String subStr) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(subStr);
            return jsonObject;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray readFile(String path) {

        JSONArray array = new JSONArray();
        File file = new File(path);
        String queueRecord = "receive message propertioes: MessageProperties";
        String token = "receivemessage: ";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(queueRecord)) {

                    int i = line.indexOf(token) + token.length();
                    if (i != -1) {
                        String subStr = line.substring(i);
                        array.add(typeCastToJson(subStr));
                    }
                }
            }
            for(Object x : array){
                JSONObject item = (JSONObject) x;
                System.out.println(item.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
}
