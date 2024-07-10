package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different status levels for tasks.
 * Each status level has an associated integer value.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum TaskStatus {

    /**
     * Task is in "To Do" status.
     */
    TODO(100),

    /**
     * Task is in "Doing" status.
     */
    DOING(200),

    /**
     * Task is in "Done" status.
     */
    DONE(300);

    private final int value;

    /**
     * Constructor for TaskStatus enum.
     *
     * @param value The integer value associated with the status.
     */
    TaskStatus(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value associated with the status.
     *
     * @return The integer value of the status.
     */
    public int getValue() {
        return value;
    }

    /**
     * Retrieves the TaskStatus enum constant associated with the given integer value.
     *
     * @param value The integer value representing a TaskStatus.
     * @return The corresponding TaskStatus enum constant.
     * @throws IllegalArgumentException if the provided value does not match any TaskStatus.
     */
    public static TaskStatus fromValue(int value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus value: " + value);
    }
}
