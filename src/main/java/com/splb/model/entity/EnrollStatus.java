package com.splb.model.entity;

public enum EnrollStatus {
    NO_ENROLLED(0),
    CONTRACT(1),
    BUDGET(2),
    NO_PARTICIPATE(3);

    private final int statusCode;

    EnrollStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
