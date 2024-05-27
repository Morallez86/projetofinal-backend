package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.utils.SkillType;

public class SkillMapper {

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

    public static SkillEntity toEntity(SkillDto dto) {
        if (dto == null) {
            return null;
        }
        SkillEntity skill = new SkillEntity();
        skill.setName(dto.getName());
        skill.setType(SkillType.fromValue(dto.getType())); // Convert int value back to SkillType
        return skill;
    }
}