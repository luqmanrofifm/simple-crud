package com.example.simple_crud.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    public String username;
    public String password;
}
