package com.project.ZTI.models;


import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@Getter
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



    @Relationship(type = "SERVES")
    private Set<Cuisine> cuisines;

    @Relationship(type = "LOCATED_IN")
    private Location location;
}
