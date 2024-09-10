package com.example.clushapi.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("크러쉬 Todo API 캘린더 API 과제")
                        .description("Todo의 CRUD와 캘린더의 CRUD 등의 기능을 제공합니다.")
                        .version("1.0.0"));
    }
}
