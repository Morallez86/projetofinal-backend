package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project")
@NamedQueries({
        @NamedQuery(name = "UserProjectEntity.findByUserAndProject",
                query = "SELECT up FROM UserProjectEntity up WHERE up.user.id = :userId AND up.project.id = :projectId"),
        @NamedQuery(name = "UserProjectEntity.findByUserId",
                query = "SELECT up FROM UserProjectEntity up WHERE up.user.id = :userId")

        @NamedQuery(name = "UserProjectEntity.findProjectsByUserId",
                query = "SELECT up.project FROM UserProjectEntity up WHERE up.user.id = :userId"),
        @NamedQuery(name = "UserProjectEntity.countProjectsByUserId",
                query = "SELECT COUNT(up) FROM UserProjectEntity up WHERE up.user.id = :userId")
})
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

    @Column(name = "active", nullable=false)
    private boolean active = false;

    public UserProjectEntity() {

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

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActive() {
        return active;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

