package aor.paj.projetofinalbackend.utils;

// Class that deals with the different types of approval/refusal in notifications managing type class
public enum NotificationManagingActions {
    PROJECT(100), INVITATION(200);

    private final int value;

    NotificationManagingActions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NotificationType fromValue(int value) {
        for (NotificationType type : NotificationType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus value: " + value);
    }
}
