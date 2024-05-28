package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SkillDto {
    private Long id;
    private String name;
    private int type;

    public SkillDto() {
    }

    public SkillDto(Long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
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
    @XmlElement
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
