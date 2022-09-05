package com.splb.model.dao.exception;

/**
 * custom superclass for DAO exceptions
 */
public class DAOException extends Exception {

    public DAOException (String message) {
        super(message);
    }
}