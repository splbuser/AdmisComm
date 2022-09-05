package com.splb.service.exceptions;

/**
 * superclass for all Services exceptions
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}