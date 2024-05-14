package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="interest")
public class InterestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private UserEntity creator;

    @ManyToMany(mappedBy = "interests")
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(mappedBy = "interests")
    private Set<ProjectEntity> projects = new HashSet<>();

    public InterestEntity() {
    }

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

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }

    public void addProject(ProjectEntity project) {
        this.projects.add(project);
        project.getInterests().add(this);
    }

    public void removeProject(ProjectEntity project) {
        this.projects.remove(project);
        project.getInterests().remove(this);
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
        user.getInterests().add(this);
    }

    public void removeUser(UserEntity user) {
        this.users.remove(user);
        user.getInterests().remove(this);
    }
}
