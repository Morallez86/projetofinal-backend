package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;

public class WorkplaceMapper {

    public static WorkplaceDto toDto(WorkplaceEntity workplace) {
        if (workplace == null) {
            return null;
        }
        WorkplaceDto dto = new WorkplaceDto();
        dto.setId(workplace.getId());
        dto.setName(workplace.getName());
        return dto;
    }

    public static WorkplaceEntity toEntity(WorkplaceDto dto) {
        if (dto == null) {
            return null;
        }
        WorkplaceEntity workplace = new WorkplaceEntity();
        workplace.setName(dto.getName());
        return workplace;
    }
}
