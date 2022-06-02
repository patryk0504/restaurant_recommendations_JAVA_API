package com.project.ZTI.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(){
        super("User with given parameters already exist");
    }
}
