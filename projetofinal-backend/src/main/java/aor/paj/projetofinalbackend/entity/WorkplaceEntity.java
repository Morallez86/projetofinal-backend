package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "workplace")
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

    public WorkplaceEntity() {
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

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity user) {
        users.add(user);
        user.setWorkplace(this);
    }

    public void removeUser(UserEntity user) {
        users.remove(user);
        user.setWorkplace(null);
    }

    public void setComponents(Set<ComponentEntity> components) {
        this.components = components;
    }

    public void addComponent(ComponentEntity component) {
        components.add(component);
        component.setWorkplace(this);
    }

    public void removeComponent(ComponentEntity component) {
        components.remove(component);
        component.setWorkplace(null);
    }
}

