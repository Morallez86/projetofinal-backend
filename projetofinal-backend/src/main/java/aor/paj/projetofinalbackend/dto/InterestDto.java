package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterestDto {
    private Long id;
    private String name;

    public InterestDto() {
    }

    public InterestDto(Long id, String name) {
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
