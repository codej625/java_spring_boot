package com.template.springboot.config;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {

    // properties 파일에서 선언하여 사용
    @Value("${value.cloud.key}")
    private String key;

}