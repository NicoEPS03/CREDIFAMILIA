package com.prueba.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Solicitud de CREDIFAMILIA API",
                version = "1.0",
                description = "Documentación API CREDIFAMILIA"
        )
)
public class SwaggerConfig {
}
