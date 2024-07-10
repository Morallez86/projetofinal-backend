package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * WorkplaceDto is a Data Transfer Object (DTO) class representing information about a workplace.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class WorkplaceDto {

    private Long id;
    private String name;

    /**
     * Default constructor for WorkplaceDto.
     */
    public WorkplaceDto() {
    }

    /**
     * Constructor for WorkplaceDto that initializes the id and name of the workplace.
     *
     * @param id   the unique identifier of the workplace.
     * @param name the name of the workplace.
     */
    public WorkplaceDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the ID of the workplace.
     *
     * @return the id of the workplace.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the workplace.
     *
     * @param id the id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the workplace.
     *
     * @return the name of the workplace.
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the workplace.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

}
