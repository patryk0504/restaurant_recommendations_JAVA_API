package com.project.ZTI.controller;

import com.project.ZTI.response.RecommendationsByRestaurantResponse;
import com.project.ZTI.response.RecommendationsByUserResponse;
import com.project.ZTI.model.Restaurant;
import com.project.ZTI.service.RestaurantRecommendationsService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get restaurant recommendation by city")
    @GetMapping(path = {"/restaurant/{restaurantId}/recommendations/city"})
    public ResponseEntity<List<RecommendationsByRestaurantResponse>>
    getRestaurantRecommendationsByCity(@PathVariable(name = "restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsByCity(restaurantId), HttpStatus.OK);
    }

    @Operation(summary = "Get restaurant recommendations all cities")
    @GetMapping(path = {"/restaurant/{restaurantId}/recommendations/country"})
    public ResponseEntity<List<RecommendationsByRestaurantResponse>>
    getRestaurantRecommendationsAllCities(@PathVariable(name = "restaurantId") Long restaurantId) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsAllCities(restaurantId), HttpStatus.OK);
    }

    @Operation(summary = "Get restaurant recommendations by users activity")
    @GetMapping(path = {"/restaurant/recommendations/user"})
    public ResponseEntity<List<RecommendationsByUserResponse>>
    getRestaurantRecommendationsByUser(HttpServletRequest request) {
        return new ResponseEntity<>(restaurantRecommendationsService.getRestaurantRecommendationsByUser(request), HttpStatus.OK);
    }
}
