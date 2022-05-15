package com.project.ZTI.exception;

public class CuisineNotFoundException extends NotFoundException{
    public CuisineNotFoundException(Long cuisineId){
        super("Couldn't find cuisine for given id: " + cuisineId);
    }
}
