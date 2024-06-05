package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.security.JwtUtil;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import aor.paj.projetofinalbackend.utils.TaskStatus;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @EJB
    private UserProjectDao userProjectDao;

    @EJB
    private SkillDao skillDao;

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

        Set<TaskEntity> taskEntities = new HashSet<>();
        TaskEntity uniqueTask = new TaskEntity();
        uniqueTask.setTitle("Final Apresentation");
        uniqueTask.setDescription("Last task");
        uniqueTask.setPlannedStartingDate(projectEntity.getPlannedEndDate());
        uniqueTask.setPlannedEndingDate(projectEntity.getPlannedEndDate());
        uniqueTask.setUser(user);
        uniqueTask.setStatus(TaskStatus.TODO);
        uniqueTask.setPriority(TaskPriority.LOW);
        taskEntities.add(uniqueTask);
        projectEntity.setTasks(taskEntities);

        Set<InterestEntity> existingInterestEntity = new HashSet<>();

        for (InterestEntity interestEntity : projectEntity.getInterests()) {
            if (interestEntity.getCreator()==null) {
                interestEntity.setCreator(user);
            }
        }

        Iterator<InterestEntity> interestIterator = projectEntity.getInterests().iterator();
        while (interestIterator.hasNext()) {
            InterestEntity interestEntity2 = interestIterator.next();
            if (interestEntity2.getId() != null) {
                interestIterator.remove();
                existingInterestEntity.add(interestEntity2);
                InterestEntity interest = interestDao.findById(interestEntity2.getId());
                interestEntity2.setName(interest.getName());
                interestEntity2.setCreator(interest.getCreator());
            }
        }

        Set<SkillEntity> existingSkillEntity = new HashSet<>();

        for (SkillEntity skillEntity : projectEntity.getSkills()) {
            if (skillEntity.getCreator()==null) {
                skillEntity.setCreator(user);
            }
        }

        Iterator<SkillEntity> iterator = projectEntity.getSkills().iterator();
        while (iterator.hasNext()) {
            SkillEntity skillEntity2 = iterator.next();
            if (skillEntity2.getId() != null) {
                iterator.remove();
                existingSkillEntity.add(skillEntity2);
                SkillEntity skill = skillDao.findById(skillEntity2.getId());
                skillEntity2.setType(skill.getType());
                skillEntity2.setCreator(skill.getCreator());
            }
        }

        Set<ComponentEntity> existingComponentEntity = new HashSet<>();
        Iterator<ComponentEntity> componentIterator = projectEntity.getComponents().iterator();
        while (componentIterator.hasNext()) {
            ComponentEntity componentEntity = componentIterator.next();
            if (componentEntity.getId()!=null) {
                componentIterator.remove();
                existingComponentEntity.add(componentEntity);
                ComponentEntity component = componentDao.findComponentById(componentEntity.getId());
                componentEntity.setBrand(component.getBrand());
                componentEntity.setName(component.getName());
                componentEntity.setSupplier(component.getSupplier());
                componentEntity.setContact(component.getContact());
                componentEntity.setIdentifier(component.getIdentifier());
                componentEntity.setDescription(component.getDescription());
                componentEntity.setObservation(component.getObservation());
            }
        }

        Set<ResourceEntity> existingResourceEntity= new HashSet<>();
        Iterator<ResourceEntity> resourceIterator = projectEntity.getResources().iterator();
        while (resourceIterator.hasNext()) {
            ResourceEntity resourceEntity = resourceIterator.next();
            if (resourceEntity.getId()!=null){
                resourceIterator.remove();
                existingResourceEntity.add(resourceEntity);
                ResourceEntity resource = resourceDao.findById(resourceEntity.getId());
                resourceEntity.setBrand(resource.getBrand());
                resourceEntity.setName(resource.getSupplier());
                resourceEntity.setDescription(resource.getDescription());
                resourceEntity.setContact(resource.getContact());
                resourceEntity.setIdentifier(resource.getIdentifier());
                resourceEntity.setSupplier(resource.getSupplier());
                resourceEntity.setExpirationDate(resource.getExpirationDate());
            }
        }
        System.out.println();

        // Persist the project entity
        System.out.println("before");
        projectDao.persist(projectEntity);
        System.out.println("after");
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

        Set<SkillEntity> completeSkillSet = project.getSkills();
        completeSkillSet.addAll(existingSkillEntity);
        project.setSkills(completeSkillSet);

        Set<ComponentEntity> completeComponentSet = project.getComponents();
        completeComponentSet.addAll(existingComponentEntity);
        project.setComponents(completeComponentSet);

        Set<ResourceEntity> completeResourceSet = project.getResources();
        completeResourceSet.addAll(existingResourceEntity);
        project.setResources(completeResourceSet);

        projectDao.merge(project);



        for (UserProjectEntity userProjectEntity : project.getUserProjects()) {
            if (userProjectEntity.isAdmin()) {
                userProjectEntity.setUser(user);
                userProjectEntity.setProject(project);
                userProjectDao.merge(userProjectEntity);
            }
        }

        Set<TaskEntity> taskEntitiesFinal = new HashSet<>();
        for (TaskEntity task : project.getTasks()) {
            task.setProject(project);
            taskEntitiesFinal.add(task);
        }
        project.setTasks(taskEntitiesFinal);
        projectDao.merge(project);
        }

    @Transactional
    public Set<ProjectDto> getAllProjects(int page, int limit) {
        List<ProjectEntity> projects = projectDao.findAllProjects(page, limit);

        return projects.stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toSet());
    }


    public long getTotalProjectCount() {
        return projectDao.getTotalProjectCount();
    }
}

