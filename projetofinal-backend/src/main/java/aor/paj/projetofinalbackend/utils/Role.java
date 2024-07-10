package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different roles in the application.
 * Each role has an associated integer value.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum Role {

    /**
     * Standard user role.
     */
    USER(100),

    /**
     * Administrator role with elevated privileges.
     */
    ADMIN(200);

    private final int value;

    /**
     * Constructor for Role enum.
     *
     * @param value The integer value associated with the role.
     */
    Role(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with the role.
     *
     * @return The integer value of the role.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the Role enum constant associated with the given integer value.
     *
     * @param value The integer value representing a Role.
     * @return The corresponding Role enum constant.
     * @throws IllegalArgumentException if the provided value does not match any Role.
     */
    public static Role fromValue(int value) {
        for (Role role : values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid Role value: " + value);
    }
}

