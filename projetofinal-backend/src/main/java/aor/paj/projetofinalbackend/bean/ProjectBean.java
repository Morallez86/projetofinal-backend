package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.mapper.TaskMapper;
import aor.paj.projetofinalbackend.mapper.UserProjectMapper;
import aor.paj.projetofinalbackend.utils.*;
import aor.paj.projetofinalbackend.websocket.ApplicationSocket;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectBean {

    @Inject
    private ProjectHistoryBean projectHistoryBean;

    @Inject
    private TokenBean tokenBean;

    @Inject
    private NotificationBean notificationBean;

    @Inject
    private UserProjectBean userProjectBean;

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

    @EJB
    private NotificationDao notificationDao;

    @EJB
    private UserNotificationDao userNotificationDao;



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
        Set<ComponentEntity> updatedComponents = new HashSet<>();
        Iterator<ComponentEntity> componentIterator = projectEntity.getComponents().iterator();
        while (componentIterator.hasNext()) {
            ComponentEntity componentEntity = componentIterator.next();
            if (componentEntity.getName() != null) {
                ComponentEntity existingComponent = componentDao.findFirstAvailableComponentByName(componentEntity.getName(), projectDto.getWorkplace().getId());
                if (existingComponent != null) {
                    // Use the existing component from the database
                    existingComponent.setAvailability(false);
                    updatedComponents.add(existingComponent);
                    componentIterator.remove();

                } else {
                    // Component not found in the database, send notification
                    sendNewComponentResourceNotification(user, componentEntity.getName());
                    // Remove componentEntity to avoid merge issues
                    componentIterator.remove();
                }
            }
        }

        System.out.println(updatedComponents);

        // Add remaining components to updatedComponents set
        for (ComponentEntity componentEntity : projectEntity.getComponents()) {
            updatedComponents.add(componentEntity);
        }

        projectEntity.setComponents(updatedComponents);
        System.out.println(updatedComponents);

        // Handle resources
        System.out.println("11111111111111");
        Set<ResourceEntity> updatedResources = new HashSet<>();
        Iterator<ResourceEntity> resourceIterator = projectEntity.getResources().iterator();
        while (resourceIterator.hasNext()) {
            ResourceEntity resourceEntity = resourceIterator.next();
            System.out.println(resourceEntity.getName());
            if (resourceEntity.getName() != null) {
                ResourceEntity existingResource = resourceDao.findById(resourceEntity.getId());
                System.out.println(existingResource);
                if (existingResource != null) {
                    System.out.println("33333333333333");
                    // Use the existing resource from the database
                    updatedResources.add(existingResource);
                    resourceIterator.remove();

                } else {
                    System.out.println("222222222222222");
                    // Resource not found in the database, send notification
                    sendNewComponentResourceNotification(user, resourceEntity.getName());
                    // Remove resourceEntity to avoid merge issues
                    resourceIterator.remove();
                }
            }
        }

        // Add remaining resources to updatedResources set
        for (ResourceEntity resourceEntity : projectEntity.getResources()) {
            updatedResources.add(resourceEntity);
        }

        projectEntity.setResources(updatedResources);

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

        System.out.println(projectEntity);
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
        completeComponentSet.addAll(updatedComponents);
        projectEntity.setComponents(completeComponentSet);

        Set<ResourceEntity> completeResourceSet = projectEntity.getResources();
        completeResourceSet.addAll(updatedResources);
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
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional
    public Set<ProjectDto> getAllProjectsNoQueries() {
        try {
            List<ProjectEntity> projects = projectDao.getAllProjectsNoQueries();
            System.out.println(projects.size() + ": size");
            return projects.stream()
                    .map(ProjectMapper::toDto)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            throw e; // Ensure the exception is thrown so the transaction is marked for rollback
        }
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
        projectEntity.setMaxUsers(projectDto.getMaxUsers());

        // Handle workplace
        if (projectDto.getWorkplace() != null) {
            WorkplaceEntity workplaceEntity = workplaceDao.findWorkplaceByName(projectDto.getWorkplace().getName());
            if (workplaceEntity != null) {
                projectEntity.setWorkplace(workplaceEntity);
            }
        }
        projectDao.merge(projectEntity);

        // Check if project status is READY
        if (ProjectStatus.fromValue(projectDto.getStatus()) == ProjectStatus.READY) {
            notificationBean.sendProjectAproval(projectEntity);
        }
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

    @Transactional
    public Set<ProjectDto> searchProjects(String searchTerm, String skillString, String interestString, ProjectStatus status) {
        List<ProjectEntity> projects = projectDao.searchProjects(searchTerm, skillString, interestString, status);
        System.out.println("Project names:");
        for (ProjectEntity project : projects) {
            System.out.println(project.getTitle());
        }

        projects.sort(Comparator.comparing(ProjectEntity::getCreationDate).reversed());

        System.out.println("Project names after sort:");
        for (ProjectEntity project : projects) {
            System.out.println(project.getTitle());
        }

        return projects.stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    private void sendNewComponentResourceNotification(UserEntity user, String componentName) {
        NotificationEntity notification = new NotificationEntity();
        notification.setTimestamp(LocalDateTime.now());
        notification.setSender(user);
        notification.setType(NotificationType.PROJECT);
        notification.setDescription("Component " + componentName + " is required for your new project.");

        notificationDao.persist(notification);

        UserNotificationEntity userNotification = new UserNotificationEntity();
        userNotification.setUser(user);
        System.out.println(user.getUsername());
        userNotification.setNotification(notification);
        userNotification.setSeen(false);

        userNotificationDao.persist(userNotification);

        List<TokenEntity> activeTokens = user.getTokens().stream()
                .filter(TokenEntity::isActiveToken)
                .collect(Collectors.toList());

        activeTokens.forEach(token -> ApplicationSocket.sendNotification(token.getTokenValue(), "notification"));
    }

    @Transactional
    public void changeUserStatus(Long projectId, Long userId, Boolean newStatus, String token) throws Exception {
        UserProjectEntity userProject = userProjectDao.findByUserAndProjectActive(userId, projectId);
        UserEntity userSending = tokenBean.findUserByToken(token);

        if (userProject == null) {
            throw new Exception("User not found in the specified project");
        }

        userProject.setIsAdmin(newStatus);
        userProjectDao.merge(userProject);

        projectHistoryBean.logUserStatusChange(userProject, newStatus, userSending);
    }

    @Transactional
    public void changeUserToInactive(Long projectId, Long userId, String token) throws Exception {
        UserProjectEntity userProject = userProjectDao.findByUserAndProjectActive(userId, projectId);
        UserEntity userSending = tokenBean.findUserByToken(token);

        if (userProject == null) {
            throw new Exception("User not found in the specified project");
        }

        userProject.setActive(false);
        userProjectDao.merge(userProject);

        projectHistoryBean.logUserInactiveChange(userProject, userSending);
    }

    @Transactional
    public void addSkillToProject(Long projectId, SkillDto skillDto, String token) {
        // Find the project by ID
        ProjectEntity projectEntity = projectDao.findProjectById(projectId);
        if (projectEntity == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Find the user by token
        UserEntity userEntity = tokenBean.findUserByToken(token);
        if (userEntity == null) {
            throw new SecurityException("User not found or token invalid");
        }
        // This could involve checking if the user is an admin or owner of the project
        if (!userProjectBean.isUserAdminAndActiveInProject(userEntity, projectEntity.getId())) {
            throw new SecurityException("User does not have permission to add skills to this project");
        }

        // Find the skill by name
        SkillEntity skillEntity = skillDao.findByName(skillDto.getName());

        // If skill does not exist, create a new one
        if (skillEntity == null) {
            skillEntity = new SkillEntity();
            skillEntity.setName(skillDto.getName());
            skillEntity.setType(SkillType.fromValue(skillDto.getType()));
            skillEntity.setCreator(userEntity);

            // Persist the new skill
            skillDao.persist(skillEntity);
        }

        // Add the skill to the project
        projectEntity.getSkills().add(skillEntity);

        // Merge the project entity to save changes
        projectDao.merge(projectEntity);
    }

    @Transactional
    public void addInterestToProject(Long projectId, InterestDto interestDto, String token) {
        // Find the project by ID
        ProjectEntity projectEntity = projectDao.findProjectById(projectId);
        if (projectEntity == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Find the user by token
        UserEntity userEntity = tokenBean.findUserByToken(token);
        if (userEntity == null) {
            throw new SecurityException("User not found or token invalid");
        }
        // This could involve checking if the user is an admin or owner of the project
        if (!userProjectBean.isUserAdminAndActiveInProject(userEntity, projectEntity.getId())) {
            throw new SecurityException("User does not have permission to add interests to this project");
        }

        // Find the interest with name
        InterestEntity interestEntity = interestDao.findByName(interestDto.getName());

        // If interest does not exist, create a new one
        if (interestEntity == null) {
            interestEntity = new InterestEntity();
            interestEntity.setName(interestDto.getName());
            interestEntity.setCreator(userEntity);

            // Persist the new interest
            interestDao.persist(interestEntity);
        }

        // Add the interest to the project
        projectEntity.getInterests().add(interestEntity);

        // Merge the project entity to save changes
        projectDao.merge(projectEntity);
    }

    @Transactional
    public void addComponentToProject(Long projectId, ComponentDto componentDto, String token) {
        // Find the project by ID
        ProjectEntity projectEntity = projectDao.findProjectById(projectId);
        if (projectEntity == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Find the user by token
        UserEntity userEntity = tokenBean.findUserByToken(token);
        if (userEntity == null) {
            throw new SecurityException("User not found or token invalid");
        }

        // Check if the user is an admin or active in the project
        if (!userProjectBean.isUserAdminAndActiveInProject(userEntity, projectEntity.getId())) {
            throw new SecurityException("User does not have permission to add components to this project");
        }

        // Find the component by name
        ComponentEntity componentEntity = componentDao.findByName(componentDto.getName());
        if (componentEntity == null) {
            throw new IllegalArgumentException("Component not found");
        }

        componentEntity.setProject(projectEntity);
        componentEntity.setAvailability(false);
        componentDao.merge(componentEntity);
    }

    @Transactional
    public void removeSKillsProject(List<Long> skillsToRemove, Long projectId) {
        // Get project by ID
        ProjectDto projectDto = getProjectById(projectId);
        if (projectDto == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Get the corresponding ProjectEntity
        ProjectEntity projectEntity = projectDao.findProjectById(projectId);
        if (projectEntity == null) {
            throw new IllegalArgumentException("Project entity not found");
        }

        List<SkillEntity> skillEntitiesToRemove = skillDao.findAllById(skillsToRemove);

        // Remove these skills from the project
        projectEntity.getSkills().removeAll(skillEntitiesToRemove);

        // Save the updated project entity
        projectDao.merge(projectEntity);
    }

    @Transactional
    public void removeInterestsProject(List<Long> interestsToRemove, Long projectId) {
        // Get project by ID
        ProjectDto projectDto = getProjectById(projectId);
        if (projectDto == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Get the corresponding ProjectEntity
        ProjectEntity projectEntity = projectDao.findProjectById(projectId);
        if (projectEntity == null) {
            throw new IllegalArgumentException("Project entity not found");
        }

        List<InterestEntity> interestEntitiesToRemove = interestDao.findAllById(interestsToRemove);

        // Remove these interests from the project
        projectEntity.getInterests().removeAll(interestEntitiesToRemove);

        // Save the updated project entity
        projectDao.merge(projectEntity);
    }

}

