package com.project.ZTI.repository;

import com.project.ZTI.models.user.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {
    User findByUsername(String username);
}
