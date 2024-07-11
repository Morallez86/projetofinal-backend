package aor.paj.projetofinalbackend.bean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import aor.paj.projetofinalbackend.utils.TaskPriority;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import aor.paj.projetofinalbackend.bean.TaskBean;
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

public class TaskBeanTest {

    @Mock
    private TaskDao taskDaoMock;

    @Mock
    private UserDao userDaoMock;

    @Mock
    private ProjectDao projectDaoMock;

    @Mock
    private ProjectHistoryDao projectHistoryDaoMock;

    @InjectMocks
    private TaskBean taskBean;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTasks() {
        // Mocking behavior
        List<TaskEntity> taskEntities = new ArrayList<>();
        TaskEntity task = new TaskEntity();
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);
        task.setUser(new UserEntity());
        task.setProject(new ProjectEntity());
        taskEntities.add(task);
        when(taskDaoMock.findAll()).thenReturn(taskEntities);

        // Test
        List<TaskDto> tasks = taskBean.getAllTasks();
        assertEquals(1, tasks.size());
        verify(taskDaoMock, times(1)).findAll();
    }

    @Test
    public void testCreateTask() {
        // Mocking behavior
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("New Task");
        taskDto.setProjectId(1L);
        taskDto.setPriority(100);
        taskDto.setStatus(100);
        taskDto.setUserId(1L);
        taskDto.setDependencies(List.of(1L, 2L));

        TaskEntity taskEntity = new TaskEntity();
        ProjectEntity projectEntity = new ProjectEntity();
        UserEntity userEntity = new UserEntity();
        List<TaskEntity> dependencies = new ArrayList<>();
        dependencies.add(new TaskEntity());
        dependencies.add(new TaskEntity());

        when(projectDaoMock.findProjectById(taskDto.getProjectId())).thenReturn(projectEntity);
        when(userDaoMock.findUserById(taskDto.getUserId())).thenReturn(userEntity);
        when(taskDaoMock.find(anyLong())).thenReturn(new TaskEntity());

        doNothing().when(taskDaoMock).persist(any(TaskEntity.class));
        doNothing().when(projectHistoryDaoMock).persist(any(ProjectHistoryEntity.class));

        // Test
        taskBean.createTask(taskDto);
        verify(taskDaoMock, times(1)).persist(any(TaskEntity.class));
        verify(projectHistoryDaoMock, times(1)).persist(any(ProjectHistoryEntity.class));
    }
}
