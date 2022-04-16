package com.project.ZTI.controller;

import com.project.ZTI.models.Restaurant;
import com.project.ZTI.repository.RestaurantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:7000")
@RestController
@RequestMapping("/api")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }


}
