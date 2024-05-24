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

    public static SkillType fromValue(int value) {
        for (SkillType type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SkillType value: " + value);
    }
}
