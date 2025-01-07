package com.example.simple_crud.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtUtils {
    public static DecodedJWT verifyToken(String token, String secretKey){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    public static String createTokenJwt(Map<String,String> payload, String secretKey, Integer tokenLifeTime){
        long expiration = TimeUnit.MINUTES.toMillis((long)tokenLifeTime);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create().withPayload(payload).withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(algorithm);
    }
}
