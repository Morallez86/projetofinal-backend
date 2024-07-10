package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Entity class representing a workplace where users and projects are associated.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "workplace")
@NamedQueries({
        @NamedQuery(name = "Workplace.findWorkplaceByName", query = "SELECT w FROM WorkplaceEntity w WHERE w.name = :name"),
        @NamedQuery(name = "Workplace.findAllWorkplaces", query = "SELECT w FROM WorkplaceEntity w"),
        @NamedQuery(
                name = "Workplace.findProjectCountPerWorkplace",
                query = "SELECT w.name, COUNT(p.id) as projectCount FROM WorkplaceEntity w LEFT JOIN w.projects p GROUP BY w.name"
        )
})
public class WorkplaceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "workplace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "workplace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComponentEntity> components = new HashSet<>();

    @OneToMany(mappedBy = "workplace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectEntity> projects = new HashSet<>();

    /**
     * Default constructor for WorkplaceEntity.
     */
    public WorkplaceEntity() {
    }

    /**
     * Get the unique identifier of the workplace.
     *
     * @return The unique identifier of the workplace.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the workplace.
     *
     * @param id The unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the workplace.
     *
     * @return The name of the workplace.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the workplace.
     *
     * @param name The name to set for the workplace.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the users associated with the workplace.
     *
     * @return The set of users associated with the workplace.
     */
    public Set<UserEntity> getUsers() {
        return users;
    }

    /**
     * Set the users associated with the workplace.
     *
     * @param users The set of users to associate with the workplace.
     */
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    /**
     * Get the components associated with the workplace.
     *
     * @return The set of components associated with the workplace.
     */
    public Set<ComponentEntity> getComponents() {
        return components;
    }

    /**
     * Set the components associated with the workplace.
     *
     * @param components The set of components to associate with the workplace.
     */
    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }

    /**
     * Get the projects associated with the workplace.
     *
     * @return The set of projects associated with the workplace.
     */
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    /**
     * Set the projects associated with the workplace.
     *
     * @param projects The set of projects to associate with the workplace.
     */
    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
