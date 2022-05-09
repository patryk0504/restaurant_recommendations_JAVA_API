package com.project.ZTI.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public
class RecommendationsByRestaurantHelper {
    private Restaurant restaurant;
    private double jaccard;
    private List<String> params = null;


    public RecommendationsByRestaurantHelper(Restaurant restaurant, double jaccard, List<String> params) {
        this.restaurant = restaurant;
        this.jaccard = jaccard;
        this.params = params;
    }

    RecommendationsByRestaurantHelper() {
    }
}
