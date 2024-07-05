package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "component")
@NamedQueries({
        @NamedQuery(name = "Component.findComponentById", query = "SELECT c FROM ComponentEntity c WHERE c.id = :id"),
        @NamedQuery(name = "Component.findByName", query = "SELECT c FROM ComponentEntity c WHERE c.availability = true AND c.name = :name"),
        @NamedQuery(
                name = "Component.findFirstAvailableComponentByName",
                query = "SELECT c FROM ComponentEntity c WHERE c.name = :name AND c.availability = true AND c.workplace.id = :workplaceId ORDER BY c.id"
        ),
        @NamedQuery(name = "Component.getTotalComponentsCount", query = "SELECT COUNT(c) FROM ComponentEntity c"),
        @NamedQuery(name = "Component.findByWorkplaceId", query = "SELECT c FROM ComponentEntity c WHERE c.workplace.id = :workplaceId"),
        @NamedQuery(name = "Component.findAllOrderedByName", query = "SELECT c FROM ComponentEntity c ORDER BY c.name ASC"),
        @NamedQuery(name = "Component.findByKeywordOrderedByName",
                query = "SELECT c FROM ComponentEntity c WHERE c.name LIKE CONCAT('%', :keyword, '%') OR c.brand LIKE CONCAT('%', :keyword, '%') OR c.supplier LIKE CONCAT('%', :keyword, '%') OR c.identifier LIKE CONCAT('%', :keyword, '%') ORDER BY c.name ASC"
        ),
        @NamedQuery(name = "Component.countByKeyword", query = "SELECT COUNT(c) FROM ComponentEntity c WHERE c.name LIKE CONCAT('%', :keyword, '%') OR c.brand LIKE CONCAT('%', :keyword, '%') OR c.supplier LIKE CONCAT('%', :keyword, '%') OR c.identifier LIKE CONCAT('%', :keyword, '%')"),
        @NamedQuery(
                name = "Component.findAvailableComponentsGroupedByName",
                query = "SELECT c.name FROM ComponentEntity c WHERE c.availability = true AND c.workplace.id = :workplaceId GROUP BY c.name"
        ),
        @NamedQuery(name = "Component.countTotalComponentsByWorkplace", query = "SELECT COUNT(c) FROM ComponentEntity c WHERE c.workplace.name = :workplaceName"),
        @NamedQuery(name = "Component.countAvailableComponentsByWorkplace", query = "SELECT COUNT(c) FROM ComponentEntity c WHERE c.workplace.name = :workplaceName AND c.availability = true")
})
public class ComponentEntity implements Serializable {

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

    @Column(name = "description")
    private String description;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "observation")
    private String observation;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id")
    private WorkplaceEntity workplace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = true)
    private ProjectEntity project;

    @Column(name = "availability")
    private Boolean availability = true;


    public ComponentEntity() {
        // Default constructor
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public WorkplaceEntity getWorkplace() {
        return workplace;
    }

    public void setWorkplace(WorkplaceEntity workplace) {
        this.workplace = workplace;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "ComponentEntity{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", supplier='" + supplier + '\'' +
                ", description='" + description + '\'' +
                ", contact='" + contact + '\'' +
                ", observation='" + observation + '\'' +
                ", identifier='" + identifier + '\'' +
                ", availability=" + availability +
                '}';
    }
}
