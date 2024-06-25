package aor.paj.projetofinalbackend.pojo;

public class WorkplaceProjectCount {
    private String workplaceName;
    private long projectCount;
    private double percentage;

    // Constructors, getters, and setters
    public WorkplaceProjectCount(String workplaceName, long projectCount, double percentage) {
        this.workplaceName = workplaceName;
        this.projectCount = projectCount;
        this.percentage = percentage;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public long getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(long projectCount) {
        this.projectCount = projectCount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
