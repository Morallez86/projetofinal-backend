package aor.paj.projetofinalbackend.pojo;

/**
 * POJO class representing workplace project count with workplace name, project count, and percentage.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class WorkplaceProjectCount {
    private String workplaceName;
    private long projectCount;
    private double percentage;

    /**
     * Constructs a new WorkplaceProjectCount object with the specified parameters.
     *
     * @param workplaceName The name of the workplace.
     * @param projectCount The count of projects associated with the workplace.
     * @param percentage The percentage of projects relative to total projects.
     */
    public WorkplaceProjectCount(String workplaceName, long projectCount, double percentage) {
        this.workplaceName = workplaceName;
        this.projectCount = projectCount;
        this.percentage = percentage;
    }

    /**
     * Retrieves the name of the workplace.
     *
     * @return The name of the workplace.
     */
    public String getWorkplaceName() {
        return workplaceName;
    }

    /**
     * Sets the name of the workplace.
     *
     * @param workplaceName The name of the workplace to set.
     */
    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    /**
     * Retrieves the count of projects associated with the workplace.
     *
     * @return The count of projects.
     */
    public long getProjectCount() {
        return projectCount;
    }

    /**
     * Sets the count of projects associated with the workplace.
     *
     * @param projectCount The count of projects to set.
     */
    public void setProjectCount(long projectCount) {
        this.projectCount = projectCount;
    }

    /**
     * Retrieves the percentage of projects relative to total projects.
     *
     * @return The percentage of projects.
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Sets the percentage of projects relative to total projects.
     *
     * @param percentage The percentage of projects to set.
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
