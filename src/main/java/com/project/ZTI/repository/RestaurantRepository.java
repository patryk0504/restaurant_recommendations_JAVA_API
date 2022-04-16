package com.project.ZTI.repository;

import com.project.ZTI.models.Restaurant;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RestaurantRepository extends Neo4jRepository<Restaurant, Long> {

}
