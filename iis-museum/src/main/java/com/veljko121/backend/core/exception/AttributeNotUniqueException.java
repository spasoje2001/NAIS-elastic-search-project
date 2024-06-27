package com.veljko121.backend.core.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;

public class AttributeNotUniqueException extends DuplicateKeyException {
    
    public AttributeNotUniqueException(String attribute, String attributeValue) {
        super(StringUtils.capitalize(attribute) + " '" + attributeValue + "' already exists.");
    }

    public AttributeNotUniqueException(String msg) {
        super(msg);
    }

}
