package com.gwd.tracetool.controller;

import com.gwd.tracetool.domain.ApiModel;
import com.gwd.tracetool.domain.FileDateDTO;
import com.gwd.tracetool.method.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class TraceController {
    ApiAnalysis apiAnalysis;
    Map<String, Map> dataMap;

    @GetMapping("/api/analyze/trace-log")

    public String home() {

        return "home";
    }


    @PostMapping("/api/analyze/trace-log")
    public String read(FileDateDTO fileDateDTO, Model model) {
        String fileDate = fileDateDTO.getFileDate();  // POST request로 받은 date input value
        String fileName = "dags_feign." + fileDate + ".log";  // file name 생성

        ArrayList<ApiModel> array = ApiParser.readApi(fileName);

        apiAnalysis = new ApiAnalysis();
        dataMap = apiAnalysis.analysis(array);

        model.addAttribute("dataMap", dataMap);
        return "statics";
    }
}
