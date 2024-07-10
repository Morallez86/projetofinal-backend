package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * UserProjectDto is a Data Transfer Object (DTO) class representing the relationship between a user and a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class UserProjectDto {

    private Long id;
    private Long userId;
    private boolean isAdmin;
    private Long projectId;
    private String username;
    private boolean active;
    private boolean online;

    /**
     * Default constructor for UserProjectDto.
     */
    public UserProjectDto() {
    }

    /**
     * Retrieves the ID of the user-project relationship.
     *
     * @return the id of the user-project relationship.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user-project relationship.
     *
     * @param id the id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the user ID associated with the user-project relationship.
     *
     * @return the user ID.
     */
    @XmlElement
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the user-project relationship.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Checks if the user is an admin for the project.
     *
     * @return true if the user is an admin, false otherwise.
     */
    @XmlElement
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Sets whether the user is an admin for the project.
     *
     * @param admin true if the user is an admin, false otherwise.
     */
    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Retrieves whether the user-project relationship is active.
     *
     * @return true if the relationship is active, false otherwise.
     */
    @XmlElement
    public boolean isActive() {
        return active;
    }

    /**
     * Sets whether the user-project relationship is active.
     *
     * @param active true if the relationship is active, false otherwise.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Retrieves the project ID associated with the user-project relationship.
     *
     * @return the project ID.
     */
    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project ID associated with the user-project relationship.
     *
     * @param projectId the project ID to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves whether the user is currently online.
     *
     * @return true if the user is online, false otherwise.
     */
    @XmlElement
    public boolean isOnline() {
        return online;
    }

    /**
     * Sets whether the user is currently online.
     *
     * @param online true if the user is online, false otherwise.
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Retrieves the username associated with the user-project relationship.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username associated with the user-project relationship.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
