package com.gwd.traceTool.method;

import com.gwd.traceTool.domain.ApiModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiParser {
    public static ApiModel createApiModel(String subStr) {
        ApiModel apiModel = new ApiModel();
        List<String> tmpList;

        tmpList = List.of(subStr.split(", code:"));
        apiModel.setUrl(tmpList.get(0).substring(4));

        tmpList = List.of(tmpList.get(1).split(",|:"));
        apiModel.setCode(tmpList.get(0));
        apiModel.setTime(tmpList.get(2));
        apiModel.setMessage(tmpList.get(4));
        apiModel.setBody(tmpList.get(6));

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
                    if (i != -1) {
                        String occurrenceTime = line.substring(0, 18);
                        String subStr = line.substring(i);

                        ApiModel dagsModel = createApiModel(subStr);
                        dagsModel.setOccurrence_time(occurrenceTime);
                        dagsModel.setFolder(folder);
                        subList.add(dagsModel);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return subList;
    }

    public ArrayList readApi(String path) {
        ArrayList<ApiModel> list = new ArrayList<ApiModel>();

        String dags1Path = "C:/Users/User/Desktop/log/dags1/" + path;
        list.addAll(readLine(dags1Path, "1"));

        String dags2Path = "C:/Users/User/Desktop/log/dags1/" + path;
        list.addAll(readLine(dags2Path, "2"));

//        int i=0;
//        for(ApiModel x : list){
//            System.out.println("url : "+x.getUrl());
//            System.out.println("code : "+x.getCode());
//            System.out.println("time : "+x.getTime());
//            if(x.getMessage().equals("")){
//                System.out.println("message : NULL");
//            }
//            else{
//                System.out.println("message : " + x.getMessage());
//            }
//            System.out.println("body : "+x.getBody());
//            System.out.println();
//            i++;
//            System.out.println("-------------------------------------------------------------------------------------------"+i);
//            System.out.println();
//        }



        return list;
    }
}
