package aor.paj.projetofinalbackend.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Data Transfer Object (DTO) representing an interest related to a project.
 * This class is used to transfer interest data between different layers of the application.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@XmlRootElement
public class InterestDto {

    private Long id;
    private String name;

    /**
     * Default constructor.
     */
    public InterestDto() {
    }

    /**
     * Constructor to initialize an InterestDto with ID and name.
     *
     * @param id the ID of the interest.
     * @param name the name of the interest.
     */
    public InterestDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the ID of the interest.
     *
     * @return the ID of the interest.
     */
    @XmlElement
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the interest.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the interest.
     *
     * @return the name of the interest.
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the interest.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

}
