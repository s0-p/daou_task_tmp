package com.gwd.tracetool.service;

import com.gwd.tracetool.domain.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiAnalysis {
    public void analysis(List<ApiModel> array){ //분석

        Map<String, Integer> hostMap = new HashMap<>();
        Map<String, Integer> portMap = new HashMap<>();
        Map<String, Integer> apiMap = new HashMap<>();
        Map<String, Integer> codeMap = new HashMap<>();
        String url;

        for(int i=0; i<array.size();i++){
            url = array.get(i).getUrl();
            Map<String, String> urlMap = splitUrl(url);

            String destinationHost = urlMap.get("destinationHost");  // ex) inbound-admin.daouoffice.com
            String port = urlMap.get("port");  // ex) 8443
            String apiType = urlMap.get("apiType"); // ex) /api/setting/service/domain/save
            int code = array.get(i).getCode();

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

            if(codeMap.containsKey(code)){
                count = codeMap.get(code) + 1;
            }
            else {
                count = 1;
            }
            //codeMap.put(code,count);

        }
        for( String strKey : hostMap.keySet() ){
            int strValue = hostMap.get(strKey);
            System.out.println( strKey +":"+ strValue );
        }

        System.out.println( "--------------------------------------------------" );
        for( String strKey : portMap.keySet() ){
            int strValue = portMap.get(strKey);
            System.out.println( strKey +":"+ strValue );
        }

        System.out.println( "--------------------------------------------------" );
        for( String strKey : apiMap.keySet() ){
            int strValue = apiMap.get(strKey);
            System.out.println( strKey +":"+ strValue );
        }
        System.out.println( "--------------------------------------------------" );
        for( String strKey : codeMap.keySet() ){
            int strValue = codeMap.get(strKey);
            System.out.println( strKey +":"+ strValue );
        }
    }
    public static Map<String, String> splitUrl(String url) {
        String[] tmpList = url.split(":");

        String destinationHost = tmpList[1].substring(2);
        String port = tmpList[2].substring(0, 4);
        String apiType = tmpList[2].substring(4);

        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("destinationHost", destinationHost);
        urlMap.put("port", port);
        urlMap.put("apiType", apiType);

        return urlMap;

    }

    // api 종류 별, 목적지 host 별, port number 별

//    public ArrayList<String> getCodeNum(ArrayList<ApiModel> array){
//        String code;
//
//        for(int i=0; i<array.size();i++){
////           System.out.println("!!!!!!!!!!!!!!!!!url: "+array.get(i).getUrl());
//            code = array.get(i).getCode();
//
//        }
//        return array;
//    }

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
//            try {
//                System.out.println("folder : "+x.getFolder());
//            }
//            catch (NullPointerException e){
//                System.out.println("folder : NULL");
//            }
            System.out.println();
            i++;
            System.out.println("-------------------------------------------------------------------------------------------"+i);
            System.out.println();
        }
    }

}
