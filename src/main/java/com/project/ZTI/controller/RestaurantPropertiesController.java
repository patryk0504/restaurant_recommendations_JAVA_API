package com.project.ZTI.controller;

import com.project.ZTI.model.Cuisine;
import com.project.ZTI.model.Location;
import com.project.ZTI.service.RestaurantPropertiesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantPropertiesController {

    private final RestaurantPropertiesService restaurantPropertiesService;

    @Autowired
    public RestaurantPropertiesController(RestaurantPropertiesService restaurantPropertiesService){
        this.restaurantPropertiesService = restaurantPropertiesService;
    }

    @Operation(summary = "Get all locations")
    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations(){
        return new ResponseEntity<>(restaurantPropertiesService.getAllLocations(), HttpStatus.OK);
    }

    @Operation(summary = "Get all cuisines")
    @GetMapping("/cuisines")
    public ResponseEntity<List<Cuisine>> getAllCuisines(){
        return new ResponseEntity<>(restaurantPropertiesService.getAllCuisines(), HttpStatus.OK);
    }
}
