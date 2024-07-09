package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object (DTO) representing a component within the project.
 * This class is used to transfer component data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class ComponentDto {

    private Long id;
    private String brand;
    private String name;
    private String supplier;
    private String description;
    private String contact;
    private String observation;
    private String identifier;
    private Long projectId;
    private Boolean availability;

    /**
     * Default constructor.
     */
    public ComponentDto() {
    }

    /**
     * Gets the ID of the component.
     *
     * @return the ID of the component.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Gets the brand of the component.
     *
     * @return the brand of the component.
     */
    @XmlElement
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the name of the component.
     *
     * @return the name of the component.
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Gets the supplier of the component.
     *
     * @return the supplier of the component.
     */
    @XmlElement
    public String getSupplier() {
        return supplier;
    }

    /**
     * Gets the description of the component.
     *
     * @return the description of the component.
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Gets the contact information for the component.
     *
     * @return the contact information for the component.
     */
    @XmlElement
    public String getContact() {
        return contact;
    }

    /**
     * Gets any observations about the component.
     *
     * @return any observations about the component.
     */
    @XmlElement
    public String getObservation() {
        return observation;
    }

    /**
     * Gets the identifier of the component.
     *
     * @return the identifier of the component.
     */
    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets the project ID associated with the component.
     *
     * @return the project ID associated with the component.
     */
    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Gets the availability status of the component.
     *
     * @return the availability status of the component.
     */
    @XmlElement
    public Boolean getAvailability() {
        return availability;
    }

    /**
     * Sets the ID of the component.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the brand of the component.
     *
     * @param brand the brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Sets the name of the component.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the supplier of the component.
     *
     * @param supplier the supplier to set.
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Sets the description of the component.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the contact information for the component.
     *
     * @param contact the contact information to set.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Sets any observations about the component.
     *
     * @param observation the observation to set.
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

    /**
     * Sets the identifier of the component.
     *
     * @param identifier the identifier to set.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Sets the project ID associated with the component.
     *
     * @param projectId the project ID to set.
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Sets the availability status of the component.
     *
     * @param availability the availability status to set.
     */
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
