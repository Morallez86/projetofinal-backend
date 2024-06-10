package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ProjectHistoryMapper;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.Set;

@Stateless
public class ProjectHistoryBean {

    @EJB
    ProjectHistoryDao projectHistoryDao;

    @EJB
    ProjectDao projectDao;

    @EJB
    ServiceBean serviceBean;

    @EJB
    UserDao userDao;

    public void addLog (ProjectHistoryDto projectHistoryDto, long projectId, String token) {
        ProjectHistoryEntity projectHistoryEntity = ProjectHistoryMapper.toEntity(projectHistoryDto);
        System.out.println("*** " + projectHistoryEntity.getType());
        Long idUser = serviceBean.getUserIdFromToken(token);
        UserEntity userEntity = userDao.findUserById(idUser);
        ProjectEntity project = projectDao.findProjectById(projectId);
        projectHistoryEntity.setProject(project);
        projectHistoryEntity.setUser(userEntity);
        System.out.println("before");
        projectHistoryDao.persist(projectHistoryEntity);
        System.out.println("after");

        Set<ProjectHistoryEntity> projectHistoryEntitySet = project.getHistoryRecords();
        projectHistoryEntitySet.add(projectHistoryEntity);
        project.setHistoryRecords(projectHistoryEntitySet);
        projectDao.merge(project);
    }
}
