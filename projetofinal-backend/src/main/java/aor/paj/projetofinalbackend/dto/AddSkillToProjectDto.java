package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object (DTO) used for adding a skill to a project.
 * This class is used to transfer skill data when adding it to a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class AddSkillToProjectDto {
    private SkillDto skill;

    /**
     * Gets the skill to be added to the project.
     *
     * @return The skill to be added.
     */
    @XmlElement
    public SkillDto getSkill() {
        return skill;
    }

    /**
     * Sets the skill to be added to the project.
     *
     * @param skill The component to set.
     */
    public void setSkill(SkillDto skill) {
        this.skill = skill;
    }
}
