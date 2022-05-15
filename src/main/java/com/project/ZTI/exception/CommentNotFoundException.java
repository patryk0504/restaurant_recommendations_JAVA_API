package com.project.ZTI.exception;

public class CommentNotFoundException extends NotFoundException{
    public CommentNotFoundException(){
        super("Couldn't find comment for given restaurant");
    }
}
