package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a project.
 * This class is used to transfer project data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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

    /**
     * Default constructor for ProjectDto.
     */
    public ProjectDto() {
    }

    /**
     * Gets the ID of the project.
     *
     * @return The ID of the project.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Gets the title of the project.
     *
     * @return The title of the project.
     */
    @XmlElement
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the project.
     *
     * @return The description of the project.
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Gets the motivation for the project.
     *
     * @return The motivation for the project.
     */
    @XmlElement
    public String getMotivation() {
        return motivation;
    }

    /**
     * Gets the status of the project.
     *
     * @return The status of the project.
     */
    @XmlElement
    public int getStatus() {
        return status;
    }

    /**
     * Gets the maximum number of users for the project.
     *
     * @return The maximum number of users for the project.
     */
    @XmlElement
    public int getMaxUsers() {
        return maxUsers;
    }

    /**
     * Gets the owner ID of the project.
     *
     * @return The owner ID of the project.
     */
    @XmlElement
    public Long getOwner() {
        return owner;
    }

    /**
     * Gets the approval status of the project.
     *
     * @return True if the project is approved, false otherwise.
     */
    @XmlElement
    public Boolean isApproved() {
        return approved;
    }

    /**
     * Gets the creation date of the project.
     *
     * @return The creation date of the project.
     */
    @XmlElement
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the approval date of the project.
     *
     * @return The approval date of the project.
     */
    @XmlElement
    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    /**
     * Gets the starting date of the project.
     *
     * @return The starting date of the project.
     */
    @XmlElement
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    /**
     * Gets the planned end date of the project.
     *
     * @return The planned end date of the project.
     */
    @XmlElement
    public LocalDateTime getPlannedEndDate() {
        return plannedEndDate;
    }

    /**
     * Gets the end date of the project.
     *
     * @return The end date of the project.
     */
    @XmlElement
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Gets the workplace associated with the project.
     *
     * @return The workplace associated with the project.
     */
    @XmlElement
    public WorkplaceDto getWorkplace() {
        return workplace;
    }

    /**
     * Gets the list of user-project associations for the project.
     *
     * @return The list of user-project associations.
     */
    @XmlElement
    public List<UserProjectDto> getUserProjectDtos() {
        return userProjectDtos;
    }

    /**
     * Gets the list of components for the project.
     *
     * @return The list of components.
     */
    @XmlElement
    public List<ComponentDto> getComponents() {
        return components;
    }

    /**
     * Gets the list of resources for the project.
     *
     * @return The list of resources.
     */
    @XmlElement
    public List<ResourceDto> getResources() {
        return resources;
    }

    /**
     * Gets the list of tasks for the project.
     *
     * @return The list of tasks.
     */
    @XmlElement
    public List<TaskDto> getTasks() {
        return tasks;
    }

    /**
     * Gets the list of interests in the project.
     *
     * @return The list of interests.
     */
    @XmlElement
    public List<InterestDto> getInterests() {
        return interests;
    }

    /**
     * Gets the list of skills for the project.
     *
     * @return The list of skills.
     */
    @XmlElement
    public List<SkillDto> getSkills() {
        return skills;
    }

    /**
     * Gets the list of project history records.
     *
     * @return The list of project history records.
     */
    @XmlElement
    public List<ProjectHistoryDto> getHistoryrecords() {
        return historyrecords;
    }

    /**
     * Gets the list of chat messages for the project.
     *
     * @return The list of chat messages.
     */
    @XmlElement
    public List<ChatMessageDto> getChatMessage() {
        return chatMessage;
    }

    /**
     * Sets the ID of the project.
     *
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the title of the project.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the project.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the motivation for the project.
     *
     * @param motivation The motivation to set.
     */
    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    /**
     * Sets the status of the project.
     *
     * @param status The status to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets the maximum number of users for the project.
     *
     * @param maxUsers The maximum number of users to set.
     */
    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    /**
     * Sets the owner ID of the project.
     *
     * @param owner The owner ID to set.
     */
    public void setOwner(Long owner) {
        this.owner = owner;
    }

    /**
     * Sets the approval status of the project.
     *
     * @param approved The approval status to set.
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
     * Sets the creation date of the project.
     *
     * @param creationDate The creation date to set.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Sets the approval date of the project.
     *
     * @param approvedDate The approval date to set.
     */
    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     * Sets the starting date of the project.
     *
     * @param startingDate The starting date to set.
     */
    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Sets the planned end date of the project.
     *
     * @param plannedEndDate The planned end date to set.
     */
    public void setPlannedEndDate(LocalDateTime plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    /**
     * Sets the end date of the project.
     *
     * @param endDate The end date to set.
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the workplace associated with the project.
     *
     * @param workplace The workplace to set.
     */
    public void setWorkplace(WorkplaceDto workplace) {
        this.workplace = workplace;
    }

    /**
     * Sets the list of user-project associations for the project.
     *
     * @param userProjectDtos The list of user-project associations to set.
     */
    public void setUserProjectDtos(List<UserProjectDto> userProjectDtos) {
        this.userProjectDtos = userProjectDtos;
    }

    /**
     * Sets the list of components for the project.
     *
     * @param components The list of components to set.
     */
    public void setComponents(List<ComponentDto> components) {
        this.components = components;
    }

    /**
     * Sets the list of resources for the project.
     *
     * @param resources The list of resources to set.
     */
    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    /**
     * Sets the list of tasks for the project.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }
    /**
     * Sets the list of interests in the project.
     *
     * @param interests The list of interests to set.
     */
    public void setInterests(List<InterestDto> interests) {
        this.interests = interests;
    }

    /**
     * Sets the list of skills for the project.
     *
     * @param skills The list of skills to set.
     */
    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    /**
     * Sets the list of project history records.
     *
     * @param historyrecords The list of project history records to set.
     */
    public void setHistoryrecords(List<ProjectHistoryDto> historyrecords) {
        this.historyrecords = historyrecords;
    }

    /**
     * Sets the list of chat messages for the project.
     *
     * @param chatMessage The list of chat messages to set.
     */
    public void setChatMessage(List<ChatMessageDto> chatMessage) {
        this.chatMessage = chatMessage;
    }
}
