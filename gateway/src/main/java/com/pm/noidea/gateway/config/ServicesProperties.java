package com.pm.noidea.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "services")
public class ServicesProperties {

    private String identityServiceUri;
    private String movieCatalogServiceUri;
    private String activityQueryServiceUri;
    private String activityCommandServiceUri;
}
