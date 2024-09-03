package application.amzn.entities;

public enum UserRole {
    SELLER(0),
    ADMIN(1);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    static UserRole fromValue(int value) {
        for (UserRole role : values()) {
            if (role.value == value) {
                return role;
            }
        }
        return null;
    }
}
