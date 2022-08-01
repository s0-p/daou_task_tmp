package domain;
import lombok.ToString;


@ToString
public class ApiModel {
    private String url;
    private String code;
    private String time;
    private String message;
    private String body;


    public String getUrl() {
        return url;
    }

    public String getCode() {
        return code;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getBody() {
        return body;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
