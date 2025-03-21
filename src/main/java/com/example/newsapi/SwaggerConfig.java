package com.example.newsapi;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private static final String apiTitle = "Disaster Message & News API";
    private static final String apiVersion = "0.1.2";
    private static final String apiDescription = "재난 문자 & News API 명세서";

    @Bean
    public OpenAPI newsAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    public Info apiInfo() {
        return new Info()
                .title(apiTitle)
                .description(apiDescription)
                .version(apiVersion);
    }

}
