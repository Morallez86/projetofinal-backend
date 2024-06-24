package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.ProjectDto;
import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.dto.TaskEndDateDto;
import aor.paj.projetofinalbackend.dto.UserProjectDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.mapper.TaskMapper;
import aor.paj.projetofinalbackend.mapper.UserProjectMapper;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import aor.paj.projetofinalbackend.utils.TaskPriority;
import aor.paj.projetofinalbackend.utils.TaskStatus;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;
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

    @EJB
    private TaskDao taskDao;

    @EJB
    private WorkplaceDao workplaceDao;



    @Transactional
    public void addProject(ProjectDto projectDto, String token) {
        // Extract user ID from the token
        Long userId = serviceBean.getUserIdFromToken(token);
        System.out.println(projectDto.getWorkplace().getName());

        // Find the user by ID
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid creator ID");
        }

        // Convert DTO to entity
        ProjectEntity projectEntity = ProjectMapper.toEntity(projectDto);

        // Set the owner of the project
        projectEntity.setOwner(user);
        projectEntity.setCreationDate(LocalDateTime.now());
        projectEntity.setStatus(ProjectStatus.PLANNING);

        // Handle interests
        Set<InterestEntity> existingInterestEntity = new HashSet<>();
        for (InterestEntity interestEntity : projectEntity.getInterests()) {
            if (interestEntity.getCreator() == null) {
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

        // Handle skills
        Set<SkillEntity> existingSkillEntity = new HashSet<>();
        for (SkillEntity skillEntity : projectEntity.getSkills()) {
            if (skillEntity.getCreator() == null) {
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

        // Handle components
        Set<ComponentEntity> existingComponentEntity = new HashSet<>();
        Iterator<ComponentEntity> componentIterator = projectEntity.getComponents().iterator();
        while (componentIterator.hasNext()) {
            ComponentEntity componentEntity = componentIterator.next();
            if (componentEntity.getId() != null) {
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

        // Handle resources
        Set<ResourceEntity> existingResourceEntity = new HashSet<>();
        Iterator<ResourceEntity> resourceIterator = projectEntity.getResources().iterator();
        while (resourceIterator.hasNext()) {
            ResourceEntity resourceEntity = resourceIterator.next();
            if (resourceEntity.getId() != null) {
                resourceIterator.remove();
                existingResourceEntity.add(resourceEntity);
                ResourceEntity resource = resourceDao.findById(resourceEntity.getId());
                resourceEntity.setBrand(resource.getBrand());
                resourceEntity.setName(resource.getName());
                resourceEntity.setDescription(resource.getDescription());
                resourceEntity.setContact(resource.getContact());
                resourceEntity.setIdentifier(resource.getIdentifier());
                resourceEntity.setSupplier(resource.getSupplier());
                resourceEntity.setExpirationDate(resource.getExpirationDate());
            }
        }

        // Handle workplaces
        Set<WorkplaceEntity> existingWorkplaceEntity = new HashSet<>();
        if (projectDto.getWorkplace() != null) {
            WorkplaceEntity workplaceEntity = workplaceDao.findWorkplaceByName(projectDto.getWorkplace().getName());
            if (workplaceEntity != null) {
                existingWorkplaceEntity.add(workplaceEntity);
            }
        }

        // Finalize workplace associations
        if (existingWorkplaceEntity != null && !existingWorkplaceEntity.isEmpty()) {
            projectEntity.setWorkplace(existingWorkplaceEntity.iterator().next());
        }


        // Persist the project entity
        projectDao.persist(projectEntity);


        // Associate user projects
        Set<UserProjectEntity> userProjectEntities = new HashSet<>();
        for (UserProjectDto userProjectDto : projectDto.getUserProjectDtos()) {
            UserEntity projectUser = userDao.findUserById(userProjectDto.getUserId());
            if (projectUser != null) {
                UserProjectEntity userProjectEntity = new UserProjectEntity();
                userProjectEntity.setUser(projectUser);
                userProjectEntity.setProject(projectEntity);
                userProjectEntity.setIsAdmin(false);
                userProjectEntities.add(userProjectEntity);
            }
        }

        // Add the owner as a UserProjectEntity
        UserProjectEntity ownerProjectEntity = new UserProjectEntity();
        ownerProjectEntity.setUser(user);
        ownerProjectEntity.setProject(projectEntity);
        ownerProjectEntity.setIsAdmin(true);
        userProjectEntities.add(ownerProjectEntity);

        // Persist user projects
        for (UserProjectEntity userProjectEntity : userProjectEntities) {
            userProjectDao.merge(userProjectEntity);
        }

        projectEntity.setUserProjects(userProjectEntities);

        // Finalize project associations
        Set<InterestEntity> completeInterestSet = projectEntity.getInterests();
        completeInterestSet.addAll(existingInterestEntity);
        projectEntity.setInterests(completeInterestSet);

        Set<SkillEntity> completeSkillSet = projectEntity.getSkills();
        completeSkillSet.addAll(existingSkillEntity);
        projectEntity.setSkills(completeSkillSet);

        Set<ComponentEntity> completeComponentSet = projectEntity.getComponents();
        completeComponentSet.addAll(existingComponentEntity);
        projectEntity.setComponents(completeComponentSet);

        Set<ResourceEntity> completeResourceSet = projectEntity.getResources();
        completeResourceSet.addAll(existingResourceEntity);
        projectEntity.setResources(completeResourceSet);

        // Handle tasks
        List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity uniqueTask = new TaskEntity();
        uniqueTask.setTitle("Final Presentation");
        uniqueTask.setDescription("Last task");
        uniqueTask.setPlannedStartingDate(projectEntity.getPlannedEndDate());
        uniqueTask.setPlannedEndingDate(projectEntity.getPlannedEndDate());
        uniqueTask.setUser(user);
        uniqueTask.setStatus(TaskStatus.TODO);
        uniqueTask.setPriority(TaskPriority.LOW);
        uniqueTask.setProject(projectEntity);
        taskEntities.add(uniqueTask);
        projectEntity.setTasks(taskEntities);

        // Persist additional entities
        projectDao.merge(projectEntity);

        for (ComponentEntity componentEntity : projectEntity.getComponents()) {
            componentEntity.setProject(projectEntity);
            componentDao.merge(componentEntity);
        }

        for (ResourceEntity resourceEntity : projectEntity.getResources()) {
            Set<ProjectEntity> projectEntities = resourceEntity.getProjects();
            projectEntities.add(projectEntity);
            resourceEntity.setProjects(projectEntities);
            resourceDao.merge(resourceEntity);
        }

        List<TaskEntity> taskEntitiesFinal = new ArrayList<>();
        for (TaskEntity task : projectEntity.getTasks()) {
            task.setProject(projectEntity);
            taskEntitiesFinal.add(task);
        }
        projectEntity.setTasks(taskEntitiesFinal);
        projectDao.merge(projectEntity);
    }

    @Transactional
    public Set<ProjectDto> getAllProjects(int page, int limit) {
        List<ProjectEntity> projects = projectDao.findAllProjects(page, limit);

        return projects.stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<ProjectDto> getAllProjectsNoQueries() {
        List<ProjectEntity> projects = projectDao.getAllProjectsNoQueries();
        System.out.println(projects.size() + ": size");

        return projects.stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public ProjectDto getProjectById(Long projectId) {
        ProjectEntity project = projectDao.findProjectById(projectId);
        if (project == null) {
            return null;
        }
        List<TaskEntity> orderedTasks = taskDao.findTasksByProjectId(projectId);
        for (TaskEntity task : orderedTasks) {
            System.out.println(task.getStatus());
        }
        project.setTasks(orderedTasks);
        projectDao.merge(project);
        for (TaskEntity task2 : project.getTasks()) {
            System.out.println("2 " + task2.getStatus());
        }
        return ProjectMapper.toDto(project);
    }


    public long getTotalProjectCount() {
        return projectDao.getTotalProjectCount();
    }

    @Transactional
    public List<TaskDto> getTasksByProjectId(Long projectId) {
        List<TaskEntity> taskEntities = taskDao.findTasksByProjectId(projectId);
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProject(Long projectId, ProjectDto projectDto, String token) {
        // Extract user ID from the token
        Long userId = serviceBean.getUserIdFromToken(token);
        UserEntity user = userDao.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        // Find the existing project
        ProjectEntity projectEntity = projectDao.findProjectById(projectId);
        if (projectEntity == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Update project details
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setStatus(ProjectStatus.fromValue(projectDto.getStatus()));
        projectEntity.setDescription(projectDto.getDescription());
        projectEntity.setMotivation(projectDto.getMotivation());
        projectEntity.setCreationDate(projectDto.getCreationDate());
        projectEntity.setPlannedEndDate(projectDto.getPlannedEndDate());

        // Handle workplace
        if (projectDto.getWorkplace() != null) {
            WorkplaceEntity workplaceEntity = workplaceDao.findWorkplaceByName(projectDto.getWorkplace().getName());
            if (workplaceEntity != null) {
                projectEntity.setWorkplace(workplaceEntity);
            }
        }
        /*
        // Update UserProject associations
        Set<UserProjectEntity> userProjectEntities = new HashSet<>();
        for (UserProjectDto userProjectDto : projectDto.getUserProjectDtos()) {
            UserEntity projectUser = userDao.findUserById(userProjectDto.getUserId());
            if (projectUser != null) {
                UserProjectEntity userProjectEntity = userProjectDao.findByUserAndProject(projectUser.getId(), projectEntity.getId());
                if (userProjectEntity == null) {
                    userProjectEntity = new UserProjectEntity();
                    userProjectEntity.setUser(projectUser);
                    userProjectEntity.setProject(projectEntity);
                    userProjectEntity.setIsAdmin(false);
                }
                userProjectEntities.add(userProjectEntity);
            }
        }
        projectEntity.setUserProjects(userProjectEntities);
        */

        // Persist all changes
        projectDao.merge(projectEntity);
    }

    public List<UserProjectDto> getUsersByProject (Long projectId) {
        List<UserProjectEntity> userProjectEntities = projectDao.findUserProjectsByProjectId(projectId);
        return userProjectEntities.stream()
                .map(UserProjectMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDto> getPossibleDependentTasks (Long projectId, TaskEndDateDto plannedStartingDate) {
        List <TaskEntity> taskEntities = projectDao.findTasksByProjectIdAndEndingDate(projectId, plannedStartingDate.getPlannedStartingDate());
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public double getAverageUsersPerProject() {
        long totalProjects = projectDao.getTotalProjectCount();
        long totalUsers = projectDao.getTotalUserCount();
        if (totalProjects == 0) {
            return 0;
        }
        return (double) totalUsers / totalProjects;
    }

    public double getPercentage(long part, long total) {
        if (total == 0) {
            return 0;
        }
        return (double) part / total * 100;
    }

}

