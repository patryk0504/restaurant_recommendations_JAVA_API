package com.project.ZTI.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ZTI.model.user.ERole;
import com.project.ZTI.model.user.Role;
import com.project.ZTI.model.user.User;
import com.project.ZTI.repository.RoleRepository;
import com.project.ZTI.request.LoginRequest;
import com.project.ZTI.request.RoleToUserRequest;
import com.project.ZTI.security.AuthUtility;
import com.project.ZTI.service.UserAdministrationService;
import com.project.ZTI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class UserAdministrationController {
    private final UserAdministrationService userAdministrationService;

    public UserAdministrationController(UserAdministrationService userAdministrationService) {
        this.userAdministrationService = userAdministrationService;
    }

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userAdministrationService.getUsers());
    }


    @Operation(summary = "Add new role")
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userAdministrationService.saveRole(role));
    }

    @Operation(summary = "Assign role to user")
    @PutMapping("/role/assign")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserRequest roleToUserRequest) {
        userAdministrationService.addRoleToUser(roleToUserRequest.getUsername(), roleToUserRequest.getRole());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove admin role from user")
    @PutMapping("/role/cancel")
    public ResponseEntity<?> cancelRoleFromUser(@RequestBody Map<String,String> username, HttpServletRequest request) {
        userAdministrationService.cancelRoleFromUser(username.get("username"), request);
        return ResponseEntity.noContent().build();
    }
}