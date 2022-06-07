package com.project.ZTI.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.Set;

@Node
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {

    @Id @GeneratedValue private long id;

    private String name;
    private String address;
    private double address_latitude;
    private double address_longitude;
    private boolean vegan_options;
    private boolean vegetarian_friendly;
    private boolean gluten_free;
    private String popularity_detailed;
    private String popularity_generic;
    private String trip_advisor_id;



    @Relationship(type = "SERVES", direction = Relationship.Direction.OUTGOING)
    private Set<Cuisine> cuisines;

    @Relationship(type = "LOCATED_IN", direction = Relationship.Direction.OUTGOING)
    private Location location;

    @Relationship(type = "RATED", direction = Relationship.Direction.INCOMING)
    private List<Rates> rates;
}
