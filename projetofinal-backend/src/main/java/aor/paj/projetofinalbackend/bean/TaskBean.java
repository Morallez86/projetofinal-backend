package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application-scoped bean responsible for managing tasks within projects.
 * @see TaskDao
 * @see UserDao
 * @see ProjectDao
 * @see ProjectHistoryDao
 *
 * @author João Morais
 * @author Ricardo Elias
 */
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

    @Inject
    TokenDao tokenDao;

    /**
     * Retrieves all tasks from the database.
     *
     * @return List of TaskDto objects representing all tasks.
     */
    @Transactional
    public List<TaskDto> getAllTasks() {
        List<TaskEntity> taskEntities = taskDao.findAll();
        return taskEntities.stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Edits a task based on the provided TaskDto.
     *
     * @param dto The TaskDto containing updated task information.
     * @return EditTaskResult object containing the edited TaskDto and its index.
     */
    @Transactional
    public EditTaskResult editTask (TaskDto dto, String token) {
        UserEntity userEdit = tokenDao.findUserByTokenValue(token);
        int compare = 0;
        TaskEntity task = TaskMapper.toEntity(dto);
        TaskEntity taskDataBase = taskDao.find(dto.getId());
        taskDataBase.setTitle(task.getTitle());
        if (taskDataBase.getStatus() != task.getStatus()) {
            compare = 1;
        }
        taskDataBase.setDescription(task.getDescription());
        taskDataBase.setPriority(task.getPriority());

        if (task.getStatus().equals(TaskStatus.DOING) && task.getStatus()!=taskDataBase.getStatus()) {
            taskDataBase.setStartingDate(LocalDateTime.now());
            taskDataBase.setEndingDate(null);
        } else if (task.getStatus().equals(TaskStatus.DONE) && task.getStatus()!=taskDataBase.getStatus()) {
            taskDataBase.setEndingDate(LocalDateTime.now());
        } else if (task.getStatus().equals(TaskStatus.TODO) && task.getStatus()!=taskDataBase.getStatus()){
            taskDataBase.setStartingDate(null);
            taskDataBase.setEndingDate(null);
        }

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
        log.setUser(userEdit);
        log.setTask(taskDataBase);
        projectHistoryDao.persist(log);



        List<TaskEntity> orderedTasks = taskDao.findTasksByProjectId(dto.getProjectId());

        int index = orderedTasks.indexOf(taskDataBase) ;


        return new EditTaskResult(TaskMapper.toDto(taskDataBase),index);
    }

    /**
     * Creates a new task based on the provided TaskDto.
     *
     * @param dto The TaskDto containing task information to be created.
     */
    public void createTask(TaskDto dto) {
        // Check for existing tasks with the same title in the project
        List<TaskEntity> existingTasks = projectDao.findTasksByTitleAndProjectId(dto.getProjectId(), dto.getTitle());
        if (!existingTasks.isEmpty()) {
            throw new IllegalArgumentException("A task with the same title already exists in the project.");
        }

        // Convert the TaskDto to a TaskEntity
        TaskEntity taskEntity = TaskMapper.toEntity(dto);

        // Find and set task dependencies
        List<TaskEntity> taskEntityList = new ArrayList<>();
        for (Long id : dto.getDependencies()) {
            taskEntityList.add(taskDao.find(id));
        }

        // Set task properties
        taskEntity.setStatus(TaskStatus.TODO);
        taskEntity.setProject(projectDao.findProjectById(dto.getProjectId()));
        taskEntity.setUser(userDao.findUserById(dto.getUserId()));
        taskEntity.setDependencies(taskEntityList);

        // Persist the new task
        taskDao.persist(taskEntity);

        // Create and persist a project history log
        ProjectHistoryEntity log = new ProjectHistoryEntity();
        log.setTitle(taskEntity.getTitle() + " was created");
        log.setTask(taskEntity);
        log.setTimestamp(LocalDateTime.now());
        log.setType(HistoryType.ADD);
        log.setUser(userDao.findUserById(dto.getUserId()));
        log.setProject(taskEntity.getProject());
        projectHistoryDao.persist(log);
    }

    /**
     * Retrieves the final task of a project with the title "Final Presentation".
     *
     * @param projectId The ID of the project.
     * @return The task entity object representing the final task.
     */
    @Transactional
    public TaskEntity getFinalTaskOfProject(Long projectId) {
        List<TaskEntity> finalTasks = projectDao.findTasksByTitleAndProjectId(projectId, "Final Presentation");
        if (!finalTasks.isEmpty()) {
            return finalTasks.get(0);  // Return the first task if found
        }
        return null;
    }
}
