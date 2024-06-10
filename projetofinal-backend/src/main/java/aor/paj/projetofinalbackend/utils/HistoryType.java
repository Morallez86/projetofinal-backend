package aor.paj.projetofinalbackend.utils;

public enum HistoryType {
    TASKS(100),
    ADD(200),
    REMOVE(300),
    PROJECTSTATE(400),

    NORMAL(500);

    private final int value;

    HistoryType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static HistoryType fromValue(int value) {
        for (HistoryType type : HistoryType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ProjectStatus value: " + value);
    }
}
