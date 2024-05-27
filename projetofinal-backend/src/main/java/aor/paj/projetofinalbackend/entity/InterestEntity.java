package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="interest")
@NamedQueries({
        @NamedQuery(name = "Interest.findAllInterests", query = "SELECT i FROM InterestEntity i"),
        @NamedQuery(name = "Interest.findByName", query = "SELECT i FROM InterestEntity i WHERE i.name = :name")
})
public class InterestEntity extends TagEntity {

    private static final long serialVersionUID = 1L;

    @ManyToMany(mappedBy = "interests")
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(mappedBy = "interests")
    private Set<ProjectEntity> projects = new HashSet<>();

    // Getters and setters for users and projects

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
}
