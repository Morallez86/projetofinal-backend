package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

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

    public WorkplaceEntity() {
    }

    // Getters and Setters for all attributes including the new projects relationship

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

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public Set<ComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
