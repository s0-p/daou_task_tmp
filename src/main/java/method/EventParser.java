package method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventParser {
    public static JSONObject typeCastToJson(String subStr) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(subStr);
            return jsonObject;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getEventName(Map map){
        Map map2 = (Map)map.get("header");
        Map map3 = (Map)map2.get("event");
        return (String)map3.get("eventName");
    }
    public static String getCreateAt(Map map){
        Map map2 = (Map)map.get("header");
        Map map3 = (Map)map2.get("event");
        return (String)map3.get("createAt");
    }
    public static String getProvider(Map map){
        Map map2 = (Map)map.get("header");
        Map map3 = (Map)map2.get("event");
        return (String)map3.get("provider");
    }

    public static String getTransactionId(Map map){
        Map map2 = (Map)map.get("header");
        Map map3 = (Map)map2.get("workflow");
        return (String)map3.get("transactionId");
    }
    public static String getStatusCode(Map map){
        Map map2 = (Map)map.get("header");
        Map map3 = (Map)map2.get("amqResponseInfo");
        String httpStatusCode;
        try {
            httpStatusCode = (String)map3.get("httpStatusCode").toString();
        }catch (NullPointerException e){
            httpStatusCode = "NULL";
        }
        return httpStatusCode;
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
//            for(Object x : array){
//                JSONObject item = (JSONObject) x;
//                System.out.println(item.toString());
//                System.out.println("---------------------------------------");
//                System.out.println(item[1]);
//            }
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> parsingData = new HashMap<>();
            int i =0;
            for (Object x : array){
                JSONObject jsonObject = (JSONObject) x;
                Map map = mapper.readValue(jsonObject.toString(), Map.class);
                parsingData.put("eventName",getEventName(map));
                parsingData.put("createAt",getCreateAt(map));
                parsingData.put("provider",getCreateAt(map));
                parsingData.put("transactionId",getTransactionId(map));
                parsingData.put("httpStatusCode",getStatusCode(map));

                Iterator<String> iterator = parsingData.keySet().iterator();

                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = (String)parsingData.get(key);
                    System.out.println("key : "+key);
                    System.out.println("value : "+value);
                    System.out.println();
                }
                i++;
                System.out.println("-------------------------------------"+i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
}
