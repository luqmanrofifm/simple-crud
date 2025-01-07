package com.example.simple_crud.constant;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class CurrentUser {
    private String email;
    private String userId;

    public void set(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }
}
