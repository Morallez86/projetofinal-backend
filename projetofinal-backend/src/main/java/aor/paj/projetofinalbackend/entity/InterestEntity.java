package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * InterestEntity represents an interest that users and projects can have.
 * It extends the {@link TagEntity} class, inheriting common attributes such as id, name, and creator.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name="interest")
@NamedQueries({
        @NamedQuery(name = "Interest.findAllInterests", query = "SELECT i FROM InterestEntity i"),
        @NamedQuery(name = "Interest.findInterestById", query = "SELECT i FROM InterestEntity i WHERE i.id = :id"),
        @NamedQuery(name = "Interest.findByName", query = "SELECT i FROM InterestEntity i WHERE i.name = :name"),
        @NamedQuery(name = "Interest.findAllById", query = "SELECT i FROM InterestEntity i WHERE i.id IN :ids"),
})
public class InterestEntity extends TagEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The users who have this interest.
     * This is a many-to-many relationship, and the inverse side is managed by {@link UserEntity}.
     */
    @ManyToMany(mappedBy = "interests")
    private Set<UserEntity> users = new HashSet<>();

    /**
     * The projects that involve this interest.
     * This is a many-to-many relationship, and the inverse side is managed by {@link ProjectEntity}.
     */
    @ManyToMany(mappedBy = "interests")
    private Set<ProjectEntity> projects = new HashSet<>();

    // Getters and setters for users and projects

    /**
     * Retrieves the users who have this interest.
     *
     * @return a set of users who have this interest.
     */
    public Set<UserEntity> getUsers() {
        return users;
    }

    /**
     * Sets the users who have this interest.
     *
     * @param users a set of users who have this interest.
     */
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    /**
     * Retrieves the projects that involve this interest.
     *
     * @return a set of projects that involve this interest.
     */
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    /**
     * Sets the projects that involve this interest.
     *
     * @param projects a set of projects that involve this interest.
     */
    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
