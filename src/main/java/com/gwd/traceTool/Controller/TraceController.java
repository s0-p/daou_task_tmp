package com.gwd.traceTool.Controller;

import com.gwd.traceTool.domain.ApiModel;
import com.gwd.traceTool.domain.FileDateDTO;
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
    public String home(){
        String path = "dags_feign.2022-07-14.log";

        apiParser = new ApiParser();
        apiParser.readApi(path);

        return "home";
    }


    @PostMapping("/api/analyze/trace-log")
    public String read(FileDateDTO fileDateDTO, Model model){
        // String data; (get에서 들어온 날짜 인자값)
        // [GET] http://localhost:8080/api/analyze/trace-log?date=20220722
        String fileDate = fileDateDTO.getFileDate();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!date : "+fileDate);
        //String fileDate = "dags_feign."+date.substring(0, 3) + '-' + date.substring(4, 5) + '-' + date.substring(6, 7) + ".log";
        String fileName = "dags_feign." + fileDate+".log";
        ArrayList<ApiModel> arr = apiParser.readApi(fileName);
        model.addAttribute("arr",arr);
            return "apiPost";
    }
}
