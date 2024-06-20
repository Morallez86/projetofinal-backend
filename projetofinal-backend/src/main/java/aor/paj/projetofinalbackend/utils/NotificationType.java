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
}
