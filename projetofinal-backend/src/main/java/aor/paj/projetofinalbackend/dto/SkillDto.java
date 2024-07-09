package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * SkillDto is a Data Transfer Object (DTO) class representing a skill entity.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class SkillDto {

    private Long id;
    private String name;
    private int type;

    /**
     * Default constructor for SkillDto.
     */
    public SkillDto() {
    }

    /**
     * Constructs a SkillDto object with specified ID, name, and type.
     *
     * @param id   the ID of the skill.
     * @param name the name of the skill.
     * @param type the type of the skill.
     */
    public SkillDto(Long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * Retrieves the ID of the skill.
     *
     * @return the ID of the skill.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the skill.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the skill.
     *
     * @return the name of the skill.
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the skill.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the type of the skill.
     *
     * @return the type of the skill.
     */
    @XmlElement
    public int getType() {
        return type;
    }

    /**
     * Sets the type of the skill.
     *
     * @param type the type to set.
     */
    public void setType(int type) {
        this.type = type;
    }
}
