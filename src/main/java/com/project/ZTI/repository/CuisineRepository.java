package com.project.ZTI.repository;

import com.project.ZTI.model.Cuisine;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CuisineRepository extends Neo4jRepository<Cuisine, Long> {
    List<Cuisine> findAll(Sort sort);

}
