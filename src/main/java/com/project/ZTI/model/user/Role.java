package com.project.ZTI.model.user;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
//@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private ERole role;

    public Role(ERole role){
        this.role = role;
    }
}
