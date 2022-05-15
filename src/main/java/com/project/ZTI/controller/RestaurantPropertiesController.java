package com.project.ZTI.controller;

import com.project.ZTI.model.Cuisine;
import com.project.ZTI.model.Location;
import com.project.ZTI.service.RestaurantPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class RestaurantPropertiesController {
    private final RestaurantPropertiesService restaurantPropertiesService;

    public RestaurantPropertiesController(RestaurantPropertiesService restaurantPropertiesService){
        this.restaurantPropertiesService = restaurantPropertiesService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations(){
        return new ResponseEntity<>(restaurantPropertiesService.getAllLocations(), HttpStatus.OK);
    }

    @GetMapping("/cuisines")
    public ResponseEntity<List<Cuisine>> getAllCuisines(){
        return new ResponseEntity<>(restaurantPropertiesService.getAllCuisines(), HttpStatus.OK);
    }
}
