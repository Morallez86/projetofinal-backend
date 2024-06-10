package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ResourceDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
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

    public List<ResourceDto> allResources (int page, int limit) {
        List<ResourceEntity> resourceEntities = resourceDao.findAllOrderedByName(page,limit);
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());
    }

    public void updateResource (ResourceDto dto) {
        ResourceEntity resourcedData = resourceDao.findById(dto.getId());
        ResourceEntity resourceEntity = ResourceMapper.toEntity(dto);
        resourceEntity.setProjects(resourcedData.getProjects());
        resourceDao.merge(resourceEntity);
    }

    public long getTotalResourcesCount () {
        return resourceDao.getTotalResourcesCount();
    }

    public List<ResourceDto> allResourcesSearch (int page, int limit, String keyWord) {
        List<ResourceEntity> resourceEntities = resourceDao.findByKeywordOrderedByName(page, limit, keyWord);
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());
    }

    public long getTotalCountBySearch (String keyWord) {
        return resourceDao.countByKeyword(keyWord);
    }

    public List<ResourceDto> getAllWithoutFilters () {
        List<ResourceEntity> resourceEntities = resourceDao.findAll();
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());

    }

}
