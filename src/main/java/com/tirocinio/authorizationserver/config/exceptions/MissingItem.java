package com.tirocinio.authorizationserver.config.exceptions;

public class MissingItem extends RuntimeException {
    public MissingItem(String message) {
        super(message);
    }
}
