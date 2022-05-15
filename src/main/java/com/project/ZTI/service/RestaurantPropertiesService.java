package com.project.ZTI.service;

import com.project.ZTI.exception.CuisineNotFoundException;
import com.project.ZTI.exception.LocationNotFoundException;
import com.project.ZTI.model.Cuisine;
import com.project.ZTI.model.Location;
import com.project.ZTI.repository.CuisineRepository;
import com.project.ZTI.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantPropertiesService {
    private final LocationRepository locationRepository;
    private final CuisineRepository cuisineRepository;

    public RestaurantPropertiesService(LocationRepository locationRepository, CuisineRepository cuisineRepository){
        this.locationRepository = locationRepository;
        this.cuisineRepository = cuisineRepository;
    }

    public Location getLocationById(Long locationId){
        return locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    public Cuisine getCuisineById(Long cuisineId){
        return cuisineRepository.findById(cuisineId).orElseThrow(() -> new CuisineNotFoundException(cuisineId));
    }

    public List<Location> getAllLocations(){
        log.info("Get all locations");
        Sort sort = Sort.by("name").ascending();
        return locationRepository.findAll(sort);
    }
    public List<Cuisine> getAllCuisines(){
        log.info("Get all locations");
        Sort sort = Sort.by("name").ascending();
        return cuisineRepository.findAll(sort);
    }
}
