package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;

/**
 * Mapper class for converting between UserProjectEntity and UserProjectDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class UserProjectMapper {

    /**
     * Converts a UserProjectEntity to a UserProjectDto.
     *
     * @param entity The UserProjectEntity to convert.
     * @return The corresponding UserProjectDto.
     */
    public static UserProjectDto toDto(UserProjectEntity entity) {
        UserProjectDto dto = new UserProjectDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setIsAdmin(entity.isAdmin());
        dto.setProjectId(entity.getProject().getId());
        dto.setUsername(entity.getUser().getUsername());
        dto.setActive(entity.isActive());
        dto.setOnline(entity.isOnline());
        return dto;
    }

    /**
     * Converts a UserProjectDto to a UserProjectEntity.
     *
     * @param dto The UserProjectDto to convert.
     * @return The corresponding UserProjectEntity.
     */
    public static UserProjectEntity toEntity(UserProjectDto dto) {
        UserProjectEntity entity = new UserProjectEntity();
        entity.setId(dto.getId());;
        entity.setIsAdmin(dto.isAdmin());
        entity.setActive(dto.isActive());
        return entity;
    }
}
