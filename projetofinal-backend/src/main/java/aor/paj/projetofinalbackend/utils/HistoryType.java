package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different types of history entries.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum HistoryType {

    /**
     * Represents history related to tasks.
     */
    TASKS(100),

    /**
     * Represents history related to additions.
     */
    ADD(200),

    /**
     * Represents history related to removals.
     */
    REMOVE(300),

    /**
     * Represents history related to project states.
     */
    PROJECTSTATE(400),

    /**
     * Represents normal history entries.
     */
    NORMAL(500);

    private final int value;

    /**
     * Constructs a HistoryType enum constant with the specified integer value.
     *
     * @param value The integer value associated with the history type.
     */
    HistoryType(int value) {
        this.value = value;
    }

    /**
     * Retrieves the integer value associated with the history type.
     *
     * @return The integer value of the history type.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the HistoryType enum constant corresponding to the given integer value.
     *
     * @param value The integer value to find the corresponding HistoryType for.
     * @return The HistoryType enum constant corresponding to the provided value.
     * @throws IllegalArgumentException if no matching HistoryType is found for the given value.
     */
    public static HistoryType fromValue(int value) {
        for (HistoryType type : HistoryType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ProjectStatus value: " + value);
    }
}
