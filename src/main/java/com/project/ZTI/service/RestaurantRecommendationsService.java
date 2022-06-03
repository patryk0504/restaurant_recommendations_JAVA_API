package com.project.ZTI.service;

import com.project.ZTI.exception.UserNotFoundException;
import com.project.ZTI.response.RecommendationsByRestaurantResponse;
import com.project.ZTI.response.RecommendationsByUserResponse;
import com.project.ZTI.model.Restaurant;
import com.project.ZTI.model.user.User;
import com.project.ZTI.repository.RecommendationsByRestaurantProjection;
import com.project.ZTI.repository.RecommendationsByUserProjection;
import com.project.ZTI.repository.RestaurantRepository;
import com.project.ZTI.security.AuthUtility;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RestaurantRecommendationsService {

    private final RestaurantRepository restaurantRepository;
    private final AuthUtility authUtility;

    public RestaurantRecommendationsService(RestaurantRepository restaurantRepository, AuthUtility authUtility){
        this.restaurantRepository = restaurantRepository;
        this.authUtility = authUtility;
    }

    public List<RecommendationsByRestaurantResponse>
    getRestaurantRecommendationsByCity(Long restaurantId) {
        List<RecommendationsByRestaurantResponse> restaurantRecommendationsResult = new ArrayList<>();
        List<RecommendationsByRestaurantProjection> recommendationsByRestaurantProjections
                = restaurantRepository.findRestaurantRecommendationsByCity(restaurantId);
        List<Long> recommendedRestaurantsIds = recommendationsByRestaurantProjections.stream()
                .map(RecommendationsByRestaurantProjection::getId)
                .collect(Collectors.toList());
        List<Restaurant> recommendedRestaurantsStandardObjects = restaurantRepository.findRestaurantByIdIn(recommendedRestaurantsIds);
        for (int i = 0; i < recommendationsByRestaurantProjections.size(); i++) {
            restaurantRecommendationsResult.add(new RecommendationsByRestaurantResponse(
                    recommendedRestaurantsStandardObjects.get(i),
                    recommendationsByRestaurantProjections.get(i).getJaccard(),
                    recommendationsByRestaurantProjections.get(i).getParams()
            ));
        }
        return restaurantRecommendationsResult;
    }

    public List<RecommendationsByRestaurantResponse>
    getRestaurantRecommendationsAllCities(Long restaurantId) {
        List<RecommendationsByRestaurantResponse> restaurantRecommendationsResult = new ArrayList<>();
        List<RecommendationsByRestaurantProjection> recommendationsByRestaurantProjections
                = restaurantRepository.findRestaurantRecommendationsAllCities(restaurantId);
        List<Long> recommendedRestaurantsIds = recommendationsByRestaurantProjections
                .stream()
                .map(RecommendationsByRestaurantProjection::getId)
                .collect(Collectors.toList());
        List<Restaurant> recommendedRestaurantsStandardObjects = restaurantRepository.findRestaurantByIdIn(recommendedRestaurantsIds);
        for (int i = 0; i < recommendationsByRestaurantProjections.size(); i++) {
            restaurantRecommendationsResult.add(
                    new RecommendationsByRestaurantResponse(
                            recommendedRestaurantsStandardObjects.get(i),
                            recommendationsByRestaurantProjections.get(i).getJaccard(),
                            recommendationsByRestaurantProjections.get(i).getParams()
                    ));
        }
        return restaurantRecommendationsResult;
    }

    public List<RecommendationsByUserResponse>
    getRestaurantRecommendationsByUser(HttpServletRequest request){
        User user = authUtility.getUserFromAccessToken(request);
        if (user == null)
            throw new UserNotFoundException();

        List<RecommendationsByUserResponse> restaurantRecommendationsResult = new ArrayList<>();
        List<RecommendationsByUserProjection> recommendationsByUserProjections
                = restaurantRepository.findRestaurantRecommendationsByUserId(user.getId());
        List<Long> recommendedRestaurantsIds = recommendationsByUserProjections
                .stream()
                .map(RecommendationsByUserProjection::getId)
                .collect(Collectors.toList());
        List<Restaurant> recommendedRestaurantsStandardObjects =
                restaurantRepository.findRestaurantByIdIn(recommendedRestaurantsIds);
        for (int i = 0; i < recommendationsByUserProjections.size(); i++) {
            restaurantRecommendationsResult.add(
                    new RecommendationsByUserResponse(
                            recommendedRestaurantsStandardObjects.get(i),
                            recommendationsByUserProjections.get(i).getOtherUsers(),
                            recommendationsByUserProjections.get(i).getAvgRating()
                    ));
        }
        return restaurantRecommendationsResult;
    }

    public List<Restaurant> getRestaurantRecommendationsByRating(HttpServletRequest request) {
        User user = authUtility.getUserFromAccessToken(request);
        if (user != null)
            return restaurantRepository.findRestaurantRecommendationsByRating(user.getId());
        else
            throw new UserNotFoundException();
    }
}
