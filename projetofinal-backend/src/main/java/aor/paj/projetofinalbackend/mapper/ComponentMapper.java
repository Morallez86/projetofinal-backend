package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;

/**
 * Mapper class for converting between ComponentEntity and ComponentDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class ComponentMapper {

    /**
     * Converts a ComponentEntity to a ComponentDto.
     *
     * @param entity The ComponentEntity to convert.
     * @return The corresponding ComponentDto.
     */
    public static ComponentDto toDto(ComponentEntity entity) {
        ComponentDto dto = new ComponentDto();
        dto.setId(entity.getId());
        dto.setBrand(entity.getBrand());
        dto.setName(entity.getName());
        dto.setSupplier(entity.getSupplier());
        dto.setDescription(entity.getDescription());
        dto.setContact(entity.getContact());
        dto.setObservation(entity.getObservation());
        dto.setIdentifier(entity.getIdentifier());
        dto.setAvailability(entity.getAvailability());
        if (entity.getProject() != null && entity.getProject().getId() != null) {
        dto.setProjectId(entity.getProject().getId());
        }
        return dto;
    }

    /**
     * Converts a ComponentDto to a ComponentEntity.
     *
     * @param dto The ComponentDto to convert.
     * @return The corresponding ComponentEntity.
     */
    public static ComponentEntity toEntity(ComponentDto dto) {
        ComponentEntity entity = new ComponentEntity();
        entity.setId(dto.getId());
        entity.setBrand(dto.getBrand());
        entity.setName(dto.getName());
        entity.setSupplier(dto.getSupplier());
        entity.setDescription(dto.getDescription());
        entity.setContact(dto.getContact());
        entity.setObservation(dto.getObservation());
        entity.setIdentifier(dto.getIdentifier());
        return entity;
    }
}
