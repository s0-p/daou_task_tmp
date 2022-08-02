package com.gwd.traceTool.method;

import com.gwd.traceTool.domain.ApiModel;

import java.util.ArrayList;

public class ApiAnalysis {

    //ArrayList<ApiModel> array;

//    public ApiAnalysis(ArrayList<ApiModel> array) { // ApiAnalysis 생성 되면서 분석됨
//        this.array = array;
//    }


    public void analysis(ArrayList<ApiModel> array){ //분석
        arrayPrint(array);
        for(int i=0; i<array.size();i++){
//            System.out.println("!!!!!!!!!!!!!!!!!url: "+array.get(i).getUrl());
            String url = array.get(i).getUrl();
        }
    }

    //public static 문자열 분리 함수

    // api 종류 별, 목적지 host 별, port number 별

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
            System.out.println();
            i++;
            System.out.println("-------------------------------------------------------------------------------------------"+i);
            System.out.println();
        }
    }

}
