package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;

public class UserProjectMapper {

    public static UserProjectDto toDto(UserProjectEntity entity) {
        UserProjectDto dto = new UserProjectDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setIsAdmin(entity.isAdmin());
        dto.setProjectId(entity.getProject().getId());
        dto.setUsername(entity.getUser().getUsername());
        return dto;
    }

    public static UserProjectEntity toEntity(UserProjectDto dto) {
        UserProjectEntity entity = new UserProjectEntity();
        entity.setId(dto.getId());;
        entity.setIsAdmin(dto.isAdmin());
        return entity;
    }
}
