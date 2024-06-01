package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class TaskDto {

    private Long id;
    private String title;

    private String description;
    private LocalDateTime plannedStartingDate;
    private LocalDateTime startingDate;
    private LocalDateTime plannedEndingDate;

    private LocalDateTime endingDate;

    private int status;

    private int priority;

    private String contributors;

    private UserDto user;

    private TaskDto dependencies;

    private TaskDto dependentTasks;

    public TaskDto() {
    }


    @XmlElement
    public Long getId() {
        return id;
    }


    @XmlElement
    public String getTitle() {
        return title;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    @XmlElement
    public LocalDateTime getPlannedStartingDate() {
        return plannedStartingDate;
    }

    @XmlElement
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    @XmlElement
    public LocalDateTime getPlannedEndingDate() {
        return plannedEndingDate;
    }

    @XmlElement
    public LocalDateTime getEndingDate() {
        return endingDate;
    }

    @XmlElement
    public int getStatus() {
        return status;
    }

    @XmlElement
    public int getPriority() {
        return priority;
    }

    @XmlElement
    public String getContributors() {
        return contributors;
    }

    @XmlElement
    public UserDto getUser() {
        return user;
    }

    @XmlElement
    public TaskDto getDependencies() {
        return dependencies;
    }

    @XmlElement
    public TaskDto getDependentTasks() {
        return dependentTasks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlannedStartingDate(LocalDateTime plannedStartingDate) {
        this.plannedStartingDate = plannedStartingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public void setPlannedEndingDate(LocalDateTime plannedEndingDate) {
        this.plannedEndingDate = plannedEndingDate;
    }

    public void setEndingDate(LocalDateTime endingDate) {
        this.endingDate = endingDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setDependencies(TaskDto dependencies) {
        this.dependencies = dependencies;
    }

    public void setDependentTasks(TaskDto dependentTasks) {
        this.dependentTasks = dependentTasks;
    }
}