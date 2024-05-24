package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.SkillEntity;

public class SkillMapper {

    public static SkillDto toDto(SkillEntity skill) {
        if (skill == null) {
            return null;
        }
        SkillDto dto = new SkillDto();
        dto.setId(skill.getId());
        dto.setType(skill.getType());
        dto.setName(skill.getName());
        return dto;
    }

    public static SkillEntity toEntity(SkillDto dto) {
        if (dto == null) {
            return null;
        }
        SkillEntity skill = new SkillEntity();
        skill.setName(dto.getName());
        skill.setType(dto.getType());
        return skill;
    }
}






