package aor.paj.projetofinalbackend.utils;

public enum SkillType {
    KNOWLEDGE(100), SOFTWARE(200), HARDWARE(300), TOOLS(400);

    private final int value;

    SkillType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
