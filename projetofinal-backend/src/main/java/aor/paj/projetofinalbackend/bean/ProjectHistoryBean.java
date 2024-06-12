package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.ChatMessageMapper;
import aor.paj.projetofinalbackend.mapper.ProjectHistoryMapper;
import aor.paj.projetofinalbackend.utils.HistoryType;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @EJB
    TaskDao taskDao;

    public List<ProjectHistoryDto> addLog (ProjectHistoryDto projectHistoryDto, long projectId, String token) {
        System.out.println("in");
        ProjectHistoryEntity projectHistoryEntity = ProjectHistoryMapper.toEntity(projectHistoryDto);
        System.out.println("*** " + projectHistoryEntity.getType());
        Long idUser = serviceBean.getUserIdFromToken(token);
        UserEntity userEntity = userDao.findUserById(idUser);
        ProjectEntity project = projectDao.findProjectById(projectId);
        TaskEntity task = taskDao.find(projectHistoryDto.getTaskId());
        projectHistoryEntity.setProject(project);
        projectHistoryEntity.setUser(userEntity);
        projectHistoryEntity.setType(HistoryType.NORMAL);
        System.out.println("--- " + projectHistoryEntity.getType());
        projectHistoryEntity.setTimestamp(LocalDateTime.now());
        if (project.getTasks().contains(task) && task != null) {
        projectHistoryEntity.setTask(task);
        }
        else if (!project.getTasks().contains(task) && task != null){
            /* quando retornarmos o dto isto era retornar null*/
            System.out.println("task doesn't belong to this project");
            return null;
        }
        System.out.println("before");
        projectHistoryDao.persist(projectHistoryEntity);
        System.out.println("after");

        Set<ProjectHistoryEntity> projectHistoryEntitySet = project.getHistoryRecords();
        projectHistoryEntitySet.add(projectHistoryEntity);
        project.setHistoryRecords(projectHistoryEntitySet);
        projectDao.merge(project);

       return projectHistoryEntitySet.stream().map(ProjectHistoryMapper::toDto).collect(Collectors.toList());
    }


}
