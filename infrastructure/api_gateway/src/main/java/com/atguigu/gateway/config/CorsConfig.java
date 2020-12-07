package com.atguigu.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

    // 解决跨域
    @Bean
    public CorsWebFilter corsWebFilter(){

        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source =  new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**",corsConfig);

        return new CorsWebFilter(source);

    }


}
