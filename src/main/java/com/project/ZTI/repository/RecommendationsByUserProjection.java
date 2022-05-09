package com.project.ZTI.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecommendationsByUserProjection {
    private List<String> otherUsers;
    private double avgRating = 0;
    private long id;
}
