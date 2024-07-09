package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a resource within the project.
 * This class is used to transfer resource data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ResourceDto {

    private Long id;
    private String brand;
    private String name;
    private String supplier;
    private String description;
    private String observation;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expirationDate;

    private String identifier;
    private String contact;
    private List<Long> projectIds;
    private List<String> projectNames;

    /**
     * Default constructor.
     */
    public ResourceDto() {
    }

    /**
     * Retrieves the ID of the resource.
     *
     * @return the ID of the resource.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the brand of the resource.
     *
     * @return the brand of the resource.
     */
    @XmlElement
    public String getBrand() {
        return brand;
    }

    /**
     * Retrieves the name of the resource.
     *
     * @return the name of the resource.
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Retrieves the supplier of the resource.
     *
     * @return the supplier of the resource.
     */
    @XmlElement
    public String getSupplier() {
        return supplier;
    }

    /**
     * Retrieves the description of the resource.
     *
     * @return the description of the resource.
     */
    @XmlElement
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the expiration date of the resource.
     *
     * @return the expiration date of the resource.
     */
    @XmlElement
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Retrieves the identifier of the resource.
     *
     * @return the identifier of the resource.
     */
    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Retrieves the contact information for the resource.
     *
     * @return the contact information for the resource.
     */
    @XmlElement
    public String getContact() {
        return contact;
    }

    /**
     * Retrieves the IDs of the projects associated with the resource.
     *
     * @return the IDs of the projects associated with the resource.
     */
    @XmlElement
    public List<Long> getProjectIds() {
        return projectIds;
    }

    /**
     * Retrieves the names of the projects associated with the resource.
     *
     * @return the names of the projects associated with the resource.
     */
    @XmlElement
    public List<String> getProjectNames() {
        return projectNames;
    }

    /**
     * Sets the ID of the resource.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the brand of the resource.
     *
     * @param brand the brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Sets the name of the resource.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the supplier of the resource.
     *
     * @param supplier the supplier to set.
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * Sets the description of the resource.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the expiration date of the resource.
     *
     * @param expirationDate the expiration date to set.
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Sets the identifier of the resource.
     *
     * @param identifier the identifier to set.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Sets the contact information for the resource.
     *
     * @param contact the contact information to set.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Sets the IDs of the projects associated with the resource.
     *
     * @param projectIds the project IDs to set.
     */
    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    /**
     * Sets the names of the projects associated with the resource.
     *
     * @param projectNames the project names to set.
     */
    public void setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
    }

    /**
     * Retrieves any observation related to the resource.
     *
     * @return the observation related to the resource.
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Sets any observation related to the resource.
     *
     * @param observation the observation to set.
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }
}
