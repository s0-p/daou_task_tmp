package method;

import domain.DagsModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DagsLogPreprocessing {
    public static DagsModel createDagsModel(String subStr) {
        DagsModel dagsModel = new DagsModel();
        List<String> tmpList;

        tmpList = List.of(subStr.split(", code:"));
        dagsModel.setUrl(tmpList.get(0).substring(4));

        tmpList = List.of(tmpList.get(1).split(",|:"));
        dagsModel.setCode(tmpList.get(0));
        dagsModel.setTime(tmpList.get(2));
        dagsModel.setMessage(tmpList.get(4));
        dagsModel.setBody(tmpList.get(6));

        return dagsModel;
    }

    public static ArrayList readDags(String path) {
        ArrayList<DagsModel> list = new ArrayList<DagsModel>();
        File file = new File(path);
        String queueRecord = "[Feign Response] ";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(queueRecord)) {

                    int i = line.indexOf(queueRecord) + queueRecord.length();
                    if (i != -1) {
                        String subStr = line.substring(i);
                        list.add(createDagsModel(subStr));
                    }
                }
            }
            for(Object x : list){
                System.out.println(x.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
