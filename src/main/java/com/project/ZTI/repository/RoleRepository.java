package com.project.ZTI.repository;

import com.project.ZTI.model.user.ERole;
import com.project.ZTI.model.user.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RoleRepository extends Neo4jRepository<Role, Long> {
    Role findByRole(ERole role);
}
