package com.gwd.traceTool.method;

import com.gwd.traceTool.domain.ApiModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApiParser {
    public static ApiModel createApiModel(Map<String, String> tmpMap) {
        ApiModel apiModel = new ApiModel();

        apiModel.setUrl(tmpMap.get("url"));
        apiModel.setCode(tmpMap.get("code"));
        apiModel.setTime(tmpMap.get("time"));
        apiModel.setMessage(tmpMap.get("message"));
        apiModel.setBody(tmpMap.get("body"));
        apiModel.setFolder(tmpMap.get("folder"));

        return apiModel;
    }

    public static ArrayList readLine(String path, String folder) {
        ArrayList<ApiModel> subList = new ArrayList<ApiModel>();

        File file = new File(path);
        String queueRecord = "[Feign Response] ";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(queueRecord)) {

                    int i = line.indexOf(queueRecord) + queueRecord.length();

                    List<String> tmpList;
                    tmpList = List.of(line.substring(i).split(", code:"));

                    Map<String, String> tmpMap = new HashMap<>();

                    tmpMap.put("url", tmpList.get(0).substring(4));

                    tmpList = List.of(tmpList.get(1).split(",|:"));
                    tmpMap.put("code", tmpList.get(0));
                    tmpMap.put("time", tmpList.get(2).replace("ms", ""));
                    tmpMap.put("message", tmpList.get(4));
                    tmpMap.put("body", tmpList.get(6));
                    tmpMap.put("folder", folder);

                    ApiModel apiModel = createApiModel(tmpMap);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                    LocalDateTime occurrenceTime = LocalDateTime.parse(line.substring(0, 23), formatter);
                    apiModel.setOccurrence_time(occurrenceTime);

                    subList.add(apiModel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subList;
    }

    public static ArrayList readApi(String path) {
        ArrayList<ApiModel> list = new ArrayList<ApiModel>();

        String dags1Path = "C:/Users/User/Desktop/log/dags1/" + path;
        list.addAll(readLine(dags1Path, "1"));

        String dags2Path = "C:/Users/User/Desktop/log/dags2/" + path;
        list.addAll(readLine(dags2Path, "2"));

        Collections.sort(list);

        /*for (ApiModel x: list) {

            System.out.println(x.getOccurrence_time());

        }*/
        return list;
    }
}
