package com.project.ZTI.request;

import com.project.ZTI.model.user.ERole;
import lombok.Data;

@Data
public class RoleToUserRequest {
    private String username;
    private ERole role;
}
