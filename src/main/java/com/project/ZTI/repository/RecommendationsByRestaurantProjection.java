package com.project.ZTI.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecommendationsByRestaurantProjection {
    private List<String> params;
    private double jaccard = 0;
    private long id;
}