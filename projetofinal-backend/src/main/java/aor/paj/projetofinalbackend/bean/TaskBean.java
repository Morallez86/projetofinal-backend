package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.EditTaskResult;
import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.TaskMapper;
import aor.paj.projetofinalbackend.utils.TaskStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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

    @Transactional
    public List<TaskDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskDao.findAll();
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public EditTaskResult editTask (TaskDto dto) {
        TaskEntity task = TaskMapper.toEntity(dto);
        TaskEntity taskDataBase = taskDao.find(dto.getId());
        taskDataBase.setTitle(task.getTitle());
        taskDataBase.setDescription(task.getDescription());
        taskDataBase.setPriority(task.getPriority());
        taskDataBase.setStatus(task.getStatus());
        taskDataBase.setContributors(task.getContributors());
        UserEntity user = userDao.findUserByUsername(dto.getUserName());
        taskDataBase.setUser(user);

        taskDao.merge(taskDataBase);


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
    }
}
