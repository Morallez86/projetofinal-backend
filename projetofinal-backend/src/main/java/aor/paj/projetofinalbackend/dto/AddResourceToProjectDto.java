package aor.paj.projetofinalbackend.dto;

/**
 * Data Transfer Object (DTO) used for adding a resource to a project.
 * This class is used to transfer resource data when adding it to a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class AddResourceToProjectDto {
    private ResourceDto resource;

    /**
     * Gets the resource to be added to the project.
     *
     * @return The resource to be added.
     */
    public ResourceDto getResource() {
        return resource;
    }

    /**
     * Sets the resource to be added to the project.
     *
     * @param resource The component to set.
     */
    public void setResource(ResourceDto resource) {
        this.resource = resource;
    }
}
