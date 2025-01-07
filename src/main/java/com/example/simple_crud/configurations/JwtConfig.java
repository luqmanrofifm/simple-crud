package com.example.simple_crud.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class JwtConfig {
    @Value("${jwt.auth.secret.key}")
    private String authTokenSecret;
    @Value("60")
    private Integer authTokenLifetime;

}
