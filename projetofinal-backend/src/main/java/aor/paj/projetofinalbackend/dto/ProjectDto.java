package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
public class ProjectDto {

    private Long id;
    private String title;
    private String description;
    private String motivation;
    private int status;
    private int maxUsers;
    private Long owner;
    private Boolean approved;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime approvedDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startingDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime plannedEndDate;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endDate;

    private WorkplaceDto workplace;

    private List<UserProjectDto> userProjectDtos;
    private List<ComponentDto> components;
    private List<ResourceDto> resources;
    private List<TaskDto> tasks;
    private List<InterestDto> interests;
    private List<SkillDto> skills;
    private List<ProjectHistoryDto> historyrecords;
    private List<ChatMessageDto> chatMessage;

    public ProjectDto() {
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
    public String getMotivation() {
        return motivation;
    }

    @XmlElement
    public int getStatus() {
        return status;
    }

    @XmlElement
    public int getMaxUsers() {
        return maxUsers;
    }

    @XmlElement
    public Long getOwner() {
        return owner;
    }

    @XmlElement
    public Boolean isApproved() {
        return approved;
    }

    @XmlElement
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    @XmlElement
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    @XmlElement
    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    @XmlElement
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @XmlElement
    public WorkplaceDto getWorkplace() {
        return workplace;
    }

    @XmlElement
    public List<UserProjectDto> getUserProjectDtos() {
        return userProjectDtos;
    }

    @XmlElement
    public List<ComponentDto> getComponents() {
        return components;
    }

    @XmlElement
    public List<ResourceDto> getResources() {
        return resources;
    }

    @XmlElement
    public List<TaskDto> getTasks() {
        return tasks;
    }

    @XmlElement
    public List<InterestDto> getInterests() {
        return interests;
    }

    @XmlElement
    public List<SkillDto> getSkills() {
        return skills;
    }

    @XmlElement
    public List<ProjectHistoryDto> getHistoryrecords() {
        return historyrecords;
    }

    @XmlElement
    public List<ChatMessageDto> getChatMessage() {
        return chatMessage;
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

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setWorkplace(WorkplaceDto workplace) {
        this.workplace = workplace;
    }

    public void setUserProjectDtos(List<UserProjectDto> userProjectDtos) {
        this.userProjectDtos = userProjectDtos;
    }

    public void setComponents(List<ComponentDto> components) {
        this.components = components;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public void setInterests(List<InterestDto> interests) {
        this.interests = interests;
    }

    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    public void setHistoryrecords(List<ProjectHistoryDto> historyrecords) {
        this.historyrecords = historyrecords;
    }

    public void setChatMessage(List<ChatMessageDto> chatMessage) {
        this.chatMessage = chatMessage;
    }
}
