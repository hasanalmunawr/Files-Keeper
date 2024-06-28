package com.hasanalmunawr.files_keeper.exception;


public class UserAlreadyExistException extends RuntimeException {


    public UserAlreadyExistException() {
        super("User already exists");
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
