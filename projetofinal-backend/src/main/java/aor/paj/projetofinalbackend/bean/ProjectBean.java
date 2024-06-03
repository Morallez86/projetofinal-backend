package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ComponentDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ResourceDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class ProjectBean {

    @EJB
    private UserDao userDao;

    @EJB
    private ServiceBean serviceBean;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private ComponentDao componentDao;

    @EJB
    private ResourceDao resourceDao;

    private ProjectMapper projectMapper = new ProjectMapper();


    public void addProject(ProjectDto projectDto, String token) {
        // Extract user ID from the token
        Long userId = serviceBean.getUserIdFromToken(token);

        // Find the user by ID
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }
        System.out.println("1");


        // Convert DTO to entity
        ProjectEntity projectEntity = projectMapper.toEntity(projectDto);

        // Set the owner of the project
        projectEntity.setOwner(user);
        System.out.println(projectDto.getStatus());

        // Persist the project entity
        projectDao.persist(projectEntity);
        ProjectEntity project = projectDao.findProjectById(projectEntity.getId());
        for (ComponentEntity componentEntity : projectEntity.getComponents()) {
            componentEntity.setProject(project);
            componentDao.merge(componentEntity);
        }

        for (ResourceEntity resourceEntity : projectEntity.getResources()) {
            Set<ProjectEntity> projectEntities = resourceEntity.getProjects();
            projectEntities.add(project);
            resourceEntity.setProjects(projectEntities);
            resourceDao.merge(resourceEntity);
        }
    }
}
