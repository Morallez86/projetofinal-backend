package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_project")
@NamedQueries({
        @NamedQuery(name = "UserProjectEntity.findByUserAndProjectActive",
                query = "SELECT up FROM UserProjectEntity up WHERE up.user.id = :userId AND up.project.id = :projectId AND up.active = true"),
        @NamedQuery(name = "UserProjectEntity.findByUserIdActive",
                query = "SELECT up FROM UserProjectEntity up WHERE up.user.id = :userId AND up.active = true"),

        @NamedQuery(name = "UserProjectEntity.findProjectsByUserIdActive",
                query = "SELECT up.project FROM UserProjectEntity up WHERE up.user.id = :userId AND up.active = true"),
        @NamedQuery(name = "UserProjectEntity.countProjectsByUserIdActive",
                query = "SELECT COUNT(up) FROM UserProjectEntity up WHERE up.user.id = :userId AND up.active = true"),
        @NamedQuery(name = "UserProjectEntity.findAdminsByProjectIdActive",
                query = "SELECT up.user FROM UserProjectEntity up WHERE up.project.id = :projectId AND up.isAdmin = true AND up.active = true"),
        @NamedQuery(name = "UserProjectEntity.countActiveUsersByProjectId",
                query = "SELECT COUNT(up) FROM UserProjectEntity up WHERE up.project.id = :projectId AND up.active = true")
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

    @Embedded
    private ProjectTimerChat projectTimerChat;

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

    public ProjectTimerChat getProjectTimerChat() {
        return projectTimerChat;
    }

    public void setProjectTimerChat(ProjectTimerChat projectTimerChat) {
        this.projectTimerChat = projectTimerChat;
    }
}

