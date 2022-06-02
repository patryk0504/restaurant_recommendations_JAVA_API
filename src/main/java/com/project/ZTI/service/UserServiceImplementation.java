package com.project.ZTI.service;

import com.project.ZTI.exception.UserAlreadyExistException;
import com.project.ZTI.model.user.ERole;
import com.project.ZTI.model.user.Role;
import com.project.ZTI.model.user.User;
import com.project.ZTI.repository.RoleRepository;
import com.project.ZTI.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found int the database {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        });
//        we need differentiate User model and User security
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole(ERole.ROLE_USER);
        user.getRoles().add(role);
        try {
            return userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            log.error("User with given parameters already exist!");
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getRole());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, ERole eRole) {
        //some additional validations?
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRole(eRole);
        log.info("Adding role {} to user {}", role.getRole(), user.getName());
        user.getRoles().add(role);
        //save wymusza zakonczenie transakcji!!!
        userRepository.save(user);

    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {} from the database", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
