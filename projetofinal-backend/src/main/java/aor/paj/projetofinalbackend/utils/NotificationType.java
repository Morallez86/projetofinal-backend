package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different types of notifications.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum NotificationType {

    /**
     * Represents a message notification type.
     */
    MESSAGE(100),

    /**
     * Represents a project-related notification type.
     */
    PROJECT(200),

    /**
     * Represents a managing action notification type.
     */
    MANAGING(300),

    /**
     * Represents an invitation notification type.
     */
    INVITATION(400);

    private final int value;

    /**
     * Constructor for NotificationType enum.
     *
     * @param value The integer value associated with the enum constant.
     */
    NotificationType(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with the enum constant.
     *
     * @return The integer value of the enum constant.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the NotificationType enum constant corresponding to the given integer value.
     *
     * @param value The integer value to find the corresponding enum constant.
     * @return The NotificationType enum constant matching the given value.
     * @throws IllegalArgumentException If no enum constant matches the provided value.
     */
    public static NotificationType fromValue(int value) {
        for (NotificationType type : NotificationType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus value: " + value);
    }
}
