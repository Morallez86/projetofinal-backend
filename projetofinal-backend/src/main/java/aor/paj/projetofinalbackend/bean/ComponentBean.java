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
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

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
}
