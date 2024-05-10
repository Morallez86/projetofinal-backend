package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name="user")

public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<ProjectEntity> projects = new HashSet<>();

    // Constructors, getters, and setters

    public UserEntity() {
        // Default constructor
    }

    public UserEntity(String username) {
        this.username = username;
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

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }

    public void addProject(ProjectEntity project) {
        this.projects.add(project);
        project.getUsers().add(this);
    }

    public void removeProject(ProjectEntity project) {
        this.projects.remove(project);
        project.getUsers().remove(this);
    }
}
