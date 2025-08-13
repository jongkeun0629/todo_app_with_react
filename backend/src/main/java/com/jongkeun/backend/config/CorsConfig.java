package com.jongkeun.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/**")  // 허용 url. api 전부
                .allowedOrigins("http://localhost:5173")    // 도메인 구분
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")  // 허용 메소드. options: 메소드 요청 전 사전 요청
                .allowedHeaders("*")    // 모든 헤더 허용
                .allowCredentials(true) // 인증 정보 포함
                .maxAge(3600);  // 요청 후 캐시 1시간 유지
    }
}
