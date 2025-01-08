package com.example.simple_crud.auth;

import com.example.simple_crud.configurations.JwtConfig;
import com.example.simple_crud.exceptions.ResourceNotFoundException;
import com.example.simple_crud.exceptions.UnauthorizedException;
import com.example.simple_crud.user.User;
import com.example.simple_crud.user.UserRepository;
import com.example.simple_crud.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;

    protected AuthService(UserRepository userRepository, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
    }

    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
        user.setEmail(registerRequest.getEmail());
        user.setCreatedDate(new Date());
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = this.authenticate(loginRequest.email, loginRequest.password);
        return this.createToken(user);
    }

    public Map<String, String> setPayloadToken(User user) {
        Map<String, String> tokenPayload = new HashMap<>();
        tokenPayload.put("email", user.getEmail());
        tokenPayload.put("user_id", user.getId().toString());
        return tokenPayload;
    }

    public LoginResponse createToken(User user) {
        Map<String, String> tokenPayload = this.setPayloadToken(user);
        String token = JwtUtils.createTokenJwt(tokenPayload, this.jwtConfig.getAuthTokenSecret(), this.jwtConfig.getAuthTokenLifetime());
        return new LoginResponse(token);
    }

    private User authenticate(String email, String password) {
        User user  = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        } else if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new UnauthorizedException("Failed to authenticate user");
        } else {
            return user;
        }
    }
}
