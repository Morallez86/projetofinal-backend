package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;

/**
 * Data Transfer Object (DTO) used for adding an interest to a project.
 * This class is used to transfer interest data when adding it to a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class AddInterestToProjectDto {
    private InterestDto interest;

    /**
     * Gets the interest to be added to the project.
     *
     * @return The interest to be added.
     */
    @XmlElement
    public InterestDto getInterest() {
        return interest;
    }

    /**
     * Sets the interest to be added to the project.
     *
     * @param interest The component to set.
     */
    public void setInterest(InterestDto interest) {
        this.interest = interest;
    }
}
