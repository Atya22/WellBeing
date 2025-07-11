package com.aytaj.wellbeing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cache")
class CacheConfigProperties {
    private Long defaultTTL = 60L;
}