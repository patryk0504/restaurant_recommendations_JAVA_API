package com.project.ZTI.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public
class RecommendationsByUserHelper {
    private Restaurant restaurant;
    private List<String> otherUsers = null;
    private double avgRating;

//    public RecommendationsByUserHelper(Restaurant restaurant, double jaccard, List<String> params) {
//        this.restaurant = restaurant;
//        this.jaccard = jaccard;
//        this.params = params;
//    }
//
//    RecommendationsByUserHelper() {
//    }
}
