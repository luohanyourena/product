package com.letter.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * create:luohan
 */
@Data
@ConfigurationProperties("girl")
@Component
@RefreshScope
public class GirlConfig {
    private String name;
    private String age;
}
