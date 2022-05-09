package com.project.ZTI.service;

import com.project.ZTI.models.Cuisine;
import com.project.ZTI.models.Location;
import com.project.ZTI.repository.CuisineRepository;
import com.project.ZTI.repository.LocationRepository;
import lombok.AllArgsConstructor;
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
