package aor.paj.projetofinalbackend.utils;

/**
 * Enum representing different statuses of a project.
 * Each status has an associated integer value.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public enum ProjectStatus {

    /**
     * Planning phase of the project.
     */
    PLANNING(100),

    /**
     * Project is ready to start.
     */
    READY(200),

    /**
     * Project is in progress.
     */
    IN_PROGRESS(300),

    /**
     * Project has been finished successfully.
     */
    FINISHED(400),

    /**
     * Project has been cancelled.
     */
    CANCELLED(500);

    private final int value;

    /**
     * Constructor for ProjectStatus enum.
     *
     * @param value The integer value associated with the status.
     */
    ProjectStatus(int value) {
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
     * Retrieves the ProjectStatus enum constant associated with the given integer value.
     *
     * @param value The integer value representing a ProjectStatus.
     * @return The corresponding ProjectStatus enum constant.
     * @throws IllegalArgumentException if the provided value does not match any ProjectStatus.
     */
    public static ProjectStatus fromValue(int value) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid ProjectStatus value: " + value);
    }
}

