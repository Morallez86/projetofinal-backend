package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different types of approval/refusal actions in notification management.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum NotificationManagingActions {

    /**
     * Represents the action type related to projects.
     */
    PROJECT(100),

    /**
     * Represents the action type related to invitations.
     */
    INVITATION(200);

    private final int value;

    /**
     * Constructor for NotificationManagingActions enum.
     *
     * @param value The integer value associated with the enum constant.
     */
    NotificationManagingActions(int value) {
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
     * Returns the NotificationManagingActions enum constant corresponding to the given integer value.
     *
     * @param value The integer value to find the corresponding enum constant.
     * @return The NotificationManagingActions enum constant matching the given value.
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
