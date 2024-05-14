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

    public InterestEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
