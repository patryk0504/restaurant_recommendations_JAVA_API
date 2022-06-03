package com.project.ZTI.service;

import com.project.ZTI.exception.RoleException;
import com.project.ZTI.exception.UserNotFoundException;
import com.project.ZTI.model.user.ERole;
import com.project.ZTI.model.user.Role;
import com.project.ZTI.model.user.User;
import com.project.ZTI.repository.CuisineRepository;
import com.project.ZTI.repository.RoleRepository;
import com.project.ZTI.repository.UserRepository;
import com.project.ZTI.security.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserAdministrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthUtility authUtility;

    public UserAdministrationService(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     AuthUtility authUtility) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authUtility = authUtility;
    }

    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public void addRoleToUser(String username, ERole eRole) {
        //some additional validations?
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRole(eRole);
        log.info("Adding role {} to user {}", role.getRole(), user.getName());
        user.getRoles().add(role);
        //save wymusza zakonczenie transakcji!!!
        userRepository.save(user);
    }

    public void cancelRoleFromUser(String username, HttpServletRequest request) {
        User user = authUtility.getUserFromAccessToken(request);
        if (user != null) {
            //user cannot cancel privileges that belongs to him
            if (user.getUsername().equals(username)) {
                throw new RoleException("Error: Deleting the role assigned to a logged user is prohibited");
            } else {
                User userToCancelRole = userRepository.findByUsername(username);
                userToCancelRole.getRoles().removeIf((r) -> r.getRole().equals(ERole.ROLE_ADMIN));
                userRepository.save(userToCancelRole);
            }
        }else
            throw new UserNotFoundException();
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getRole());
        return roleRepository.save(role);
    }
}
