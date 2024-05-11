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
}

