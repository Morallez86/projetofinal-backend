package aor.paj.projetofinalbackend.mapper;


import aor.paj.projetofinalbackend.dto.InterestDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapper class for converting between InterestEntity and InterestDto.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class InterestMapper {

    /**
     * Converts an InterestEntity to an InterestDto.
     *
     * @param interest The InterestEntity to convert.
     * @return The corresponding InterestDto.
     */
    public static InterestDto toDto(InterestEntity interest) {
        if (interest == null) {
            return null;
        }
        InterestDto dto = new InterestDto();
        dto.setId(interest.getId());
        dto.setName(interest.getName());
        return dto;
    }

    /**
     * Converts an InterestDto to an InterestEntity.
     *
     * @param dto The InterestDto to convert.
     * @return The corresponding InterestEntity.
     */
    public static InterestEntity toEntity(InterestDto dto) {
        if (dto == null) {
            return null;
        }
        InterestEntity interest = new InterestEntity();
        interest.setName(dto.getName());
        interest.setId(dto.getId());
        return interest;
    }

    /**
     * Converts a Set of InterestEntity objects to a Set of InterestDto objects.
     *
     * @param entitiesList The Set of InterestEntity objects to convert.
     * @return A Set of InterestDto objects corresponding to the input Set of InterestEntity objects.
     */
    public static Set<InterestDto> listToDto (Set<InterestEntity> entitiesList){
        Set<InterestDto> listDto = new HashSet<>();
        for (InterestEntity entity : entitiesList) {
            InterestDto dtoConverted = InterestMapper.toDto(entity);
            listDto.add(dtoConverted);
        }
        return listDto;
    }

}
