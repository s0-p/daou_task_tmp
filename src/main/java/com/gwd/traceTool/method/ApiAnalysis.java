package com.gwd.traceTool.method;

import com.gwd.traceTool.domain.ApiModel;

import java.util.ArrayList;

public class ApiAnalysis {


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

    public ArrayList<ApiModel> analysis(ArrayList<ApiModel> array){
        arrayPrint(array);
        return array;
    }

}
