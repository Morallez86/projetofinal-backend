package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;

public class ComponentMapper {

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
        if (entity.getProject() != null && entity.getProject().getId() != null) {
        dto.setProjectId(entity.getProject().getId());
        }
        return dto;
    }

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
