package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * TagEntity is an abstract base class for tags in the application.
 * It represents common attributes and behaviors shared skill and interest entities.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@MappedSuperclass
public abstract class TagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the tag.
     * Generated automatically by the persistence provider.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the tag. It must be unique and cannot be updated once set.
     */
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    /**
     * The user who created the tag. This reference is immutable once set.
     * It is a many-to-one relationship with {@link UserEntity}.
     */
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, updatable = false)
    private UserEntity creator;

    // Common getters and setters

    /**
     * Retrieves the ID of the tag.
     *
     * @return the unique identifier of the tag.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the tag. Typically used by the persistence provider.
     *
     * @param id the unique identifier to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the tag.
     *
     * @return the name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tag. The name must be unique and cannot be updated once set.
     *
     * @param name the unique name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the creator of the tag.
     *
     * @return the user who created the tag.
     */
    public UserEntity getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the tag. This reference is immutable once set.
     *
     * @param creator the user to set as the creator of the tag.
     */
    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }
}
