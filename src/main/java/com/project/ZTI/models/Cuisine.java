package com.project.ZTI.models;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
public class Cuisine {

    @Id @GeneratedValue private long id;
    private String name;

    public Cuisine(String name){
        this.name = name;
    }
}
