package com.gwd.traceTool.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EventParser {
    public static JSONObject StringToJson(String subStr) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(subStr);
            return jsonObject;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray readLine(String path, String folder) {
        JSONArray subArray = new JSONArray();
        File file = new File(path);
        JSONObject currJson = new JSONObject();
        String queueRecord = "receive message propertioes: MessageProperties";
        String token = "receivemessage: ";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(queueRecord)) {

                    int i = line.indexOf(token) + token.length();
                    if (i != -1) {
                        String occurrenceTime = line.substring(0, 22);
                        String subStr = line.substring(i);

                        currJson = StringToJson(subStr);
                        currJson.put("occurrenceTime", occurrenceTime);
                        currJson.put("folder", folder);
                        subArray.add(currJson);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subArray;
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

    static class JSONComparator implements Comparator<JSONObject> {
        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            String v1 = o1.get("occurrenceTime").toString();
            String v3 = o2.get("occurrenceTime").toString();
            return v1.compareTo(v3);
        }
    }


        public static JSONArray readEvent(String path) throws JsonProcessingException {

        JSONArray array = new JSONArray();

        String dems1Path = "C:/Users/User/Desktop/log/dems1/trace/" + path;
        array.addAll(readLine(dems1Path, "dems1"));


        String dems2Path = "C:/Users/User/Desktop/log/dems2/trace/" + path;
        array.addAll(readLine(dems2Path, "dems2"));

        Collections.sort(array, new JSONComparator());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> parsingData = new HashMap<>();
        int i =0;
        for (Object x : array){
            JSONObject jsonObject = (JSONObject) x;
            Map map = mapper.readValue(jsonObject.toString(), Map.class);
            parsingData.put("eventName",getEventName(map));
            parsingData.put("createAt",getCreateAt(map));
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

        /*for(Object x : array){
                JSONObject item = (JSONObject) x;
                System.out.println(item.get("occurrenceTime"));
                System.out.println("---------------------------------------");
        }*/

        return array;
    }
}
