package com.project.ZTI.security;

import com.project.ZTI.filter.CustomAuthenticationFilter;
import com.project.ZTI.filter.CustomAuthorizationFilter;
import com.project.ZTI.model.user.ERole;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };
//    we must create these beans in our app
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final CustomAuthenticationFilter customAuthenticationFilter;

    public SecurityConfig(com.project.ZTI.service.UserServiceImplementation userDetailsService,
                          PasswordEncoder passwordEncoder,
                          @Lazy CustomAuthorizationFilter customAuthorizationFilter,
                          @Lazy CustomAuthenticationFilter customAuthenticationFilter
    ){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.customAuthorizationFilter = customAuthorizationFilter;
        this.customAuthenticationFilter = customAuthenticationFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and();
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //user routes
        http.authorizeRequests().antMatchers("/login/**","/api/user/save/**", "/api/token/refresh/**").permitAll();

//        http.authorizeRequests().antMatchers("/api/login/**","/api/user/save/**", "/api/token/refresh/**").permitAll();
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll();

        http.authorizeRequests().antMatchers(GET, "/api/users").hasAnyAuthority(ERole.ROLE_ADMIN.name());
        http.authorizeRequests().antMatchers(POST, "/api/role").hasAnyAuthority(ERole.ROLE_ADMIN.name());
        http.authorizeRequests().antMatchers(PUT, "/api/role/assign").hasAnyAuthority(ERole.ROLE_ADMIN.name());
        //restaurant routes
        http.authorizeRequests().antMatchers(GET,
                "/api/restaurants/**",
                "/api/restaurant/**",
                "/api/locations",
                "/api/cuisines",
                "/api/restaurants/recommendations/**").hasAuthority(ERole.ROLE_USER.name());
        http.authorizeRequests().antMatchers(PUT,"/api/restaurant/**");
        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(customAuthenticationFilter);
//        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
