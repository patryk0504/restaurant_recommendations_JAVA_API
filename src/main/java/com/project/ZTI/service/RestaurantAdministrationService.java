package com.project.ZTI.service;

import com.project.ZTI.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantAdministrationService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantAdministrationService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    public void deleteRestaurant(Long restaurantId){
        restaurantRepository.deleteById(restaurantId);
    }
}
