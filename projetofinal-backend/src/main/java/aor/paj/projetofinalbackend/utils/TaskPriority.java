package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different priority levels for tasks.
 * Each priority level has an associated integer value.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum TaskPriority {

    /**
     * Low priority level.
     */
    LOW(100),

    /**
     * Medium priority level.
     */
    MEDIUM(200),

    /**
     * High priority level.
     */
    HIGH(300);

    private final int value;

    /**
     * Constructor for TaskPriority enum.
     *
     * @param value The integer value associated with the priority level.
     */
    TaskPriority(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with the priority level.
     *
     * @return The integer value of the priority level.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the TaskPriority enum constant associated with the given integer value.
     *
     * @param value The integer value representing a TaskPriority.
     * @return The corresponding TaskPriority enum constant.
     * @throws IllegalArgumentException if the provided value does not match any TaskPriority.
     */
    public static TaskPriority fromValue(int value) {
        for (TaskPriority priority : values()) {
            if (priority.value == value) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid TaskPriority value: " + value);
    }
}
