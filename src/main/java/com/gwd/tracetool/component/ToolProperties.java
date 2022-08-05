package com.gwd.tracetool.component;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ToolProperties {
    @Value("${tools.log-path.base}")
    private String logBasePath;

    @Value("${tools.log-path.dags1}")
    private String dags1LogPath;

    @Value("${tools.log-path.dags2}")
    private String dags2LogPath;

    @Value("${tools.log-path.dems1}")
    private String dems1LogPath;

    @Value("${tools.log-path.dems2}")
    private String dems2LogPath;
}
