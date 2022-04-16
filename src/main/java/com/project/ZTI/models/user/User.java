package com.project.ZTI.models.user;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Node
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String username;
    private String password;

    public User(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }


    @JsonCreator
    public User(@JsonProperty("username") String username, @JsonProperty("password")String password) {
        this.username = username;
        this.password = password;
    }

    @Relationship(type = "HAS_ROLE", direction = Relationship.Direction.OUTGOING)
    private Collection<Role> roles = new ArrayList<>();




}
