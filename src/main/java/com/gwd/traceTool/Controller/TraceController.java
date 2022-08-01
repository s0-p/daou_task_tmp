package com.gwd.traceTool.Controller;

import com.gwd.traceTool.method.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class TraceController {
    EventParser eventParser;
    ApiParser apiParser;

    @GetMapping("/api/analyze/trace-log")
    public String home(Model model){
        String path = "C:/Users/User/Desktop/인턴과제_자료_20220722/dags1/dags_feign.2022-07-14.log";



        apiParser = new ApiParser();
        apiParser.readApi(path);

        return "home";
    }


    @PostMapping("/api/analyze/trace-log")
    public String read(Model model, String data){
        // String data; (get에서 들어온 날짜 인자값)
        // [GET] http://localhost:8080/api/analyze/trace-log?date=20220722
            return "";
    }
}
