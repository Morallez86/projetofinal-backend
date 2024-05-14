package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project")
public class UserProjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    // Constructors, getters, setters

    public UserProjectEntity() {
        // Default constructor
    }

    public UserProjectEntity(UserEntity user, ProjectEntity project, boolean isAdmin) {
        this.user = user;
        this.project = project;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

