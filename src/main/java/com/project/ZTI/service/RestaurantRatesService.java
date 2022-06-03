package com.project.ZTI.service;

import com.project.ZTI.exception.CommentNotFoundException;
import com.project.ZTI.exception.RateNotFoundException;
import com.project.ZTI.exception.RestaurantNotFoundException;
import com.project.ZTI.exception.UserNotFoundException;
import com.project.ZTI.model.Rates;
import com.project.ZTI.model.Restaurant;
import com.project.ZTI.model.user.User;
import com.project.ZTI.repository.RestaurantRepository;
import com.project.ZTI.response.RestaurantRateByUsersResponse;
import com.project.ZTI.security.AuthUtility;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantRatesService {
    private final RestaurantRepository restaurantRepository;
    private final AuthUtility authUtility;

    public RestaurantRatesService(RestaurantRepository restaurantRepository, AuthUtility authUtility) {
        this.restaurantRepository = restaurantRepository;
        this.authUtility = authUtility;
    }

    public Map<String, Integer> getRestaurantRate(Long restaurantId, HttpServletRequest request) {
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
            throw new UserNotFoundException();
    }

    public Restaurant rateRestaurant(Long restaurantId, int rate, HttpServletRequest request) {
        User user = authUtility.getUserFromAccessToken(request);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        //to avoid duplicated relationship, first check if user has rate already
        List<Rates> userRates = restaurant.getRates().stream()
                .filter((r) -> {
                    return r.getUser().getId().equals(user.getId());
                }).collect(Collectors.toList());
        //create new rate
        if (userRates.isEmpty()) {
            Rates newRate = new Rates(user, rate, null);
            restaurant.getRates().add(newRate);
            //only change rate value
        } else {
            userRates.get(0).setRating(rate);
        }
        return restaurantRepository.save(restaurant);
    }

    public Map<String, String> getRestaurantComment(Long restaurantId, HttpServletRequest request) {
        User user = authUtility.getUserFromAccessToken(request);
        if (user != null) {
            Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId)
                    .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
            var tmp = restaurant.getRates().stream().filter(rates -> {
                return rates.getUser().getId().equals(user.getId());
            }).findFirst();
            Map<String, String> result = new HashMap<>();
            result.put("comment", tmp.orElseThrow(CommentNotFoundException::new).getComment());
            return result;
        } else
            throw new UserNotFoundException();
    }

    public List<RestaurantRateByUsersResponse> getAllRestaurantRates(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        var rates = restaurant.getRates();
        List<RestaurantRateByUsersResponse> restaurantRateByUsersResponsesList = new ArrayList<>();
        rates.forEach(
                (rate) -> {
                    restaurantRateByUsersResponsesList.add(
                            new RestaurantRateByUsersResponse(
                                    rate.getUser().getUsername(),
                                    rate.getComment(),
                                    rate.getRating()
                            )
                    );
                }
        );
        if(restaurantRateByUsersResponsesList.isEmpty())
            throw new CommentNotFoundException();
        return restaurantRateByUsersResponsesList;
    }

    public Restaurant commentRestaurant(Long restaurantId, String comment, HttpServletRequest request) {
        User user = authUtility.getUserFromAccessToken(request);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        //to avoid duplicated relationship, first check if user has rate already
        List<Rates> userRates = restaurant.getRates().stream()
                .filter((r) -> {
                    return r.getUser().getId().equals(user.getId());
                }).collect(Collectors.toList());
        //create new rate
        if (userRates.isEmpty()) {
            Rates newRate = new Rates(user, 0, comment);
            restaurant.getRates().add(newRate);
            //only change rate value
        } else {
            userRates.get(0).setComment(comment);
        }
        return restaurantRepository.save(restaurant);
    }
}
