package com.splb.model.entity;

public enum Role {
    USER(0),
    ADMIN(1);

    private final int userStatus;

    Role(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserStatus() {
        return userStatus;
    }
}
