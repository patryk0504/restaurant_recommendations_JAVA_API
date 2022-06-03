package com.project.ZTI.controller;

import com.project.ZTI.model.Cuisine;
import com.project.ZTI.model.Restaurant;
import com.project.ZTI.repository.RestaurantRepository;
import com.project.ZTI.request.AddRestaurantRequest;
import com.project.ZTI.service.RestaurantAdministrationService;
import com.project.ZTI.service.RestaurantPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = {"http://localhost:7777", "http://localhost:3000"})
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class RestaurantAdministrationController {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantPropertiesService restaurantPropertiesService;
    private final RestaurantAdministrationService restaurantAdministrationService;

    public RestaurantAdministrationController(RestaurantRepository restaurantRepository,
                                              RestaurantPropertiesService restaurantPropertiesService,
                                              RestaurantAdministrationService restaurantAdministrationService){
        this.restaurantRepository = restaurantRepository;
        this.restaurantPropertiesService =restaurantPropertiesService;
        this.restaurantAdministrationService = restaurantAdministrationService;
    }

    @PostMapping("/restaurant")
    public ResponseEntity<?> addNewRestaurant(@RequestBody Map<String, AddRestaurantRequest> restaurantRequest){
        AddRestaurantRequest restaurant = restaurantRequest.get("data");
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurant.getName());
        newRestaurant.setAddress(restaurant.getAddress());
        newRestaurant.setLocation(restaurantPropertiesService.getLocationById(restaurant.getLocation()));
        newRestaurant.setAddress_latitude(restaurant.getAddress_latitude());
        newRestaurant.setAddress_longitude(restaurant.getAddress_longitude());

        Set<Cuisine> cuisineSet = new HashSet<>();
        restaurant.getCuisines().forEach((r)->{
            cuisineSet.add(restaurantPropertiesService.getCuisineById(r));
        });

        newRestaurant.setCuisines(cuisineSet);

        return new ResponseEntity<>(restaurantRepository.save(newRestaurant), HttpStatus.OK);
    }

    @DeleteMapping("restaurant/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId){
        restaurantAdministrationService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
