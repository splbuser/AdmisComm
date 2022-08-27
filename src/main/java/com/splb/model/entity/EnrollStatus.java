package com.splb.model.entity;

public enum EnrollStatus {
    NO_ENROLLED(0),
    CONTRACT(1),
    BUDGET(2);

    private int statusCode;

    EnrollStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
