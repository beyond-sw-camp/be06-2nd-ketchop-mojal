package com.example.mojal2ndproject2.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500") // 프론트엔드가 호스팅되는 도메인
                        .allowedOrigins("http://localhost:5501")
                        .allowedOriginPatterns("*") //혹시 몰라 추가
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*") //0805추가
                        .allowCredentials(true); // 자격 증명(쿠키 등)을 허용 //0805추가
            }
        };
    }
}
