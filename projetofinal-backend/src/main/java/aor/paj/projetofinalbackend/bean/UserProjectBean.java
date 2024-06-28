package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.UserProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserProjectBean {

    @EJB
    UserProjectDao userProjectDao;

    @EJB
    ProjectDao projectDao;

    public boolean isUserInProject(Long userId, Long projectId) {
        UserProjectEntity userProject = userProjectDao.findByUserAndProject(userId, projectId);
        return userProject != null;
    }

    public boolean isProjectAtMaxUsers(Long projectId) {
        ProjectEntity project = projectDao.findProjectById(projectId);
        Long currentProjectCount = userProjectDao.countProjectsByUserId(projectId);
        return currentProjectCount >= project.getMaxUsers();
    }
}
