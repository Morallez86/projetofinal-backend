package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import aor.paj.projetofinalbackend.utils.ListConverter;
import org.modelmapper.ModelMapper;

public class ResourceMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.typeMap(ResourceDto.class, ResourceEntity.class).addMappings(mapper -> mapper.skip(ResourceEntity::setProjects));
    }

    public static ResourceEntity toEntity (ResourceDto dto){
        ResourceEntity entity =  modelMapper.map(dto, ResourceEntity.class);
        return entity;
    }
}
