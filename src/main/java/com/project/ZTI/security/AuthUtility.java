package com.project.ZTI.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.ZTI.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class AuthUtility {
    private final String jwtSecret = "secretKey";
    private final Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
    private final UserService userService;

    @Autowired
    public AuthUtility(UserService userService){
        this.userService = userService;
    }


    public Algorithm getAlgorithm(){
        return algorithm;
    }
    public String generateAccessToken(HttpServletRequest request, User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public String generateAccessToken(HttpServletRequest request, com.project.ZTI.model.user.User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(HttpServletRequest request, User user){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 120*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(getAlgorithm());
    }

    public com.project.ZTI.model.user.User getUserFromAccessToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                //verify token
                JWTVerifier verifier = JWT.require(getAlgorithm()).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                return userService.getUser(username);
            } catch (Exception e) {
                log.error("Cannot get user from JWT token");
            }
        }else{
            throw new RuntimeException("Access token not provided");
        }
        return null;
    }
}
