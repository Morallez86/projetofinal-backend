package aor.paj.projetofinalbackend.utils;

public enum Role {
    USER(100), ADMIN(200);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role fromValue(int value) {
        for (Role role : values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid Role value: " + value);
    }
}

