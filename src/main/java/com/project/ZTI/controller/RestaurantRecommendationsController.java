package com.project.ZTI.controller;

import com.project.ZTI.models.RecommendationsByRestaurantHelper;
import com.project.ZTI.models.RecommendationsByUserHelper;
import com.project.ZTI.models.Restaurant;
import com.project.ZTI.service.RestaurantRecommendationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RestaurantRecommendationsController {

    private final RestaurantRecommendationsService restaurantRecommendationsService;

    public RestaurantRecommendationsController(RestaurantRecommendationsService restaurantRecommendationsService){
        this.restaurantRecommendationsService = restaurantRecommendationsService;
    }

    @GetMapping(path = {"/restaurant/{restaurantId}/recommendations/city"})
    public ResponseEntity<List<RecommendationsByRestaurantHelper>>
    getRestaurantRecommendationsByCity(@PathVariable(name = "restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsByCity(restaurantId), HttpStatus.OK);
    }

    @GetMapping(path = {"/restaurant/{restaurantId}/recommendations/country"})
    public ResponseEntity<List<RecommendationsByRestaurantHelper>>
    getRestaurantRecommendationsAllCities(@PathVariable(name = "restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsAllCities(restaurantId), HttpStatus.OK);
    }

    @GetMapping(path = {"/restaurant/recommendations/user"})
    public ResponseEntity<List<RecommendationsByUserHelper>>
    getRestaurantRecommendationsByUser(HttpServletRequest request) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsByUser(request), HttpStatus.OK);
    }

    @GetMapping("/restaurant/recommendations/rating")
    public ResponseEntity<List<Restaurant>> getRestaurantRecommendationsByRating(HttpServletRequest request) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsByRating(request), HttpStatus.OK);
    }
}