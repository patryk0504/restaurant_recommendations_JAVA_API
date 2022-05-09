package com.project.ZTI.service;

import com.project.ZTI.models.RecommendationsByRestaurantHelper;
import com.project.ZTI.models.RecommendationsByUserHelper;
import com.project.ZTI.models.Restaurant;
import com.project.ZTI.models.user.User;
import com.project.ZTI.repository.RecommendationsByRestaurantProjection;
import com.project.ZTI.repository.RecommendationsByUserProjection;
import com.project.ZTI.repository.RestaurantRepository;
import com.project.ZTI.security.AuthUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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

    public List<RecommendationsByRestaurantHelper>
    getRestaurantRecommendationsByCity(Long restaurantId) {
        List<RecommendationsByRestaurantHelper> restaurantRecommendationsResult = new ArrayList<>();
        List<RecommendationsByRestaurantProjection> recommendationsByRestaurantProjections
                = restaurantRepository.findRestaurantRecommendationsByCity(restaurantId);
        List<Long> recommendedRestaurantsIds = recommendationsByRestaurantProjections.stream()
                .map(RecommendationsByRestaurantProjection::getId)
                .collect(Collectors.toList());
        List<Restaurant> recommendedRestaurantsStandardObjects = restaurantRepository.findRestaurantByIdIn(recommendedRestaurantsIds);
        for (int i = 0; i < recommendationsByRestaurantProjections.size(); i++) {
            restaurantRecommendationsResult.add(new RecommendationsByRestaurantHelper(
                    recommendedRestaurantsStandardObjects.get(i),
                    recommendationsByRestaurantProjections.get(i).getJaccard(),
                    recommendationsByRestaurantProjections.get(i).getParams()
            ));
        }
        return restaurantRecommendationsResult;
    }

    public List<RecommendationsByRestaurantHelper>
    getRestaurantRecommendationsAllCities(Long restaurantId) {
        List<RecommendationsByRestaurantHelper> restaurantRecommendationsResult = new ArrayList<>();
        List<RecommendationsByRestaurantProjection> recommendationsByRestaurantProjections
                = restaurantRepository.findRestaurantRecommendationsAllCities(restaurantId);
        List<Long> recommendedRestaurantsIds = recommendationsByRestaurantProjections
                .stream()
                .map(RecommendationsByRestaurantProjection::getId)
                .collect(Collectors.toList());
        List<Restaurant> recommendedRestaurantsStandardObjects = restaurantRepository.findRestaurantByIdIn(recommendedRestaurantsIds);
        for (int i = 0; i < recommendationsByRestaurantProjections.size(); i++) {
            restaurantRecommendationsResult.add(
                    new RecommendationsByRestaurantHelper(
                            recommendedRestaurantsStandardObjects.get(i),
                            recommendationsByRestaurantProjections.get(i).getJaccard(),
                            recommendationsByRestaurantProjections.get(i).getParams()
                    ));
        }
        return restaurantRecommendationsResult;
    }

    public List<RecommendationsByUserHelper>
    getRestaurantRecommendationsByUser(HttpServletRequest request){
        User user = authUtility.getUserFromAccessToken(request);
        if (user == null)
            throw new RuntimeException("User not found");

        List<RecommendationsByUserHelper> restaurantRecommendationsResult = new ArrayList<>();
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
                    new RecommendationsByUserHelper(
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
            throw new RuntimeException("User not found");
    }
}
