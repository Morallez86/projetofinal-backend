package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.EditTaskResult;
import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.TaskMapper;
import aor.paj.projetofinalbackend.utils.HistoryType;
import aor.paj.projetofinalbackend.utils.TaskStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskBean {

    @Inject
    TaskDao taskDao;

    @Inject
    UserDao userDao;

    @Inject
    ProjectDao projectDao;

    @Inject
    ProjectHistoryDao projectHistoryDao;

    @Transactional
    public List<TaskDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskDao.findAll();
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EditTaskResult editTask (TaskDto dto) {
        int compare = 0;
        TaskEntity task = TaskMapper.toEntity(dto);
        TaskEntity taskDataBase = taskDao.find(dto.getId());
        taskDataBase.setTitle(task.getTitle());
        if (taskDataBase.getStatus() != task.getStatus()) {
            compare = 1;
        }
        taskDataBase.setDescription(task.getDescription());
        taskDataBase.setPriority(task.getPriority());
        taskDataBase.setStatus(task.getStatus());
        taskDataBase.setContributors(task.getContributors());
        UserEntity user = userDao.findUserByUsername(dto.getUserName());
        taskDataBase.setUser(user);

        taskDao.merge(taskDataBase);

        ProjectHistoryEntity log = new ProjectHistoryEntity();
        if (compare!=1) {
            log.setTitle(taskDataBase.getTitle() + " was edited");
        } else {
                log.setTitle(taskDataBase.getTitle() + ": " + taskDataBase.getStatus());
            }
        log.setType(HistoryType.TASKS);
        log.setTimestamp(LocalDateTime.now());
        log.setProject(taskDataBase.getProject());
        log.setUser(user);
        projectHistoryDao.persist(log);



        List<TaskEntity> orderedTasks = taskDao.findTasksByProjectId(dto.getProjectId());

        int index = orderedTasks.indexOf(taskDataBase) ;


        return new EditTaskResult(TaskMapper.toDto(taskDataBase),index);
    }

    public void createTask (TaskDto dto) {
        TaskEntity taskEntity = TaskMapper.toEntity(dto);
        List<TaskEntity> taskEntityList=new ArrayList<>();
        for (Long id : dto.getDependencies()) {
            taskEntityList.add(taskDao.find(id));
        }
        taskEntity.setStatus(TaskStatus.TODO);
        taskEntity.setProject(projectDao.findProjectById(dto.getProjectId()));
        taskEntity.setUser(userDao.findUserById(dto.getUserId()));
        taskEntity.setDependencies(taskEntityList);
        taskDao.persist(taskEntity);

        ProjectHistoryEntity log = new ProjectHistoryEntity();
        log.setTitle( taskEntity.getTitle() + " was created");
        log.setTask(taskEntity);
        log.setTimestamp(LocalDateTime.now());
        log.setType(HistoryType.ADD);
        log.setUser(userDao.findUserById(dto.getUserId()));
        log.setProject(taskEntity.getProject());
        projectHistoryDao.persist(log);

    }
}
