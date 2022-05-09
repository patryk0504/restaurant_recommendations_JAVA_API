package com.project.ZTI.controller;

import com.project.ZTI.models.Restaurant;
import com.project.ZTI.service.RestaurantRatesService;
import com.project.ZTI.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:7777", "http://localhost:3000"})
@RestController
@RequestMapping("/api")
@Slf4j
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantRatesService restaurantRatesService;

    public RestaurantController(RestaurantService restaurantService,
                                RestaurantRatesService restaurantRatesService) {
        this.restaurantService = restaurantService;
        this.restaurantRatesService = restaurantRatesService;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/restaurants/locations/{id}")
    public ResponseEntity<List<Restaurant>> getRestaurantByLocation(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getRestaurantByLocation(id), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable(name = "restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurant(restaurantId), HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}/rate")
    public ResponseEntity<Map<String, Integer>> getRestaurantRate(@PathVariable(name = "restaurantId") Long restaurantId,
                                                                  HttpServletRequest request) throws IOException {
        return new ResponseEntity<>(restaurantRatesService.getRestaurantRate(restaurantId, request), HttpStatus.OK);
    }

    @PutMapping("/restaurant/{restaurantId}/rate/{rate}")
    public ResponseEntity<?> rateRestaurant(@PathVariable(name = "restaurantId") Long restaurantId,
                                            @PathVariable(name = "rate") int rate,
                                            HttpServletRequest request) {
        restaurantRatesService.rateRestaurant(restaurantId, rate, request);
        return ResponseEntity.noContent().build();
    }

}
