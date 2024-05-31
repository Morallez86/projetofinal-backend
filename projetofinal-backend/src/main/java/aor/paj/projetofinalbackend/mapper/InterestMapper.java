package aor.paj.projetofinalbackend.mapper;


import aor.paj.projetofinalbackend.dto.InterestDto;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.SkillEntity;

import java.util.HashSet;
import java.util.Set;

public class InterestMapper {

    public static InterestDto toDto(InterestEntity interest) {
        if (interest == null) {
            return null;
        }
        InterestDto dto = new InterestDto();
        dto.setId(interest.getId());
        dto.setName(interest.getName());
        return dto;
    }

    public static InterestEntity toEntity(InterestDto dto) {
        if (dto == null) {
            return null;
        }
        InterestEntity interest = new InterestEntity();
        interest.setName(dto.getName());
        return interest;
    }

    public static Set<InterestDto> listToDto (Set<InterestEntity> entitiesList){
        Set<InterestDto> listDto = new HashSet<>();
        for (InterestEntity entity : entitiesList) {
            InterestDto dtoConverted = InterestMapper.toDto(entity);
            listDto.add(dtoConverted);
        }
        return listDto;
    }

}
