package com.gwd.tracetool.controller;

import com.gwd.tracetool.domain.ApiModel;
import com.gwd.tracetool.domain.FileDateDTO;
import com.gwd.tracetool.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TraceController {
    EventParser eventParser;
    ApiParserService apiParser;
    ApiAnalysis apiAnalysis;
    @GetMapping("/api/analyze/trace-log")

    public String home(){
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
    public String read(FileDateDTO fileDateDTO, Model model){
        String fileDate = fileDateDTO.getFileDate();  // POST request로 받은 date input value
        String fileName = "dags_feign." + fileDate+".log";


        List<ApiModel> array = apiParser.readApi(fileName);
        apiAnalysis = new ApiAnalysis();
        apiAnalysis.analysis(array);
        // apiAnalysis.array는 나중에 사용가능
        model.addAttribute("array",array);
            return "apiPost";
    }
}
