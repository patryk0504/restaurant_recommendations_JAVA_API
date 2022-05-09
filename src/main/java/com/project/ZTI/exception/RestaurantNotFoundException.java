package com.project.ZTI.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(Long id){
        super(MessageFormat.format("Couldn't find restaurant with id: {0}", id));
    }
}
