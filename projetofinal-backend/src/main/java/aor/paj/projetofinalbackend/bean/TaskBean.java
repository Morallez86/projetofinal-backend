package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.TaskDto;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.mapper.TaskMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskBean {

    @Inject
    TaskDao taskDao;

    @Inject
    UserDao userDao;

    @Transactional
    public List<TaskDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskDao.findAll();
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public void editTask (TaskDto dto) {
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
    }
}
