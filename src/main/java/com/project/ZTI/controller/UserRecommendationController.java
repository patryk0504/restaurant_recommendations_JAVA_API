package com.project.ZTI.controller;

import com.project.ZTI.repository.UsersWithParametersProjection;
import com.project.ZTI.service.UserRecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:7777", "http://localhost:3000"})
@RestController
@RequestMapping("/api")
@Slf4j
public class UserRecommendationController {
    private final UserRecommendationService userRecommendationService;

    public UserRecommendationController(UserRecommendationService userRecommendationService){
        this.userRecommendationService = userRecommendationService;
    }

    @Operation(summary = "Get similar users")
    @GetMapping("/user/recommendations")
    public ResponseEntity<List<UsersWithParametersProjection>> getUsers(HttpServletRequest request){
        return new ResponseEntity<>(userRecommendationService.getUsers(request), HttpStatus.OK);
    }
}
