package aor.paj.projetofinalbackend.mapper;

import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import aor.paj.projetofinalbackend.utils.ListConverter;
import org.modelmapper.ModelMapper;

public class ResourceMapper {

    public static ResourceEntity toEntity (ResourceDto dto){
        ResourceEntity entity = new ResourceEntity();
        entity.setId(dto.getId());
        entity.setBrand(dto.getBrand());
        entity.setSupplier(dto.getSupplier());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setContact(dto.getContact());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setIdentifier(dto.getIdentifier());

        return entity;
    }
}
