package com.project.ZTI.service;

import com.project.ZTI.exception.UserNotFoundException;
import com.project.ZTI.model.user.User;
import com.project.ZTI.repository.UserRepository;
import com.project.ZTI.repository.UsersWithParametersProjection;
import com.project.ZTI.security.AuthUtility;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserRecommendationService {

    private final AuthUtility authUtility;
    private final UserRepository userRepository;

    public UserRecommendationService(AuthUtility authUtility, UserRepository userRepository){
        this.authUtility = authUtility;
        this.userRepository = userRepository;
    }

    public List<UsersWithParametersProjection> getUsers(HttpServletRequest request){
        User user = authUtility.getUserFromAccessToken(request);
        if (user != null)
            return userRepository.findUserWithParameters(user.getId());
        else
            throw new UserNotFoundException();
    }
}
