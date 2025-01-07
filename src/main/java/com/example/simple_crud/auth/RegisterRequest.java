package com.example.simple_crud.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    public String email;
    public String password;
    public String name;
}
