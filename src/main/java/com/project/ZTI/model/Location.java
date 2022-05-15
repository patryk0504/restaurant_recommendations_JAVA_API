package com.project.ZTI.model;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
public class Location {
    @Id
    @GeneratedValue private Long id;

    private String name;

}
