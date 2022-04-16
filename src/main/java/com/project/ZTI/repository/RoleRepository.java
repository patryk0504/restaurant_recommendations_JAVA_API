package com.project.ZTI.repository;

import com.project.ZTI.models.user.ERole;
import com.project.ZTI.models.user.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RoleRepository extends Neo4jRepository<Role, Long> {
    Role findByRole(ERole role);
}
