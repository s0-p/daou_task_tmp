package com.gwd.traceTool.method;

import com.gwd.traceTool.domain.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiAnalysis {

    //ArrayList<ApiModel> array;

//    public ApiAnalysis(ArrayList<ApiModel> array) { // ApiAnalysis 생성 되면서 분석됨
//        this.array = array;
//    }


    public void analysis(ArrayList<ApiModel> array){ //분석
        arrayPrint(array);

        //saveInMap()
    }

    //public static 문자열 분리 함수
    public static Map<String, String> splitUrl(String url) {
        String[] tmpList = url.split(":");

        String destinationHost = tmpList[1].substring(2);
        String port = tmpList[2].substring(0, 4);
        String apiType = tmpList[2].substring(4);

        if(apiType.contains("OPERATION")) {
            int index = apiType.indexOf("/OPERATION");
            apiType = apiType.substring(0, index);
        }

        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("destinationHost", destinationHost);
        urlMap.put("port", port);
        urlMap.put("apiType", apiType);

        return urlMap;

    }

    // api 종류 별, 목적지 host 별, port number 별
    public static Map saveInMap(ArrayList<ApiModel> array) {

        Map<String, Map> dataMap = new HashMap<>();
        Map<String, Integer> hostMap = new HashMap<>();
        Map<String, Integer> portMap = new HashMap<>();
        Map<String, Integer> apiMap = new HashMap<>();
        Map<String, String> urlMap;

        for(int i=0; i<array.size();i++){
            String url = array.get(i).getUrl();
            urlMap = splitUrl(url);

            String destinationHost = urlMap.get("destinationHost");
            String port = urlMap.get("port");
            String apiType = urlMap.get("apiType");

            int count = 0;

            //  목적지 host 별
            if (hostMap.containsKey(destinationHost)) {
                count = hostMap.get(destinationHost) + 1;
            }
            else {
                count = 1;
            }
            hostMap.put(destinationHost, count);


            //  port 별
            if (portMap.containsKey(port)) {
                count = portMap.get(port) + 1;
            }
            else {
                count = 1;
            }
            portMap.put(port, count);


            //  api 종류 별
            if(apiMap.containsKey(apiType)) {
                count = apiMap.get(apiType) + 1;
            }
            else {
                count = 1;
            }
            apiMap.put(apiType, count);


        }
        dataMap.put("destinationHost", hostMap);
        dataMap.put("port", portMap);
        dataMap.put("api", apiMap);

        for( String strKey : dataMap.keySet() ){
            Map strValue = dataMap.get(strKey);
            System.out.println( strKey +":"+ strValue );
        }

        return dataMap;
    }

    public static void arrayPrint(ArrayList<ApiModel> array){
        int i=0;
        for(ApiModel x : array){
            System.out.println("url : "+x.getUrl());
            System.out.println("code : "+x.getCode());
            System.out.println("time : "+x.getTime());
            if(x.getMessage().equals("")){
                System.out.println("message : NULL");
            }
            else{
                System.out.println("message : " + x.getMessage());
            }
            System.out.println("body : "+x.getBody());
            try {
                System.out.println("folder : "+x.getFolder());
            }
            catch (NullPointerException e){
                System.out.println("folder : NULL");
            }
            System.out.println();
            i++;
            System.out.println("-------------------------------------------------------------------------------------------"+i);
            System.out.println();
        }
    }

}
