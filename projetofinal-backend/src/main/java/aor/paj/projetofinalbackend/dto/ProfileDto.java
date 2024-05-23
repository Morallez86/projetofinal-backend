package aor.paj.projetofinalbackend.dto;

import java.time.LocalDateTime;

public class ProfileDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String biography;
    private Boolean visibility;

    private

    public ProfileDto() {
    }

    public ProfileDto(Long id, String username, String firstName, String lastName, String email, String biography, Boolean visibility, Boolean activeProject, String profileImageType, String profileImagePath) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.biography = biography;
        this.visibility = visibility;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

}
