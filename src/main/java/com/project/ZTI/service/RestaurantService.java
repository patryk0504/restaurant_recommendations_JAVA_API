package com.project.ZTI.service;

import com.project.ZTI.exception.RestaurantNotFoundException;
import com.project.ZTI.model.Restaurant;
import com.project.ZTI.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository =restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        log.info("Get all restaurants");
        Pageable firstPageWithFiveElements = PageRequest.of(0, 5);
        return restaurantRepository.findTopBy(firstPageWithFiveElements);
    }

    public List<Restaurant> getRestaurantByLocation(Long id) {
        log.info("Get all restaurants by location: " + id);
        return restaurantRepository.findRestaurantByLocationId(id);
    }

    public Restaurant getRestaurant(Long restaurantId) {
        log.info("Get restaurants by id");
        return restaurantRepository.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
}
