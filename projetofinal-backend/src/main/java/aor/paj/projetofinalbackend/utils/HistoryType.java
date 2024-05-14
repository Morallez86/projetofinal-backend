package aor.paj.projetofinalbackend.utils;

public enum HistoryType {
    USER(100),
    DATA(200),
    TASKSTATE(300),
    PROJECTSTATE(400);

    private final int code;

    HistoryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
