package com.project.ZTI.exception;

public class LocationNotFoundException extends NotFoundException{
    public LocationNotFoundException(Long locationId){
        super("Couldn't find location for given id: " + locationId);
    }
}
