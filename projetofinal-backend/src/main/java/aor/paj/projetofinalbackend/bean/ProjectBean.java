package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.HashSet;
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

    @EJB
    private InterestDao interestDao;

    private ProjectMapper projectMapper = new ProjectMapper();


    @Transactional
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


        Set<InterestEntity> existingInterestEntity = new HashSet<>();

        for (InterestEntity interestEntity : projectEntity.getInterests()) {
            if (interestEntity.getCreator()==null) {
                interestEntity.setCreator(user);
            }
        }

        for (InterestEntity interestEntity2: projectEntity.getInterests()) {
            if (interestEntity2.getId()!=null){
                System.out.println("in");
                projectEntity.getInterests().remove(interestEntity2);
                existingInterestEntity.add(interestEntity2);
            }
        }

        for (SkillEntity skillEntity : projectEntity.getSkills()) {
            if (skillEntity.getCreator()==null) {
                skillEntity.setCreator(user);
            }
        }

        // Persist the project entity
        projectDao.persist(projectEntity);
        ProjectEntity project = projectDao.findProjectById(projectEntity.getId());
        for (ComponentEntity componentEntity : projectEntity.getComponents()) {
            componentEntity.setProject(project);
            componentDao.merge(componentEntity);
        }

        for (ResourceEntity resourceEntity : projectEntity.getResources()) {
            System.out.println("entrou");
            Set<ProjectEntity> projectEntities = resourceEntity.getProjects();
            if (!projectEntities.contains(project)) {
                System.out.println("entrou2");
                projectEntities.add(project);
                System.out.println("entrou3");
                resourceEntity.setProjects(projectEntities);
                System.out.println("entrou4");
                resourceDao.merge(resourceEntity);
                System.out.println("entrou5");
            }
        }

        Set<InterestEntity> completeInterestSet = project.getInterests();
        completeInterestSet.addAll(existingInterestEntity);

        project.setInterests(completeInterestSet);
        projectDao.merge(project);
        /*projectEntity.getInterests().addAll(existingInterestEntity);
        projectEntity.setInterests(projectEntity.getInterests());
        projectDao.merge(projectEntity);*/

        }
    }

