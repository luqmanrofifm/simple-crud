package com.example.simple_crud.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        OAuthFlow passwordFlow = new OAuthFlow()
                .tokenUrl("http://localhost:7878/api/v1/auth/token");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows().password(passwordFlow))
                .name("OAuth2PasswordBearer");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("OAuth2PasswordBearer", securityScheme))
                .info(new Info()
                        .title("Simple CRUD API")
                        .version("1.0")
                        .description("Documentation for Simple CRUD API"));
    }
}
