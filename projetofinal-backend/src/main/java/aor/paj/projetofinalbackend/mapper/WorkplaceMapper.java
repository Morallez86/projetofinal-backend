package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;

/**
 * Mapper class for converting between WorkplaceEntity and WorkplaceDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class WorkplaceMapper {

    /**
     * Converts a WorkplaceEntity to a WorkplaceDto.
     *
     * @param workplace The WorkplaceEntity to convert.
     * @return The corresponding WorkplaceDto.
     */
    public static WorkplaceDto toDto(WorkplaceEntity workplace) {
        if (workplace == null) {
            return null;
        }
        WorkplaceDto dto = new WorkplaceDto();
        dto.setId(workplace.getId());
        dto.setName(workplace.getName());
        return dto;
    }

    /**
     * Converts a WorkplaceDto to a WorkplaceEntity.
     *
     * @param dto The WorkplaceDto to convert.
     * @return The corresponding WorkplaceEntity.
     */
    public static WorkplaceEntity toEntity(WorkplaceDto dto) {
        if (dto == null) {
            return null;
        }
        WorkplaceEntity workplace = new WorkplaceEntity();
        workplace.setName(dto.getName());
        return workplace;
    }
}
