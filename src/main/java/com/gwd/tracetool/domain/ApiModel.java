package com.gwd.tracetool.domain;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;


@ToString
public class ApiModel implements Comparable<ApiModel> {
    private LocalDateTime occurrenceTime;
    private String folder;
    private String url;
    private String code;
    private String time;
    private String message;
    private String body;

    public LocalDateTime getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(LocalDateTime occurrence_time) {
        this.occurrenceTime = occurrence_time;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int compareTo(ApiModel o) {
        return this.occurrenceTime.compareTo(o.getOccurrenceTime());
    }
}
