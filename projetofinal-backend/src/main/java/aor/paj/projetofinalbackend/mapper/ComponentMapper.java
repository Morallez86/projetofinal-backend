package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import org.modelmapper.ModelMapper;

public class ComponentMapper {
    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.typeMap(ComponentDto.class, ComponentEntity.class).addMappings(mapper -> mapper.skip(ComponentEntity::setProject));
    }

    public static ComponentEntity toEntity (ComponentDto dto) {
        ComponentEntity entity = modelMapper.map(dto, ComponentEntity.class);
        entity.setProject(ProjectMapper.toEntity(dto.getProject()));
        return entity;
    }
}
