package com.hasanalmunawr.files_keeper.exception;


public class CodeInvalidException extends RuntimeException {

    public CodeInvalidException(String message) {
        super(message);
    }

    public CodeInvalidException() {
        super("Invalid code");
    }
}
