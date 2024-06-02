package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ResourceEntity;

import java.util.stream.Collectors;

public class ResourceMapper {

    public static ResourceDto toDto(ResourceEntity entity) {
        ResourceDto dto = new ResourceDto();
        dto.setId(entity.getId());
        dto.setBrand(entity.getBrand());
        dto.setName(entity.getName());
        dto.setSupplier(entity.getSupplier());
        dto.setDescription(entity.getDescription());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setIdentifier(entity.getIdentifier());
        dto.setContact(entity.getContact());
        dto.setProjects(entity.getProjects().stream().map(ProjectMapper::toDto).collect(Collectors.toList()));
        return dto;
    }

    public static ResourceEntity toEntity(ResourceDto dto) {
        ResourceEntity entity = new ResourceEntity();
        entity.setBrand(dto.getBrand());
        entity.setName(dto.getName());
        entity.setSupplier(dto.getSupplier());
        entity.setDescription(dto.getDescription());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setIdentifier(dto.getIdentifier());
        entity.setContact(dto.getContact());
        entity.setProjects(dto.getProjects().stream().map(ProjectMapper::toEntity).collect(Collectors.toSet()));
        return entity;
    }
}
