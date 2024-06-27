package aor.paj.projetofinalbackend.utils;

public enum NotificationType {

    MESSAGE(100), PROJECT(200), MANAGING(300), INVITATION(400);

    private final int value;

    NotificationType(int value) {
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
