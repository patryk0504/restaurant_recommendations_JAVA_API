package com.project.ZTI.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ZTI.security.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

//    private final String jwtSecret = "secretKey";

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            com.project.ZTI.models.user.User user = objectMapper.readValue(request.getInputStream(), com.project.ZTI.models.user.User.class);
            String username = user.getUsername();
            String password = user.getPassword();
            log.info("Username {} tries to login", username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            return authenticationManager.authenticate(authenticationToken);
        }catch(IOException e){
            log.error(e.toString());
        }
        return null;
    }

    /**
     * Fired when attemptAuthentication function authorizes user.
     * @param response - here we pass auth token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //get successfully auth user
        User user = (User) authentication.getPrincipal();
        //generate tokens
        String accessToken = Utility.generateAccessToken(request, user);
        String refreshToken = Utility.generateRefreshToken(request, user);
        //send
        //in header
//        response.setHeader("access_token", accessToken);
//        response.setHeader("refresh_token", refreshToken);
        //in body
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
