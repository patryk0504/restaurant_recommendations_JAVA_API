package com.project.ZTI.repository;

import com.project.ZTI.models.Location;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface LocationRepository extends Neo4jRepository<Location, Long> {
    List<Location> findAll(Sort sort);
}
