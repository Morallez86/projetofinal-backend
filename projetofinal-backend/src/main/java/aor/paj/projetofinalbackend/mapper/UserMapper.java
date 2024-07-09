package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.UserDto;
import aor.paj.projetofinalbackend.entity.UserEntity;

/**
 * Mapper class for converting between UserEntity and UserDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class UserMapper {

    /**
     * Converts a UserEntity to a UserDto.
     *
     * @param user The UserEntity to convert.
     * @return The corresponding UserDto.
     */
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

    /**
     * Converts a UserDto to a UserEntity.
     *
     * @param dto The UserDto to convert.
     * @return The corresponding UserEntity.
     */
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
