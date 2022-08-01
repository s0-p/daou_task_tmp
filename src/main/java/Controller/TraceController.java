package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraceController {

    @GetMapping("/api/analyze/trace-log")
    public String read(Model model, String data){
        // String data; (get에서 들어온 날짜 인자값)
        // [GET] http://localhost:8080/api/analyze/trace-log?date=20220722
            return "";
    }
}
