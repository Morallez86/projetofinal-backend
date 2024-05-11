package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="project")

public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> userProjects = new HashSet<>();

    // Constructors, getters, and setters

    public ProjectEntity() {
        // Default constructor
    }

    public ProjectEntity(String name) {
        this.name = name;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserProjectEntity> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(Set<UserProjectEntity> userProjects) {
        this.userProjects = userProjects;
    }

    public void addUserProject(UserProjectEntity userProject) {
        userProjects.add(userProject);
        userProject.setProject(this);
    }

    public void removeUserProject(UserProjectEntity userProject) {
        userProjects.remove(userProject);
        userProject.setProject(null);
    }
}
