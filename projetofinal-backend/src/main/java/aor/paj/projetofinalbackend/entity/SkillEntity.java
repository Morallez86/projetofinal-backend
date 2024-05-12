package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.SkillType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="skill")

public class SkillEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SkillType type;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserEntity creator;

    @ManyToMany(mappedBy = "skills")
    private Set<UserEntity> users = new HashSet<>();

    // Constructors, getters, setters

    public SkillEntity() {
        // Default constructor
    }

    public SkillEntity(String name, SkillType type, UserEntity creator) {
        this.name = name;
        this.type = type;
        this.creator = creator;
    }

    // Getters and setters

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

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
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

    public void addUser(UserEntity user) {
        this.users.add(user);
        user.getSkills().add(this);
    }

    public void removeUser(UserEntity user) {
        this.users.remove(user);
        user.getSkills().remove(this);
    }
}