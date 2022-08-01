package daou.daou_task.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

import static daou.daou_task.method.DagsLogPreprocessing.readDags;

@Controller
public class TraceController {

    @GetMapping("/api/analyze/trace-log")
    public String read(Model model, String date){
        // String date; (get에서 들어온 날짜 인자값)
        // [GET] http://localhost:8080/api/analyze/trace-log?date=20220722

        String fileDate = date.substring(0, 3) + '-' + date.substring(4, 5) + '-' + date.substring(6, 7) + ".log";

        //String path = "C:/Users/User/Desktop/log/dags1/dags_feign.2022-07-14.log";

        String path = "dags_feign.2022-07-14.log";
        String fileType = "dags";
        ArrayList resultArray = readDags(path);

        return "";
    }
}
