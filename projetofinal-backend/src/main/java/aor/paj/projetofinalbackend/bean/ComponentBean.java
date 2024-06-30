package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ComponentDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ComponentMapper;
import aor.paj.projetofinalbackend.mapper.SkillMapper;
import aor.paj.projetofinalbackend.utils.ListConverter;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ComponentBean {

    @EJB
    private ServiceBean serviceBean;

    @EJB
    private UserDao userDao;

    @EJB
    private ComponentDao componentDao;

    @EJB
    private ProjectDao projectDao;

    public void addComponentDefault( ComponentDto dto) {
        ComponentEntity componentEntity = ComponentMapper.toEntity(dto);
        componentDao.persist(componentEntity);
    }

    @Transactional
    public void addComponentInProject( ComponentDto dto, Long projectId) {
        ProjectEntity project = projectDao.findProjectById(projectId);
        ComponentEntity componentEntity = ComponentMapper.toEntity(dto);
        project.getComponents().add(componentEntity);
        projectDao.merge(project);
        componentEntity.setProject(project);
        componentDao.merge(componentEntity);
    }

    public void updateComponent(ComponentDto dto) {
        System.out.println("1");
        ComponentEntity componentEntity = ComponentMapper.toEntity(dto);
        System.out.println("2");
        ProjectEntity project = projectDao.findProjectById(dto.getProjectId());
        System.out.println("3");
        componentEntity.setProject(project);
        System.out.println("4");
        componentDao.merge(componentEntity);
}

    public List<ComponentDto> allComponents (int page, int limit) {
        List<ComponentEntity> componentEntities = componentDao.findAllOrderedByName(page, limit);
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }
    public long getTotalComponentsCount () {
        return componentDao.getTotalComponentsCount();
    }

    public List<ComponentDto> allComponentsSearch (int page, int limit, String keyWord) {
        List<ComponentEntity> componentEntities = componentDao.findByKeywordOrderedByName(page, limit, keyWord);
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    public long getTotalCountBySearch (String keyWord) {
        return componentDao.countByKeyword(keyWord);
    }

    public List<ComponentDto> getAllWithoutFilters () {
        List<ComponentEntity> componentEntities = componentDao.findAll();
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    public List<ComponentDto> getAllByWorkplaceId(Long workplaceId) {
        List<ComponentEntity> componentEntities = componentDao.findByWorkplaceId(workplaceId);
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    public List<String> findAvailableComponentsGroupedByName (Long workplace){
        return componentDao.findAvailableComponentsGroupedByName(workplace);
    }
}


