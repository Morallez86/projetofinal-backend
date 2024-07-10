package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity class representing the association between a user and a project,
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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

    @Column(name = "online", nullable = false)
    private boolean online = false;

    @Embedded
    private ProjectTimerChat projectTimerChat;

    /**
     * Default constructor for UserProjectEntity.
     */
    public UserProjectEntity() {
    }

    /**
     * Get the unique identifier of the user project association.
     *
     * @return The unique identifier of the user project association.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the user project association.
     *
     * @param id The unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the user associated with the project.
     *
     * @return The user associated with the project.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Set the user associated with the project.
     *
     * @param user The user to set for the project association.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Get the project associated with the user.
     *
     * @return The project associated with the user.
     */
    public ProjectEntity getProject() {
        return project;
    }

    /**
     * Set the project associated with the user.
     *
     * @param project The project to set for the user project association.
     */
    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    /**
     * Check if the user has admin privileges for the project.
     *
     * @return True if the user has admin privileges, false otherwise.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Set whether the user has admin privileges for the project.
     *
     * @param isAdmin True to set the user as an admin, false otherwise.
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Check if the user project association is active.
     *
     * @return True if the user project association is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set whether the user project association is active.
     *
     * @param active True to activate the user project association, false to deactivate.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get the embedded project timer chat associated with the user project.
     *
     * @return The embedded project timer chat.
     */
    public ProjectTimerChat getProjectTimerChat() {
        return projectTimerChat;
    }

    /**
     * Set the embedded project timer chat associated with the user project.
     *
     * @param projectTimerChat The project timer chat to set.
     */
    public void setProjectTimerChat(ProjectTimerChat projectTimerChat) {
        this.projectTimerChat = projectTimerChat;
    }

    /**
     * Check if the user associated with the project is currently online.
     *
     * @return True if the user associated with the project is online, false otherwise.
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Set whether the user associated with the project is online.
     *
     * @param online True to mark the user as online, false otherwise.
     */
    public void setOnline(boolean online) {
        this.online = online;
    }
}
