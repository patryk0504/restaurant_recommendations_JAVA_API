package com.project.ZTI.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ZTI.security.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private AuthenticationManager authenticationManager;
    private AuthUtility authUtility;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Autowired
    public void setAuthUtility(AuthUtility authUtility) {
        this.authUtility = authUtility;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            com.project.ZTI.models.user.User user = objectMapper.readValue(request.getInputStream(), com.project.ZTI.models.user.User.class);
            String username = user.getUsername();
            String password = user.getPassword();
            log.info("Username {} tries to login", username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            return super.getAuthenticationManager().authenticate(authenticationToken);
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
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        //get successfully auth user
        User user = (User) authentication.getPrincipal();
        //generate tokens
        String accessToken = authUtility.generateAccessToken(request, user);
        String refreshToken = authUtility.generateRefreshToken(request, user);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
