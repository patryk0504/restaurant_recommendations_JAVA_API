package com.project.ZTI;

import com.project.ZTI.model.user.ERole;
import com.project.ZTI.service.UserAdministrationService;
import com.project.ZTI.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final UserAdministrationService userAdministrationService;

    @Autowired
    public DataLoader(UserAdministrationService userAdministrationService) {
        this.userAdministrationService = userAdministrationService;
    }

    public void run(ApplicationArguments args) {
        try{
//            userService.saveRole(new Role(ERole.ROLE_ADMIN));
//            userService.saveRole(new Role(ERole.ROLE_USER));
//            userService.saveUser(new User("javaUser", "javaUser", "javaUser"));
//            userService.addRoleToUser("javaUser", ERole.ROLE_ADMIN);
            userAdministrationService.addRoleToUser("javaUser", ERole.ROLE_USER);
        }catch(org.springframework.dao.DataIntegrityViolationException e){
            log.error(e.toString());
        }
    }
}