package com.mentoringapp.domain;

public enum UserRole {
    MENTOR("MENTOR"),
    MENTEE("MENTEE");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.value.equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
} 