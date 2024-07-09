package aor.paj.projetofinalbackend.dto;

/**
 * Data Transfer Object (DTO) used for adding a component to a project.
 * This class is used to transfer component data when adding it to a project.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class AddComponentToProjectDto {
    private ComponentDto component;

    /**
     * Gets the component to be added to the project.
     *
     * @return The component to be added.
     */
    public ComponentDto getComponent() {
        return component;
    }

    /**
     * Sets the component to be added to the project.
     *
     * @param component The component to set.
     */
    public void setComponent(ComponentDto component) {
        this.component = component;
    }
}
