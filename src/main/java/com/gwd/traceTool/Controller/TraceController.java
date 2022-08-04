package com.gwd.traceTool.Controller;

import com.gwd.traceTool.domain.ApiModel;
import com.gwd.traceTool.domain.FileDateDTO;
import com.gwd.traceTool.method.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class TraceController {
    EventParser eventParser;
    ApiParser apiParser;
    ApiAnalysis apiAnalysis;

    Map<String, Map> dataMap;

    @GetMapping("/api/analyze/trace-log")

    public String home() {
        /*String path = "dags_feign.2022-07-14.log";
        apiParser = new ApiParser();
        apiParser.readApi(path);*/
        String path = "dems.2022-07-14.log";
        eventParser = new EventParser();
//        try {
//            eventParser.readEvent(path);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }


        return "home";
    }


    @PostMapping("/api/analyze/trace-log")
    public String read(FileDateDTO fileDateDTO, Model model) {
        String fileDate = fileDateDTO.getFileDate();  // POST request로 받은 date input value
        String fileName = "dags_feign." + fileDate + ".log";


        ArrayList<ApiModel> array = apiParser.readApi(fileName);
        ApiAnalysis apiAnalysis = new ApiAnalysis();
        Map dataMap = apiAnalysis.analysis(array);
        /* 
        model.addAttribute("dataMap",dataMap);
            return "apiPost";

         */
        model.addAttribute("dataMap", dataMap);
        return "statics";
    }
}
