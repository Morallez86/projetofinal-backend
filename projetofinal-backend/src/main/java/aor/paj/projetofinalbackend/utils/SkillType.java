package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different types of skills.
 * Each skill type has an associated integer value.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum SkillType {

    /**
     * Represents knowledge-based skills.
     */
    KNOWLEDGE(100),

    /**
     * Represents software-related skills.
     */
    SOFTWARE(200),

    /**
     * Represents hardware-related skills.
     */
    HARDWARE(300),

    /**
     * Represents skills related to tools.
     */
    TOOLS(400);

    private final int value;

    /**
     * Constructor for SkillType enum.
     *
     * @param value The integer value associated with the skill type.
     */
    SkillType(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with the skill type.
     *
     * @return The integer value of the skill type.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the SkillType enum constant associated with the given integer value.
     *
     * @param value The integer value representing a SkillType.
     * @return The corresponding SkillType enum constant.
     * @throws IllegalArgumentException if the provided value does not match any SkillType.
     */
    public static SkillType fromValue(int value) {
        for (SkillType type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid SkillType value: " + value);
    }
}
