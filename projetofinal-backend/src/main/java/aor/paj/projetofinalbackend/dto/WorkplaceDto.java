package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;
@XmlRootElement
public class WorkplaceDto {

    private Long id;
    private String name;

    // Constructors, Getters, and Setters

    public WorkplaceDto() {
    }

    public WorkplaceDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @XmlElement
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
