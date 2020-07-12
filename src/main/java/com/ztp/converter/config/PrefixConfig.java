package com.ztp.converter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "prefix")
public class PrefixConfig {
    private String boutiquePrefix;
    private String productDetailPrefix;
    private String searchPrefix;
}
