package com.template.springboot.config;

import com.template.springboot.interceptor.SessionInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()) // 사용할 Interceptor 지정

            .addPathPatterns("/**") // 특정 URL 패턴에 대해 인터셉터를 적용

            // Static resource
            .excludePathPatterns("/config/**") // 특정 URL 패턴에 대해 인터셉터를 제외
            .excludePathPatterns("/static/**")
            .excludePathPatterns("/templates/**");
    }
}