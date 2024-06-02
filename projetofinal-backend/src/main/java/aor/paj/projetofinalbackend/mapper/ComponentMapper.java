package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import org.modelmapper.ModelMapper;

public class ComponentMapper {
    public static ComponentEntity toEntity (ComponentDto dto) {
        ComponentEntity entity = new ComponentEntity();
        entity.setId(dto.getId());
        entity.setBrand(dto.getBrand());
        entity.setName(dto.getName());
        entity.setSupplier(dto.getSupplier());
        entity.setDescription(dto.getDescription());
        entity.setContact(dto.getContact());
        entity.setObservation(dto.getObservation());
        entity.setIdentifier(dto.getIdentifier());
        return entity;
    }
}
