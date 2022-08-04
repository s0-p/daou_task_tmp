package com.gwd.tracetool.domain;
import lombok.ToString;

import java.time.LocalDateTime;


@ToString
public class ApiModel implements Comparable<ApiModel>{
    private LocalDateTime occurrence_time;
    private String folder;
    private String url;
    private String code;
    private String time;
    private String message;
    private String body;

    public LocalDateTime getOccurrence_time() {
        return occurrence_time;
    }

    public void setOccurrence_time(LocalDateTime occurrence_time) {
        this.occurrence_time = occurrence_time;
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
        return this.occurrence_time.compareTo(o.getOccurrence_time());
    }
}
