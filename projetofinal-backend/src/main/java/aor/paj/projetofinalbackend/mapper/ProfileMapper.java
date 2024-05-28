package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ProfileDto;
import aor.paj.projetofinalbackend.dto.InterestDto;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

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

        List<InterestDto> interests = user.getInterests().stream()
                .map(ProfileMapper::interestToDto)
                .collect(Collectors.toList());
        dto.setInterests(interests);

        List<SkillDto> skills = user.getSkills().stream()
                .map(ProfileMapper::skillToDto)
                .collect(Collectors.toList());
        dto.setSkills(skills);

        if (user.getWorkplace() != null) {
            dto.setWorkplace(user.getWorkplace().getName());
        }

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

    private static InterestDto interestToDto(InterestEntity interest) {
        if (interest == null) {
            return null;
        }

        return new InterestDto(interest.getId(), interest.getName());
    }

    private static SkillDto skillToDto(SkillEntity skill) {
        if (skill == null) {
            return null;
        }

        return new SkillDto(skill.getId(), skill.getName(), skill.getType().getValue());
    }
}
