package com.project.ZTI.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(){
        super("Error: User not found");
    }
}
