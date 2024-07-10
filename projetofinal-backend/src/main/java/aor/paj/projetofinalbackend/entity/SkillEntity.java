package aor.paj.projetofinalbackend.entity;

import aor.paj.projetofinalbackend.utils.SkillType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * SkillEntity represents a skill that users and projects can possess.
 * It extends the {@link TagEntity} class, inheriting common attributes such as id, name, and creator.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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

    /**
     * The type of the skill, represented by {@link SkillType}.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SkillType type;

    /**
     * The users who possess this skill.
     * <p>
     * This is a many-to-many relationship, and the inverse side is managed by {@link UserEntity}.
     * </p>
     */
    @ManyToMany(mappedBy = "skills")
    private Set<UserEntity> users = new HashSet<>();

    /**
     * The projects that require this skill.
     * <p>
     * This is a many-to-many relationship, and the inverse side is managed by {@link ProjectEntity}.
     * </p>
     */
    @ManyToMany(mappedBy = "skills")
    private Set<ProjectEntity> projects = new HashSet<>();

    // Getters and setters for type, users, and projects

    /**
     * Retrieves the type of the skill.
     *
     * @return the type of the skill.
     */
    public SkillType getType() {
        return type;
    }

    /**
     * Sets the type of the skill.
     *
     * @param type the type of the skill.
     */
    public void setType(SkillType type) {
        this.type = type;
    }

    /**
     * Retrieves the users who possess this skill.
     *
     * @return a set of users who possess this skill.
     */
    public Set<UserEntity> getUsers() {
        return users;
    }

    /**
     * Sets the users who possess this skill.
     *
     * @param users a set of users who possess this skill.
     */
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    /**
     * Retrieves the projects that require this skill.
     *
     * @return a set of projects that require this skill.
     */
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    /**
     * Sets the projects that require this skill.
     *
     * @param projects a set of projects that require this skill.
     */
    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
