package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

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
                query = "SELECT r FROM ResourceEntity r WHERE r.expirationDate BETWEEN :now AND :oneWeekFromNow")
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
    

    public ResourceEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
