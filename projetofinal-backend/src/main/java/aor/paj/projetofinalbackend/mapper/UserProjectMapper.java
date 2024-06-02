package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;

public class UserProjectMapper {

    public static UserProjectDto toDto(UserProjectEntity entity) {
        UserProjectDto dto = new UserProjectDto();
        dto.setId(entity.getId());
        dto.setUser(UserMapper.toDto(entity.getUser()));
        dto.setAdmin(entity.isAdmin());
        dto.setProject(ProjectMapper.toDto(entity.getProject()));
        return dto;
    }

    public static UserProjectEntity toEntity(UserProjectDto dto) {
        UserProjectEntity entity = new UserProjectEntity();
        entity.setId(dto.getId());
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        entity.setAdmin(dto.isAdmin());
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        return entity;
    }
}
