package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.SkillType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="skill")
@NamedQueries({
        @NamedQuery(name = "Skill.findAllSkills", query = "SELECT s FROM SkillEntity s"),
        @NamedQuery(name = "Skill.findSkillById", query = "SELECT s FROM SkillEntity s WHERE s.id = :id"),
        @NamedQuery(name = "Skill.findByName", query = "SELECT s FROM SkillEntity s WHERE s.name = :name"),
        @NamedQuery(name = "Skill.findAllById", query = "SELECT s FROM SkillEntity s WHERE s.id IN :ids"),
})
public class SkillEntity extends TagEntity {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SkillType type;

    @ManyToMany(mappedBy = "skills")
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    private Set<ProjectEntity> projects = new HashSet<>();

    // Getters and setters for type, users, and projects

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
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
}
