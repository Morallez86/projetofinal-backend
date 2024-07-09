package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Entity class representing a resource.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Entity
@Table(name = "resource")
@NamedQueries({
        @NamedQuery(name = "ResourceEntity.findById",
                query = "SELECT r FROM ResourceEntity r WHERE r.id = :id"),
        @NamedQuery(name = "Resource.findByName", query = "SELECT r FROM ResourceEntity r WHERE r.name = :name"),
        @NamedQuery(name = "ResourceEntity.findProjectsByResourceId",
                query = "SELECT p FROM ResourceEntity r JOIN r.projects p WHERE r.id = :id"),
        @NamedQuery(name = "Resource.findAllOrderedByName", query = "SELECT r FROM ResourceEntity r ORDER BY r.name ASC"),
        @NamedQuery(name = "Resource.getTotalResourcesCount", query = "SELECT COUNT(r) FROM ResourceEntity r"),
        @NamedQuery(name = "Resource.findByKeywordOrderedByName",
                query = "SELECT r FROM ResourceEntity r WHERE r.name LIKE CONCAT('%', :keyword, '%') OR r.brand LIKE CONCAT('%', :keyword, '%') OR r.supplier LIKE CONCAT('%', :keyword, '%') OR r.identifier LIKE CONCAT('%', :keyword, '%') ORDER BY r.name ASC"
        ),
        @NamedQuery(name = "Resource.countByKeyword", query = "SELECT COUNT(r) FROM ResourceEntity r WHERE r.name LIKE CONCAT('%', :keyword, '%') OR r.brand LIKE CONCAT('%', :keyword, '%') OR r.supplier LIKE CONCAT('%', :keyword, '%') OR r.identifier LIKE CONCAT('%', :keyword, '%')"),
        @NamedQuery(name = "ResourceEntity.findResourcesExpiringWithinWeek",
                query = "SELECT r FROM ResourceEntity r WHERE r.expirationDate BETWEEN :now AND :oneWeekFromNow"),
        @NamedQuery(name = "ResourceEntity.findUnusedResources",
                query = "SELECT r FROM ResourceEntity r WHERE r.projects IS EMPTY")
})
public class ResourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "supplier", nullable = false)
    private String supplier;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "expiration_date", nullable = true)
    private LocalDateTime expirationDate;

    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    @Column(name = "observation")
    private String observation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_resource",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<ProjectEntity> projects = new HashSet<>();

    /**
     * Default constructor for ResourceEntity.
     */
    public ResourceEntity() {
    }

    /**
     * Get the ID of the resource.
     *
     * @return The ID of the resource.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the brand of the resource.
     *
     * @return The brand of the resource.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the brand of the resource.
     *
     * @param brand The brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get the name of the resource.
     *
     * @return The name of the resource.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the resource.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the supplier of the resource.
     *
     * @return The supplier of the resource.
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * Set the supplier of the resource.
     *
     * @param supplier The supplier to set.
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Get the description of the resource.
     *
     * @return The description of the resource.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the resource.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the contact information associated with the resource.
     *
     * @return The contact information.
     */
    public String getContact() {
        return contact;
    }

    /**
     * Set the contact information associated with the resource.
     *
     * @param contact The contact information to set.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Get the expiration date of the resource.
     *
     * @return The expiration date of the resource.
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Set the expiration date of the resource.
     *
     * @param expirationDate The expiration date to set.
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Get the unique identifier of the resource.
     *
     * @return The identifier of the resource.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Set the unique identifier of the resource.
     *
     * @param identifier The identifier to set.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Get the set of projects associated with the resource.
     *
     * @return The set of projects associated with the resource.
     */
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    /**
     * Set the set of projects associated with the resource.
     *
     * @param projects The set of projects to set.
     */
    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }

    /**
     * Set the ID of the resource.
     *
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the observations associated with the resource.
     *
     * @return The set of observations associated with the resource.
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Set the observation or notes related to the resource.
     *
     * @param observation The observation or notes to set.
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }
}
