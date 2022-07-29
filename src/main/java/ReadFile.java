import domain.DagsModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


public class ReadFile {

    public static DagsModel saveDagsInfo(String subStr) {
        DagsModel dagsModel = new DagsModel();


    }
    public static JSONObject typeCastToJson(String subStr) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(subStr);
            return jsonObject;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static JSONArray readFile(String path, String fileType) {

        JSONArray array = new JSONArray();
        File file = new File(path);
        String queueRecord, token;

        if (fileType == "dems") {
            queueRecord = "receive message propertioes: MessageProperties";
            token = "receivemessage: ";
        } else {
            queueRecord = "Feign Response";
            token = "[Feign Response] ";
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(queueRecord)) {

                    int i = line.indexOf(token) + token.length();
                    if (i != -1) {
                        String subStr = line.substring(i);
                        array.add(typeCastToJson(subStr));

                        //System.out.println(jsonObject.toString());
                    }
                }
            }
            /*for(Object x : array){
                JSONObject item = (JSONObject) x;
                System.out.println(item.toString());
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void main(String[] args) {
        String path = "../dags_feign.2022-07-14.log";
        String fileType = "dags";
        JSONArray resultArray = readFile(path, fileType);


    }
}
