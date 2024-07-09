package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.utils.SkillType;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapper class for converting between SkillEntity and SkillDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class SkillMapper {

    /**
     * Converts a SkillEntity to a SkillDto.
     *
     * @param skill The SkillEntity to convert.
     * @return The corresponding SkillDto.
     */
    public static SkillDto toDto(SkillEntity skill) {
        if (skill == null) {
            return null;
        }
        SkillDto dto = new SkillDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setType(skill.getType().getValue()); // Convert SkillType to its int value
        return dto;
    }

    /**
     * Converts a SkillDto to a SkillEntity.
     *
     * @param dto The SkillDto to convert.
     * @return The corresponding SkillEntity.
     */
    public static SkillEntity toEntity(SkillDto dto) {
        if (dto == null) {
            return null;
        }
        SkillEntity skill = new SkillEntity();
        skill.setId(dto.getId());
        skill.setName(dto.getName());
        skill.setType(SkillType.fromValue(dto.getType()));  // Convert int value back to SkillType
        return skill;
    }

    /**
     * Converts a Set of SkillEntity objects to a Set of SkillDto objects.
     *
     * @param entitiesList The Set of SkillEntity objects to convert.
     * @return The corresponding Set of SkillDto objects.
     */
    public static Set <SkillDto> listToDto (Set<SkillEntity> entitiesList){
        Set<SkillDto> listDto = new HashSet<>();
        for (SkillEntity entity : entitiesList) {
            SkillDto dtoConverted = SkillMapper.toDto(entity);
            listDto.add(dtoConverted);
        }
        return listDto;
    }
}
