package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
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
        dto.setObservation(entity.getObservation());
        dto.setProjectIds(entity.getProjects().stream().map(ProjectEntity::getId).collect(Collectors.toList()));
        dto.setProjectNames(entity.getProjects().stream().map(ProjectEntity::getTitle).collect(Collectors.toList()));
        return dto;
    }

    public static ResourceEntity toEntity(ResourceDto dto) {
        ResourceEntity entity = new ResourceEntity();
        entity.setId(dto.getId());
        entity.setBrand(dto.getBrand());
        entity.setName(dto.getName());
        entity.setSupplier(dto.getSupplier());
        entity.setDescription(dto.getDescription());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setIdentifier(dto.getIdentifier());
        entity.setContact(dto.getContact());
        entity.setObservation(dto.getObservation());

        if (dto.getProjectIds() != null) {
            entity.setProjects(dto.getProjectIds().stream().map(id -> {
                ProjectEntity project = new ProjectEntity();
                project.setId(id);
                return project;
            }).collect(Collectors.toSet()));
        }
        return entity;
    }
}
