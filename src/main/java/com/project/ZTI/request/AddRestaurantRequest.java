package com.project.ZTI.request;

import com.project.ZTI.model.Cuisine;
import com.project.ZTI.model.Location;
import com.project.ZTI.model.Rates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AddRestaurantRequest {
    private String name;
    private String address;
    private double address_latitude;
    private double address_longitude;
    private boolean vegan_options;
    private boolean vegetarian_friendly;
    private boolean gluten_free;

    private Set<Long> cuisines;
    private Long location;

}
