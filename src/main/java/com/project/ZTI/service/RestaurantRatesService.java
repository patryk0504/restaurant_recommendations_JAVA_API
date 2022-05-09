package com.project.ZTI.service;

import com.project.ZTI.exception.RateNotFoundException;
import com.project.ZTI.exception.RestaurantNotFoundException;
import com.project.ZTI.models.Rates;
import com.project.ZTI.models.Restaurant;
import com.project.ZTI.models.user.User;
import com.project.ZTI.repository.RestaurantRepository;
import com.project.ZTI.security.AuthUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class RestaurantRatesService {
    private final RestaurantRepository restaurantRepository;
    private final AuthUtility authUtility;

    public RestaurantRatesService(RestaurantRepository restaurantRepository, AuthUtility authUtility){
        this.restaurantRepository = restaurantRepository;
        this.authUtility = authUtility;
    }

    public Map<String, Integer> getRestaurantRate(Long restaurantId, HttpServletRequest request) throws IOException {
        User user = authUtility.getUserFromAccessToken(request);
        if (user != null) {
            Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId)
                    .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
            var tmp = restaurant.getRates().stream().filter(rates -> {
                return rates.getUser().getId().equals(user.getId());
            }).findFirst();
            Map<String, Integer> result = new HashMap<>();
            result.put("rating", tmp.orElseThrow(RateNotFoundException::new).getRating());
            return result;
        } else
            throw new RuntimeException("User not found");
    }

    @PutMapping("/restaurant/{restaurantId}/rate/{rate}")
    public Restaurant rateRestaurant(Long restaurantId, int rate, HttpServletRequest request) {
        User user = authUtility.getUserFromAccessToken(request);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        //to avoid duplicated relationship, first check if user has rate already
        List<Rates> userRates = restaurant.getRates().stream()
                .filter((r) -> {
                    return r.getUser().getId().equals(user.getId());
                }).collect(Collectors.toList());
        //create new rate
        if(userRates.isEmpty()){
            Rates newRate = new Rates(user, rate, null);
            restaurant.getRates().add(newRate);
            //only change rate value
        }else{
            userRates.get(0).setRating(rate);
        }
        return restaurantRepository.save(restaurant);
    }
}
