package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.utils.EmailSender;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

/**
 * Application-scoped bean responsible for managing user-project relationship operations.
 * @see UserProjectDao
 * @see ProjectDao
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class UserProjectBean {

    @EJB
    UserProjectDao userProjectDao;

    @EJB
    ProjectDao projectDao;

    /**
     * Checks if a user is associated with a project.
     *
     * @param userId    The ID of the user.
     * @param projectId The ID of the project.
     * @return true if the user is associated with the project, false otherwise.
     */
    public boolean isUserInProject(Long userId, Long projectId) {
        UserProjectEntity userProject = userProjectDao.findByUserAndProjectActive(userId, projectId);
        return userProject != null;
    }

    /**
     * Checks if a project has reached its maximum number of users.
     *
     * @param projectId The ID of the project.
     * @return true if the project has reached its maximum number of users, false otherwise.
     */
    public boolean isProjectAtMaxUsers(Long projectId) {
        ProjectEntity project = projectDao.findProjectById(projectId);
        Long currentProjectCount = userProjectDao.countActiveUsersByProjectId(projectId);
        return currentProjectCount >= project.getMaxUsers();
    }

    /**
     * Checks if a user is an active admin in a project.
     *
     * @param userEntity The UserEntity representing the user.
     * @param projectId  The ID of the project.
     * @return true if the user is an active admin in the project, false otherwise.
     */
    public boolean isUserAdminAndActiveInProject(UserEntity userEntity, Long projectId) {
        List<UserEntity> activeAdmins = userProjectDao.findAdminsByProjectIdActive(projectId);
        return activeAdmins.contains(userEntity);
    }
}
