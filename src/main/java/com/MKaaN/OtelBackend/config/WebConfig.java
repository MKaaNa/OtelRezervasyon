package com.MKaaN.OtelBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Tüm endpointlere CORS izin verir
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Frontend adresi (Angular)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metodları
                .allowedHeaders("*"); // İzin verilen başlıklar
    }
}