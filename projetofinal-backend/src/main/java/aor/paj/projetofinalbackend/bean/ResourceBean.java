package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ResourceDao;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import aor.paj.projetofinalbackend.mapper.ComponentMapper;
import aor.paj.projetofinalbackend.mapper.ResourceMapper;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResourceBean {

    @EJB
    private ServiceBean serviceBean;

    @EJB
    private ResourceDao resourceDao;

    public List<ResourceDto> allResources () {
        List<ResourceEntity> resourceEntities = resourceDao.findAll();
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());
    }
}
