package aor.paj.projetofinalbackend.utils;

public enum TaskStatus {
    TODO(100),
    DOING(200),
    DONE(300);

    private final int value;

    TaskStatus(int value) {this.value = value;}

    public int getValue() {return value;};
}
