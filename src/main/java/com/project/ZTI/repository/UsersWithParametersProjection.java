package com.project.ZTI.repository;

import com.project.ZTI.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UsersWithParametersProjection {
    private String username;
    List<String> restaurantNames;
    String similarUser;
    double similarity;
}
