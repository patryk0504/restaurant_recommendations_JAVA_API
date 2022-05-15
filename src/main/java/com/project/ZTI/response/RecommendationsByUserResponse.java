package com.project.ZTI.response;

import com.project.ZTI.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public
class RecommendationsByUserResponse {
    private Restaurant restaurant;
    private List<String> otherUsers = null;
    private double avgRating;
}
