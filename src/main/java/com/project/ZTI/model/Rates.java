package com.project.ZTI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ZTI.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Getter
@Setter
public class Rates {
    @RelationshipId
    private Long id;
    private int rating;
    private String comment;

    //points towards user -rated-> restaurant
    @TargetNode
    @JsonIgnore
    private final User user;

    public Rates(User user, int rating, String comment){
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }
}
