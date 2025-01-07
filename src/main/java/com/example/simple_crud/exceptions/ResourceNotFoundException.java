package com.example.simple_crud.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -5896860997454921991L;

    private final String msg;

    public static ResourceNotFoundException create(String msg) {
        return new ResourceNotFoundException(msg);
    }

    public ResourceNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
