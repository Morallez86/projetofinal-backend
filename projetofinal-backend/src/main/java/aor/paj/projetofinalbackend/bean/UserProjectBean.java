package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.UserProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserProjectBean {

    @EJB
    UserProjectDao userProjectDao;

    @EJB
    ProjectDao projectDao;

    public boolean isUserInProject(Long userId, Long projectId) {
        UserProjectEntity userProject = userProjectDao.findByUserAndProjectActive(userId, projectId);
        return userProject != null;
    }

    public boolean isProjectAtMaxUsers(Long projectId) {
        ProjectEntity project = projectDao.findProjectById(projectId);
        Long currentProjectCount = userProjectDao.countActiveUsersByProjectId(projectId);
        return currentProjectCount >= project.getMaxUsers();
    }

    public boolean isUserAdminAndActiveInProject(UserEntity userEntity, Long projectId) {
        List<UserEntity> activeAdmins = userProjectDao.findAdminsByProjectIdActive(projectId);
        return activeAdmins.contains(userEntity);
    }
}
