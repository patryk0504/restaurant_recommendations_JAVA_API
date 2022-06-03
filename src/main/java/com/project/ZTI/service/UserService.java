package com.project.ZTI.service;

import com.project.ZTI.model.user.ERole;
import com.project.ZTI.model.user.Role;
import com.project.ZTI.model.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUser(String username);
    //nie powinnismy zwracac wszystkih userow tylko pewna porcje
}
