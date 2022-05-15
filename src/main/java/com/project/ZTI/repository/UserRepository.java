package com.project.ZTI.repository;

import com.project.ZTI.model.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;


public interface UserRepository extends Neo4jRepository<User, Long> {
    User findByUsername(String username);

    @Query("MATCH (p1:User)-[x:RATED]->(r:Restaurant)<-[y:RATED]-(p2:User) " +
                  "WHERE id(p1) = $userId " +
                  "WITH COUNT(r) AS numberrated,SUM(x.rating * y.rating) AS xyDotProduct, " +
                  "SQRT(REDUCE(xDot = 0.0, a IN COLLECT(x.rating) | xDot + a^2)) AS xLength, " +
                  "SQRT(REDUCE(yDot = 0.0, b IN COLLECT(y.rating) | yDot + b^2)) AS yLength," +
                  "collect(r.name) as restaurants, p1, p2 WHERE numberrated > 0 " +
                  "RETURN restaurants as restaurantNames, p1 as user, p2.username as similarUser, numberrated as numberOfSimilarRestaurants, round(xyDotProduct / (xLength * yLength), 2) AS similarity " +
                  "ORDER BY similarity DESC LIMIT 100;")
    List<UsersWithParametersProjection> findUserWithParameters(@Param("userId") Long userId);
}
