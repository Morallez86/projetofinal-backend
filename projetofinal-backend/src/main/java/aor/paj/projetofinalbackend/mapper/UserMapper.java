package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.utils.Role;

public class UserMapper {

    public static UserDto toDto(UserEntity user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getValue());
        dto.setActive(user.getActive());
        dto.setPending(user.getPending());
        dto.setEmailToken(user.getEmailToken());
        dto.setBiography(user.getBiography());
        dto.setVisibility(user.getVisibility());
        dto.setActiveProject(user.getActiveProject());
        dto.setOnline(user.getOnline());
        return dto;
    }

    public static UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setActive(dto.getActive());
        user.setPending(dto.getPending());
        user.setEmailToken(dto.getEmailToken());
        user.setBiography(dto.getBiography());
        user.setVisibility(dto.getVisibility());
        user.setActiveProject(dto.getActiveProject());
        user.setOnline(dto.getOnline());
        return user;
    }
}
