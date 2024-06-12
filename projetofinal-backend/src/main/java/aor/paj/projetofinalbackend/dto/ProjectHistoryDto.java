package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class ProjectHistoryDto {
    private Long id;
    private String newDescription;

    private int type;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    private long userId;

    private long projectId;

    private long taskId;

    private String taskName;

    private String userName;

    private String title;

    public ProjectHistoryDto() {
    }

    @XmlElement
    public Long getId() {
        return id;
    }
    @XmlElement
    public String getNewDescription() {
        return newDescription;
    }
    @XmlElement
    public int getType() {
        return type;
    }
    @XmlElement
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @XmlElement
    public long getUserId() {
        return userId;
    }
    @XmlElement
    public long getProjectId() {
        return projectId;
    }

    @XmlElement
    public long getTaskId() {
        return taskId;
    }

    @XmlElement
    public String getUserName() {
        return userName;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    @XmlElement
    public String getTaskName() {
        return taskName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
