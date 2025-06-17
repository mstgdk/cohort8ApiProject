package com.patika.enums;

public enum RoleType {

    ROLE_EMPLOYEE("Employee"),
    ROLE_ADMIN("Administrator");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
