package com.project.ZTI.service;

import com.project.ZTI.models.user.ERole;
import com.project.ZTI.models.user.Role;
import com.project.ZTI.models.user.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, ERole role);
    User getUser(String username);
    //nie powinnismy zwracac wszystkih userow tylko pewna porcje
    List<User> getUsers();
}
