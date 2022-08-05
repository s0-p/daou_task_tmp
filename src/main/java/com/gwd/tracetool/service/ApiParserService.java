package com.gwd.tracetool.service;

import com.gwd.tracetool.component.ToolProperties;
import com.gwd.tracetool.domain.ApiModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiParserService {

    private final ToolProperties toolProperties;

    public ApiModel createApiModel(String subStr) {
        List<String> urlList = List.of(subStr.split(", code:"));
        List<String> statusList = List.of(urlList.get(1).split(",|:"));

        return ApiModel.builder()
                .url(urlList.get(0).substring(4))
                .code(Integer.valueOf(statusList.get(0)))
                .time(statusList.get(2))
                .message(statusList.get(4))
                .body(statusList.get(6))
                .build();
    }

    public List readApiLogFile(Path path, int logOffset) {
        List<ApiModel> subList = new ArrayList<ApiModel>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        File file = new File(path.toString());
        String queueRecord = "[Feign Response] ";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.contains(queueRecord)) {
                    continue;
                }

                int i = line.indexOf(queueRecord) + queueRecord.length();
                if (i != -1) {

                    LocalDateTime occurrenceTime = LocalDateTime.parse(line.substring(0, 23), formatter);
                    String subStr = line.substring(i);

                    ApiModel apiModel = createApiModel(subStr);
                    apiModel.setOccurrenceTime(occurrenceTime);
                    apiModel.setLogOffset(logOffset);
                    subList.add(apiModel);
                }
            }
        } catch (IOException e) {
            log.info("Fail to parse log : log-path={}, stack-trace={}", path, new Throwable().getStackTrace());
        }

        return subList;
    }

    private String generateLogfileName(String date) {
        // example : dags_feign.2022-07-14.log
        return String.format("dags_feign.%s.log", date);
    }

    public List readApi(String date) {
        int offset = 1;
        List<ApiModel> list = new ArrayList<ApiModel>();
        String fileName = generateLogfileName(date);

        List<Path> dagsLogDirPathList = new ArrayList<>(Arrays.asList(
                Paths.get(toolProperties.getDags1LogPath(), fileName),
                Paths.get(toolProperties.getDags2LogPath(), fileName)));

        for (Path logPath : dagsLogDirPathList) {
            list.addAll(readApiLogFile(logPath, offset++));
        }

        Collections.sort(list);
        return list;
    }
}
