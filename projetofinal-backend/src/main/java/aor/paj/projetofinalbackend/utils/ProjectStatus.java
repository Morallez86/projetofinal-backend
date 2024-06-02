package aor.paj.projetofinalbackend.utils;

public enum ProjectStatus {
    PLANNING(100),
    READY(200),
    IN_PROGRESS(300),
    FINISHED(400),
    CANCELLED(500);

    private final int value;

    ProjectStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProjectStatus fromValue(int value) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ProjectStatus value: " + value);
    }
}

