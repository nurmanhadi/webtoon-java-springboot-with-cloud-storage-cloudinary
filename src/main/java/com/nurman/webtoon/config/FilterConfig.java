package com.nurman.webtoon.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nurman.webtoon.helper.JwtAuthFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new JwtAuthFilter());
        bean.addUrlPatterns("/api/secure/*");
        return bean;
    }
}
