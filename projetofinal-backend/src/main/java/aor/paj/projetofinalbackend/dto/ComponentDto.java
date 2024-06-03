package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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


    public ComponentDto() {
    }

    @XmlElement
    public Long getId() {
        return id;
    }
    @XmlElement
    public String getBrand() {
        return brand;
    }
    @XmlElement
    public String getName() {
        return name;
    }
    @XmlElement
    public String getSupplier() {
        return supplier;
    }
    @XmlElement
    public String getDescription() {
        return description;
    }
    @XmlElement
    public String getContact() {
        return contact;
    }
    @XmlElement
    public String getObservation() {
        return observation;
    }
    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    @XmlElement
    public Long getProjectId() {
        return projectId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
