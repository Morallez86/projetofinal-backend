package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.UserEntity;

public class ProfileMapper {

    public static ProfileDto toDto(UserEntity user) {
        if (user == null) {
            return null;
        }
        ProfileDto dto = new ProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBiography(user.getBiography());
        dto.setVisibility(user.getVisibility());
        return dto;
    }

    public static UserEntity toEntity(ProfileDto dto) {
        if (dto == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setBiography(dto.getBiography());
        user.setVisibility(dto.getVisibility());
        return user;
    }
}
