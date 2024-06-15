package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDto {

    private Long id;
    private String title;

    private String description;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedStartingDate;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startingDate;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedEndingDate;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endingDate;

    private int status;

    private int priority;

    private String contributors;

    private Long userId;

    private List<TaskDto> dependencies;

    private List<TaskDto> dependentTasks;

    private Long projectId;

    private String userName;

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
    public List<TaskDto> getDependencies() {
        return dependencies;
    }

    @XmlElement
    public List<TaskDto> getDependentTasks() {
        return dependentTasks;
    }

    @XmlElement
    public Long getUserId() {
        return userId;
    }

    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    @XmlElement
    public String getUserName() {
        return userName;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setDependencies(List<TaskDto> dependencies) {
        this.dependencies = dependencies;
    }

    public void setDependentTasks(List<TaskDto> dependentTasks) {
        this.dependentTasks = dependentTasks;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
