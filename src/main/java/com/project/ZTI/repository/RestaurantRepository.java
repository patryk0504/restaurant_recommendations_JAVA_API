package com.project.ZTI.repository;

import com.project.ZTI.model.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends Neo4jRepository<Restaurant, Long> {
    List<Restaurant> findRestaurantByLocationId(Long id);

    List<Restaurant> findTopBy(Pageable pageable);

    Optional<Restaurant> findRestaurantById(Long id);

    List<Restaurant> findRestaurantByIdIn(List<Long> id);

    @Query("MATCH (me:User)-[my:RATED]->(r:Restaurant)\n" +
            "MATCH (other:User)-[their:RATED]->(r)\n" +
            "WHERE id(me) = $userId\n" +
            "AND me <> other\n" +
            "AND abs(my.rating - their.rating) < 2\n" +
            "WITH other,r\n" +
            "MATCH (other)-[otherRating:RATED]->(r2:Restaurant)\n" +
            "WHERE r2 <> r\n" +
            "WITH avg(otherRating.rating) AS avgRating, r2, collect(other.username) as other_users\n" +
            "MATCH (r2) -[:LOCATED_IN]-> (l:Location)\n" +
            "RETURN r2 as restaurant, avgRating, l.name as location, other_users\n" +
            "ORDER BY avgRating desc\n" +
            "LIMIT 25")
    List<Restaurant> findRestaurantRecommendationsByRating(@Param("userId") Long userId);


    @Query("MATCH (m:Restaurant)-[:LOCATED_IN]->(location:Location)<-[:LOCATED_IN]-(other:Restaurant)\n" +
            "where id(m) = $restaurantId\n" +
            "MATCH (m)-[:SERVES]-(t)-[:SERVES]-(other)\n" +
            "WITH m, other, COUNT(t) AS intersection, COLLECT(t.name) AS i\n" +
            "MATCH (m)-[:SERVES|LOCATED_IN]-(mt)\n" +
            "WITH m, other, intersection,i, COLLECT(mt.name) AS s1\n" +
            "MATCH (other)-[:SERVES|LOCATED_IN]-(ot)\n" +
            "WITH m,other,intersection,i, s1, COLLECT(ot.name) AS s2\n" +
            "WITH m,other,intersection,s1,s2\n" +
            "WITH m,other,intersection,s1+[x IN s2 WHERE NOT x IN s1] AS union, s1, s2\n" +
            "RETURN other as restaurant, union as params, round(((1.0*intersection)/SIZE(union)), 2) " +
            "AS jaccard ORDER BY jaccard DESC LIMIT 100")
    List<RecommendationsByRestaurantProjection> findRestaurantRecommendationsByCity(@Param("restaurantId") Long restaurantId);


    @Query("MATCH (m:Restaurant)-[:SERVES|LOCATED_IN]-(t)-[:SERVES|LOCATED_IN]-(other:Restaurant)\n" +
            "where id(m) = $restaurantId\n" +
            "WITH m, other, COUNT(t) AS intersection, COLLECT(t.name) AS i\n" +
            "MATCH (m)-[:SERVES|LOCATED_IN]-(mt)\n" +
            "WITH m, other, intersection,i, COLLECT(mt.name) AS s1\n" +
            "MATCH (other)-[:SERVES|LOCATED_IN]-(ot)\n" +
            "WITH m,other,intersection,i, s1, COLLECT(ot.name) AS s2\n" +
            "WITH m,other,intersection,s1,s2\n" +
            "WITH m,other,intersection,s1+[x IN s2 WHERE NOT x IN s1] AS union, s1, s2\n" +
            "RETURN other as restaurant, union as params, round(((1.0*intersection)/SIZE(union)), 2) " +
            "AS jaccard ORDER BY jaccard DESC LIMIT 100")
    List<RecommendationsByRestaurantProjection> findRestaurantRecommendationsAllCities(@Param("restaurantId") Long restaurantId);


    @Query("MATCH (me:User)-[my:RATED]->(r:Restaurant)\n" +
            "MATCH (other:User)-[their:RATED]->(r)\n" +
            "WHERE id(me) = $userId\n" +
            "AND me <> other\n" +
            "AND abs(my.rating - their.rating) < 2\n" +
            "WITH other,r\n" +
            "MATCH (other)-[otherRating:RATED]->(r2:Restaurant)\n" +
            "WHERE r2 <> r\n" +
            "WITH avg(otherRating.rating) AS avgRating, r2, collect(distinct other.username) as otherUsers\n" +
            "MATCH (r2) -[:LOCATED_IN]-> (l:Location)\n" +
            "RETURN r2 as restaurant, avgRating, l.name as location, otherUsers\n" +
            "ORDER BY avgRating desc\n" +
            "LIMIT 25")
    List<RecommendationsByUserProjection> findRestaurantRecommendationsByUserId(@Param("userId") Long userId);
}
