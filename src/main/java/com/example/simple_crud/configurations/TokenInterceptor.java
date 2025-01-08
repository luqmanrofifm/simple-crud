package com.example.simple_crud.configurations;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.simple_crud.constant.CurrentUser;
import com.example.simple_crud.exceptions.BaseException;
import com.example.simple_crud.exceptions.UnauthorizedException;
import com.example.simple_crud.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final CurrentUser currentUser;
    private final JwtConfig jwtConfig;

    public TokenInterceptor(CurrentUser currentUser, JwtConfig jwtConfig) {
        this.currentUser = currentUser;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String jwtToken = request.getHeader("Authorization");
        if (!ObjectUtils.isEmpty(jwtToken) && jwtToken.startsWith("Bearer ")) {
            try {
                String token = jwtToken.substring(7);
                DecodedJWT decodeToken = JwtUtils.verifyToken(token, jwtConfig.getAuthTokenSecret());
                String email = decodeToken.getClaim("email").asString();
                String userId = decodeToken.getClaim("user_id").asString();

                if (ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(userId)) {
                    throw new BaseException("Missing information in token");
                }

                currentUser.set(email, userId);
            } catch (Exception e){
                throw new UnauthorizedException("Unauthorized");
            }
        } else {
            throw new UnauthorizedException("Unauthorized");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
