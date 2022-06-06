package com.project.ZTI.controller;

import com.project.ZTI.model.Rates;
import com.project.ZTI.model.Restaurant;
import com.project.ZTI.request.AddRestaurantRequest;
import com.project.ZTI.response.RestaurantRateByUsersResponse;
import com.project.ZTI.service.RestaurantRatesService;
import com.project.ZTI.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Operation(summary = "Get page of restaurants")
    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(Pageable pageable) {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get restaurants in given location")
    @GetMapping("/restaurants/locations/{id}")
    public ResponseEntity<List<Restaurant>> getRestaurantByLocation(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getRestaurantByLocation(id), HttpStatus.OK);
    }

    @Operation(summary = "Get restaurant details")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable(name = "restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurant(restaurantId), HttpStatus.OK);
    }

    @Operation(summary = "Get restaurant rating")
    @GetMapping("/restaurant/{restaurantId}/rate")
    public ResponseEntity<Map<String, Integer>> getRestaurantRate(@PathVariable(name = "restaurantId") Long restaurantId,
                                                                  HttpServletRequest request) {
        return new ResponseEntity<>(restaurantRatesService.getRestaurantRate(restaurantId, request), HttpStatus.OK);
    }

    @Operation(summary = "Rate restaurant")
    @PutMapping("/restaurant/{restaurantId}/rate/{rate}")
    public ResponseEntity<?> rateRestaurant(@PathVariable(name = "restaurantId") Long restaurantId,
                                            @PathVariable(name = "rate") int rate,
                                            HttpServletRequest request) {
        restaurantRatesService.rateRestaurant(restaurantId, rate, request);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get restaurant comments")
    @GetMapping("/restaurant/{restaurantId}/comment")
    public ResponseEntity<Map<String, String>> getRestaurantComment(@PathVariable(name="restaurantId") Long restaurantId,
                                                                    HttpServletRequest request){
        return new ResponseEntity<>(restaurantRatesService.getRestaurantComment(restaurantId, request), HttpStatus.OK);

    }

    @Operation(summary = "Get restaurant rates")
    @GetMapping("/restaurant/{restaurantId}/rates/all")
    public ResponseEntity<List<RestaurantRateByUsersResponse>> getAllRestaurantRates(@PathVariable(name="restaurantId") Long restaurantId,
                                                                                     HttpServletRequest request){
        return new ResponseEntity<>(restaurantRatesService.getAllRestaurantRates(restaurantId), HttpStatus.OK);

    }

    @Operation(summary = "Comment restaurant")
    @PutMapping("/restaurant/{restaurantId}/comment")
    public ResponseEntity<?> commentRestaurant(@PathVariable(name = "restaurantId") Long restaurantId,
                                            @RequestBody Map<String, String> comment,
                                            HttpServletRequest request) {
        restaurantRatesService.commentRestaurant(restaurantId, comment.get("comment"), request);
        return ResponseEntity.noContent().build();
    }
}
