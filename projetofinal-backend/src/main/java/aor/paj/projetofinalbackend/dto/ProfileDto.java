package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a user profile within the system.
 * This class is used to transfer profile data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class ProfileDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String biography;
    private Boolean visibility;
    private String workplace;
    private List<InterestDto> interests;
    private List<SkillDto> skills;
    private int role;

    /**
     * Default constructor.
     */
    public ProfileDto() {
    }

    /**
     * Retrieves the ID of the profile.
     *
     * @return the ID of the profile.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the profile.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the username of the profile.
     *
     * @return the username of the profile.
     */
    @XmlElement
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the profile.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the first name of the profile.
     *
     * @return the first name of the profile.
     */
    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the profile.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the profile.
     *
     * @return the last name of the profile.
     */
    @XmlElement
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the profile.
     *
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email of the profile.
     *
     * @return the email of the profile.
     */
    @XmlElement
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the profile.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the biography of the profile.
     *
     * @return the biography of the profile.
     */
    @XmlElement
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the biography of the profile.
     *
     * @param biography the biography to set.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Retrieves the visibility status of the profile.
     *
     * @return the visibility status of the profile.
     */
    @XmlElement
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility status of the profile.
     *
     * @param visibility the visibility status to set.
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Retrieves the workplace of the profile.
     *
     * @return the workplace of the profile.
     */
    @XmlElement
    public String getWorkplace() {
        return workplace;
    }

    /**
     * Sets the workplace of the profile.
     *
     * @param workplace the workplace to set.
     */
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    /**
     * Retrieves the list of interests associated with the profile.
     *
     * @return the list of interests associated with the profile.
     */
    @XmlElement
    public List<InterestDto> getInterests() {
        return interests;
    }

    /**
     * Sets the list of interests associated with the profile.
     *
     * @param interests the list of interests to set.
     */
    public void setInterests(List<InterestDto> interests) {
        this.interests = interests;
    }

    /**
     * Retrieves the list of skills associated with the profile.
     *
     * @return the list of skills associated with the profile.
     */
    @XmlElement
    public List<SkillDto> getSkills() {
        return skills;
    }

    /**
     * Sets the list of skills associated with the profile.
     *
     * @param skills the list of skills to set.
     */
    public void setSkills(List<SkillDto> skills) {
        this.skills = skills;
    }

    /**
     * Retrieves the role of the profile.
     *
     * @return the role of the profile.
     */
    @XmlElement
    public int getRole() {
        return role;
    }

    /**
     * Sets the role of the profile.
     *
     * @param role the role to set.
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the ProfileDto object.
     *
     * @return a string representation of the ProfileDto object.
     */
    @Override
    public String toString() {
        return "ProfileDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", biography='" + biography + '\'' +
                ", visibility=" + visibility +
                ", workplace='" + workplace + '\'' +
                ", interests=" + interests +
                ", skills=" + skills +
                '}';
    }
}
