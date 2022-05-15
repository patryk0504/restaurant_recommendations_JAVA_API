package com.project.ZTI.response;

import com.project.ZTI.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public
class RecommendationsByRestaurantResponse {
    private Restaurant restaurant;
    private double jaccard;
    private List<String> params = null;


    public RecommendationsByRestaurantResponse(Restaurant restaurant, double jaccard, List<String> params) {
        this.restaurant = restaurant;
        this.jaccard = jaccard;
        this.params = params;
    }

    RecommendationsByRestaurantResponse() {
    }
}
