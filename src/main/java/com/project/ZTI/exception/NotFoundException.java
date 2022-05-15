package com.project.ZTI.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Couldn't find element");
    }
    public NotFoundException(String message){super(message);}
}
