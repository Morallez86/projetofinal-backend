package aor.paj.projetofinalbackend.dto;

import java.util.HashSet;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
