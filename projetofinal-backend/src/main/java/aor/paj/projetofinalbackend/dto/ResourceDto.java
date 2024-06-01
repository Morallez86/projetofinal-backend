package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

public class ResourceDto {

    private Long id;
    private String brand;
    private String name;
    private String supplier;
    private String description;
    private LocalDateTime expirationDate;
    private String identifier;

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
}