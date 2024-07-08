package aor.paj.projetofinalbackend.dto;

import aor.paj.projetofinalbackend.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;
import java.util.List;

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

    public ResourceDto() {
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
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @XmlElement
    public String getIdentifier() {
        return identifier;
    }

    @XmlElement
    public String getContact() {
        return contact;
    }

    @XmlElement
    public List<Long> getProjectIds() {
        return projectIds;
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

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<String> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
    }
}
