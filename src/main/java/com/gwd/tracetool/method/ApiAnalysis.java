package com.gwd.tracetool.method;

import com.gwd.tracetool.domain.ApiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiAnalysis {

    public static Map getAvgTime(ArrayList<ApiModel> array, Map<String, Map> dataMap) {  // 최종 output에 API별 average time 통계를 추가하여 반환

        Map<String, Map> apiMap = dataMap.get("api");
        for (Map.Entry<String, Map> entry : apiMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> value = entry.getValue();

            int totalTime = 0;
            for (ApiModel model : array) {

                if (model.getUrl().contains(key)) {
                    totalTime = totalTime + Integer.parseInt(model.getTime());
                }
            }
            int AvgTime = totalTime / value.get("count");
            value.put("avgTime", AvgTime);

        }
        return dataMap;
    }

    public static Map<String, String> splitUrl(String url) {  // url에서 목적지 호스트, 포트번호, API 종류를 추출
        String[] tmpList = url.split(":");
        Map<String, String> urlMap = new HashMap<>();

        String destinationHost = tmpList[1].substring(2);
        String port = tmpList[2].substring(0, 4);
        String apiType = tmpList[2].substring(4);

        if (apiType.contains("OPERATION")) {
            int index = apiType.indexOf("/OPERATION");
            apiType = apiType.substring(0, index);
        }

        urlMap.put("destinationHost", destinationHost);
        urlMap.put("port", port);
        urlMap.put("apiType", apiType);

        return urlMap;

    }

    public static int counter(Map map, String key) {
        if (map.containsKey(key)) {    // map안에 key가 있다면 value 증가
            return (int) map.get(key) + 1;
        } else {                        // map안에 key가 없다면 최초 삽입
            return 1;
        }
    }

    public Map analysis(ArrayList<ApiModel> array) {

        Map<String, Map> dataMap = new HashMap<>();        // 최종 output data
        Map<String, Integer> hostMap = new HashMap<>();    // 목적지 host 종류 별 data
        Map<String, Integer> portMap = new HashMap<>();    // port 종류 별 data
        Map<String, Map> apiMap = new HashMap<>();         // api 종류 별 data
        Map<String, Integer> dagsMap = new HashMap<>();    // dags server 별 data
        Map<String, Integer> codeMap = new HashMap<>();    // http status code 별 data
        Map<String, String> urlMap;                        // url을 split 한 후 data

        for (int i = 0; i < array.size(); i++) {
            String url = array.get(i).getUrl();                      // ex) https://inbound-admin.daouoffice.com:8443/api/setting/service/domain/save
            urlMap = splitUrl(url);                                  // ex) {destinationHost=inbound-admin.daouoffice.com, port=8443, apiType=/api/setting/service/domain/save}
            String destinationHost = urlMap.get("destinationHost");  // ex) inbound-admin.daouoffice.com
            String port = urlMap.get("port");                        // ex) 8443
            String apiType = urlMap.get("apiType");                  // ex) /api/setting/service/domain/save
            String code = array.get(i).getCode();                    // ex) 400
            String dags = array.get(i).getFolder();                  // ex) 1


            //  목적지 host 별
            int count = counter(hostMap, destinationHost);
            hostMap.put(destinationHost, count);

            //  port number 별
            count = counter(portMap, port);
            portMap.put(port, count);

            //  http status code 별
            count = counter(codeMap, code);
            codeMap.put(code, count);

            //  dags server 별
            count = counter(dagsMap, dags);
            dagsMap.put(dags, count);

            //  api 종류 별
            if (apiMap.containsKey(apiType)) {
                count = (int) apiMap.get(apiType).get("count") + 1;
                apiMap.get(apiType).put("count", count);
            } else {
                Map<String, Integer> apiInfo = new HashMap<>();
                apiInfo.put("count", 1);
                apiMap.put(apiType, apiInfo);
            }

        }

        // 최종 통계 자료 산출 후 삽입
        dataMap.put("api", apiMap);
        dataMap.put("port", portMap);
        dataMap.put("destinationHost", hostMap);
        dataMap.put("httpStatusCode", codeMap);
        dataMap.put("dagServer", dagsMap);

        return getAvgTime(array, dataMap);
    }

}

// 추후 디버깅용 Map print 함수
//    public static void mapPrint(Map dataMap){
//        for( Object key : dataMap.keySet() ){
//            System.out.println("["+key + " 종류별 건수 통계]");
//            Map subMap = (Map)dataMap.get(key);
//            for( Object subKey : subMap.keySet()){
//                System.out.println(subKey + " : " + subMap.get(subKey));
//            }
//            System.out.println("---------------------------------------------------------------");
//
//        }
//    }
