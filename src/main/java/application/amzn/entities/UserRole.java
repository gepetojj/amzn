package application.amzn.entities;

import java.util.Objects;

public enum UserRole {
    SELLER("seller"),
    ADMIN("admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    String valueOf() {
        return value;
    }

    static UserRole fromValue(String value) {
        for (UserRole role : values()) {
            if (Objects.equals(role.value, value)) {
                return role;
            }
        }
        return null;
    }
}
