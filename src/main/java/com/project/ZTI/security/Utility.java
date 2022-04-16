package com.project.ZTI.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.ZTI.models.user.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

public class Utility {
    private final static String jwtSecret = "secretKey";
    private final static Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

    private Utility(){}

    public static Algorithm getAlgorithm(){
        return algorithm;
    }
    public static String generateAccessToken(HttpServletRequest request, User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Utility.getAlgorithm());
    }

    public static String generateAccessToken(HttpServletRequest request, com.project.ZTI.models.user.User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()))
                .sign(Utility.getAlgorithm());
    }

    public static String generateRefreshToken(HttpServletRequest request, User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(Utility.getAlgorithm());
    }
}
