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
 * UserDto is a Data Transfer Object (DTO) class representing a user entity with essential information.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private int role;
    private Boolean active;
    private Boolean pending;
    private String emailToken;
    private String biography;
    private Boolean visibility;
    private Boolean activeProject;
    private LocalDateTime registTime;
    private String password;
    private String workplace;
    private Boolean online;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private List<LocalDateTime> lastTimeChatOpen;

    /**
     * Retrieves the user's workplace name.
     *
     * @return the workplace name.
     */
    @XmlElement
    public String getWorkplace() {
        return workplace;
    }

    /**
     * Sets the workplace name for the user.
     *
     * @param workplace the workplace name to set.
     */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    /**
     * Retrieves the user id.
     *
     * @return the user id.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the user id.
     *
     * @param id the user id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name.
     */
    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return the last name.
     */
    @XmlElement
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username.
     */
    @XmlElement
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the email address.
     */
    @XmlElement
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the role of the user.
     *
     * @return the role.
     */
    @XmlElement
    public int getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set.
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Checks if the user is active.
     *
     * @return true if active, false otherwise.
     */
    @XmlElement
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the activity status of the user.
     *
     * @param active the activity status to set.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Checks if the user account is pending.
     *
     * @return true if pending, false otherwise.
     */
    @XmlElement
    public Boolean getPending() {
        return pending;
    }

    /**
     * Sets the pending status of the user account.
     *
     * @param pending the pending status to set.
     */
    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    /**
     * Retrieves the email verification token for the user.
     *
     * @return the email verification token.
     */
    @XmlElement
    public String getEmailToken() {
        return emailToken;
    }

    /**
     * Sets the email verification token for the user.
     *
     * @param emailToken the email verification token to set.
     */
    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    /**
     * Retrieves the biography of the user.
     *
     * @return the biography.
     */
    @XmlElement
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the biography of the user.
     *
     * @param biography the biography to set.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Checks if the user visibility is active.
     *
     * @return true if visibility is active, false otherwise.
     */
    @XmlElement
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility status of the user.
     *
     * @param visibility the visibility status to set.
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Checks if the user is currently active in a project.
     *
     * @return true if active in a project, false otherwise.
     */
    @XmlElement
    public Boolean getActiveProject() {
        return activeProject;
    }

    /**
     * Sets the activity status of the user in a project.
     *
     * @param activeProject the activity status in a project to set.
     */
    public void setActiveProject(Boolean activeProject) {
        this.activeProject = activeProject;
    }

    /**
     * Retrieves the registration timestamp of the user.
     *
     * @return the registration timestamp.
     */
    @XmlElement
    public LocalDateTime getRegistTime() {
        return registTime;
    }

    /**
     * Sets the registration timestamp of the user.
     *
     * @param registTime the registration timestamp to set.
     */
    public void setRegistTime(LocalDateTime registTime) {
        this.registTime = registTime;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password.
     */
    @XmlElement
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is currently online.
     *
     * @return true if online, false otherwise.
     */
    @XmlElement
    public Boolean getOnline() {
        return online;
    }

    /**
     * Sets the online status of the user.
     *
     * @param online the online status to set.
     */
    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     * Retrieves the list of timestamps indicating the last time the user's chat was opened.
     *
     * @return the list of last chat open timestamps.
     */
    @XmlElement
    public List<LocalDateTime> getLastTimeChatOpen() {
        return lastTimeChatOpen;
    }

    /**
     * Sets the list of timestamps indicating the last time the user's chat was opened.
     *
     * @param lastTimeChatOpen the list of last chat open timestamps to set.
     */
    public void setLastTimeChatOpen(List<LocalDateTime> lastTimeChatOpen) {
        this.lastTimeChatOpen = lastTimeChatOpen;
    }

    /**
     * Returns a string representation of the UserDto object.
     *
     * @return a string representation containing all attributes of the UserDto.
     */
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", active=" + active +
                ", pending=" + pending +
                ", emailToken='" + emailToken + '\'' +
                ", biography='" + biography + '\'' +
                ", visibility=" + visibility +
                ", activeProject=" + activeProject +
                ", registTime=" + registTime +
                ", password='" + password + '\'' +
                ", workplace='" + workplace + '\'' +
                ", online=" + online +
                '}';
    }
}
