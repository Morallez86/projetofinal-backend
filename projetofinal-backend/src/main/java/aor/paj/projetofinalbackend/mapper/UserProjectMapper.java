package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;

public class UserProjectMapper {

    public static UserProjectEntity toEntity (UserProjectDto dto) {
        UserProjectEntity entity = new UserProjectEntity();
        entity.setUser(UserMapper.toEntity(dto.getUser()));
        entity.setId(dto.getId());
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        entity.setAdmin(dto.isAdmin());
        return entity;
    }
}
