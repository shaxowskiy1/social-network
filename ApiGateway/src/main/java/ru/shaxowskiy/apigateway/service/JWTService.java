package ru.shaxowskiy.apigateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    private final String issuer = "cloud_filestorage";
    @Value("${jwt.secret}")
    private String secret;
    private final String subject = "User details";

    public DecodedJWT validateToken(String token){
        JWTVerifier verifyingToken = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();
        return verifyingToken.verify(token);
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getClaim("username").asString();
    }
}
