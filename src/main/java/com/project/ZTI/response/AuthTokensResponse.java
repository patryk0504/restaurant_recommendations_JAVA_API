package com.project.ZTI.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class AuthTokensResponse {
    String access_token;
    String refresh_token;
    Collection<GrantedAuthority> roles;
}
