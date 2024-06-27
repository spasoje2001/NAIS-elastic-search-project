package com.veljko121.backend.core.exception;

public class EmailNotUniqueException extends AttributeNotUniqueException {

    public EmailNotUniqueException(String email) {
        super("email", email);
    }
    
}
