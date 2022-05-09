package com.project.ZTI.exception;

import java.text.MessageFormat;

public class RateNotFoundException extends RuntimeException{
    public RateNotFoundException(){
        super("Couldn't find rate for given restaurant");
    }
}
