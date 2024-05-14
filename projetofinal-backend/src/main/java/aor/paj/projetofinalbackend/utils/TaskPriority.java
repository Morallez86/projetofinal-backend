package aor.paj.projetofinalbackend.utils;

public enum TaskPriority {
    LOW(100),
    MEDIUM(200),
    HIGH(300);

    private final int value;

    TaskPriority(int value) {this.value = value;}

    public int getValue() {return value;};
}
