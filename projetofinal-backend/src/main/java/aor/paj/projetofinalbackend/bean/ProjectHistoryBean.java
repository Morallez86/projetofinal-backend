package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProjectHistoryMapper;
import aor.paj.projetofinalbackend.utils.HistoryType;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Stateless session bean responsible for managing project history logs.
 * @see ProjectHistoryDao
 * @see ProjectDao
 * @see UserDao
 * @see TaskDao
 * @see ServiceBean
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ProjectHistoryBean {

    @EJB
    ProjectHistoryDao projectHistoryDao;

    @EJB
    ProjectDao projectDao;

    @EJB
    UserDao userDao;

    @EJB
    TaskDao taskDao;

    @Inject
    ServiceBean serviceBean;

    /**
     * Adds a project history log entry for a specified project.
     *
     * @param projectHistoryDto The DTO containing the project history details.
     * @param projectId The ID of the project to log history for.
     * @param token The authentication token for identifying the user.
     * @return A list of ProjectHistoryDto objects representing all history records for the project.
     */
    public List<ProjectHistoryDto> addLog (ProjectHistoryDto projectHistoryDto, long projectId, String token) {
        ProjectHistoryEntity projectHistoryEntity = ProjectHistoryMapper.toEntity(projectHistoryDto);
        Long idUser = serviceBean.getUserIdFromToken(token);
        UserEntity userEntity = userDao.findUserById(idUser);
        ProjectEntity project = projectDao.findProjectById(projectId);
        TaskEntity task = taskDao.find(projectHistoryDto.getTaskId());
        projectHistoryEntity.setProject(project);
        projectHistoryEntity.setUser(userEntity);
        projectHistoryEntity.setType(HistoryType.NORMAL);
        projectHistoryEntity.setTimestamp(LocalDateTime.now());
        if (project.getTasks().contains(task) && task != null) {
        projectHistoryEntity.setTask(task);
        }
        else if (!project.getTasks().contains(task) && task != null){
            //task doesn't belong to this project
            return null;
        }
        projectHistoryDao.persist(projectHistoryEntity);

        Set<ProjectHistoryEntity> projectHistoryEntitySet = project.getHistoryRecords();
        projectHistoryEntitySet.add(projectHistoryEntity);
        project.setHistoryRecords(projectHistoryEntitySet);
        projectDao.merge(project);

       return projectHistoryEntitySet.stream().map(ProjectHistoryMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Logs a change in user status for a project.
     *
     * @param userProjectEntity The user project entity representing the user's role in the project.
     * @param newStatus The new status of the user (true if admin, false otherwise).
     * @param userSending The user entity representing the user making the status change.
     */
    public void logUserStatusChange(UserProjectEntity userProjectEntity, Boolean newStatus, UserEntity userSending) {
        String titleLog;
        if(newStatus){
            titleLog = userProjectEntity.getUser().getUsername() + " is now ADMIN";
        }else{
            titleLog = userProjectEntity.getUser().getUsername() + " is no longer ADMIN";
        }

        // Create a new ProjectHistoryEntity instance
        ProjectHistoryEntity projectHistoryEntity = new ProjectHistoryEntity();
        projectHistoryEntity.setTitle(titleLog);
        projectHistoryEntity.setProject(userProjectEntity.getProject());
        projectHistoryEntity.setUser(userSending);
        projectHistoryEntity.setType(HistoryType.PROJECTSTATE);
        projectHistoryEntity.setTimestamp(LocalDateTime.now());

        // Persist the history entity
        projectHistoryDao.persist(projectHistoryEntity);

        // Update project's history records
        ProjectEntity project = projectHistoryEntity.getProject();
        Set<ProjectHistoryEntity> projectHistoryEntitySet = project.getHistoryRecords();
        projectHistoryEntitySet.add(projectHistoryEntity);
        project.setHistoryRecords(projectHistoryEntitySet);
        projectDao.merge(project);
    }

    /**
     * Logs a change indicating that a user has become inactive (removed or exited) from a project.
     *
     * @param userProjectEntity The user project entity representing the user's role in the project.
     * @param userSending The user entity representing the user making the change.
     */
    public void logUserInactiveChange(UserProjectEntity userProjectEntity, UserEntity userSending) {
        String titleLog = userProjectEntity.getUser().getUsername() + " was removed/exited";;

        // Create a new ProjectHistoryEntity instance
        ProjectHistoryEntity projectHistoryEntity = new ProjectHistoryEntity();
        projectHistoryEntity.setTitle(titleLog);
        projectHistoryEntity.setProject(userProjectEntity.getProject());
        projectHistoryEntity.setUser(userSending);
        projectHistoryEntity.setType(HistoryType.REMOVE);
        projectHistoryEntity.setTimestamp(LocalDateTime.now());

        // Persist the history entity
        projectHistoryDao.persist(projectHistoryEntity);

        // Update project's history records
        ProjectEntity project = projectHistoryEntity.getProject();
        Set<ProjectHistoryEntity> projectHistoryEntitySet = project.getHistoryRecords();
        projectHistoryEntitySet.add(projectHistoryEntity);
        project.setHistoryRecords(projectHistoryEntitySet);
        projectDao.merge(project);
    }
}
