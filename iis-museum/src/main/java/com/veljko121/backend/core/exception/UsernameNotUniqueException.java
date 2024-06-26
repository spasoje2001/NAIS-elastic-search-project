package com.veljko121.backend.core.exception;

public class UsernameNotUniqueException extends AttributeNotUniqueException {

    public UsernameNotUniqueException(String username) {
        super("username", username);
    }
    
}
