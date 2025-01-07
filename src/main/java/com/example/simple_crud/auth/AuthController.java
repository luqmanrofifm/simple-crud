package com.example.simple_crud.auth;

import com.example.simple_crud.common.Response;
import com.example.simple_crud.common.controller.BaseController;
import com.example.simple_crud.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Controller")
public class AuthController extends BaseController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping({"/login"})
    @Operation(security = {})
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = this.authService.login(loginRequest);
        return this.success(loginResponse, "Login Successful");
    }

    @PostMapping(value = {"/token"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(security = {})
    public ResponseEntity<Object> token(@RequestParam("username") String username, @RequestParam("password") String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username);
        loginRequest.setPassword(password);
        LoginResponse loginResponse = this.authService.login(loginRequest);

        Map<String,Object> data = new HashMap<>();
        data.put("access_token", loginResponse.getToken());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping({"/register"})
    @Operation(security = {})
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest registerRequest) {
        User user = this.authService.register(registerRequest);
        return this.success(user, "Register Successful");
    }
}
