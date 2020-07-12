package com.ztp.converter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "url")
public class UrlConfig {
    private String webHomePage;
    private String deepHomePage;
    private String baseWebSearchLink;
    private String baseWebBoutiqueLink;
    private String baseSearchDeepLink;
    private String baseProductDetailDeepLink;
    private String baseBoutiqueDeepLink;
}
