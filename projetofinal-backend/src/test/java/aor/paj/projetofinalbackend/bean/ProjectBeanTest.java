package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.*;
import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.mapper.ProjectMapper;
import aor.paj.projetofinalbackend.mapper.TaskMapper;
import aor.paj.projetofinalbackend.mapper.UserProjectMapper;
import aor.paj.projetofinalbackend.mapper.WorkplaceMapper;
import aor.paj.projetofinalbackend.service.ProjectService;
import aor.paj.projetofinalbackend.utils.*;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ProjectBeanTest {

    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectHistoryBean projectHistoryBean;

    @Mock
    private TokenBean tokenBean;

    @Mock
    private TaskBean taskBean;

    @Mock
    private NotificationBean notificationBean;

    @Mock
    private UserProjectBean userProjectBean;

    @Mock
    private UserDao userDao;

    @Mock
    private ServiceBean serviceBean;

    @Mock
    private ProjectDao projectDao;

    @Mock
    private ComponentDao componentDao;

    @Mock
    private ResourceDao resourceDao;

    @Mock
    private InterestDao interestDao;

    @Mock
    private UserProjectDao userProjectDao;

    @Mock
    private SkillDao skillDao;

    @Mock
    private TaskDao taskDao;

    @Mock
    private WorkplaceDao workplaceDao;

    @Mock
    private NotificationDao notificationDao;

    @Mock
    private UserNotificationDao userNotificationDao;

    @InjectMocks
    private ProjectBean projectBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProject() {
        // Mocking dependencies
        ProjectDto projectDto = new ProjectDto();
        projectDto.setTitle("Test Project");
        projectDto.setDescription("Test Description");
        projectDto.setPlannedEndDate(LocalDateTime.now().plusDays(30));
        WorkplaceEntity workplaceEntity = new WorkplaceEntity();
        workplaceEntity.setName("Coimbra");
        WorkplaceDto wpDto = WorkplaceMapper.toDto(workplaceEntity);
        projectDto.setWorkplace(wpDto);
        List<UserProjectDto> userProjectDtoList = new ArrayList<>();
        UserProjectDto userProjectDto = new UserProjectDto();
        projectDto.setUserProjectDtos(userProjectDtoList);

        String token = "mockedToken";
        when(serviceBean.getUserIdFromToken(token)).thenReturn(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userDao.findUserById(anyLong())).thenReturn(userEntity);

        when(componentDao.findFirstAvailableComponentByName(anyString(), anyLong())).thenReturn(null);
        when(resourceDao.findById(anyLong())).thenReturn(null);
        when(workplaceDao.findWorkplaceByName(anyString())).thenReturn(new WorkplaceEntity());

        // Test
        assertDoesNotThrow(() -> projectBean.addProject(projectDto, token));
    }

    @Test
    void testGetAllProjects() {
        // Mocking dependencies
        List<ProjectEntity> projects = new ArrayList<>();
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setTitle("Test Project 1");
        projectEntity.setStatus(ProjectStatus.READY);
        projects.add(projectEntity);
        UserEntity user = new UserEntity();
        projectEntity.setOwner(user);

        when(projectDao.findAllProjects(anyInt(), anyInt())).thenReturn(projects);

        // Test
        Set<ProjectDto> result = projectBean.getAllProjects(1, 10);
        assertEquals(1, result.size());
    }

    @Test
    void testGetProjectById() {
        // Mocking dependencies
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setTitle("Test Project");
        projectEntity.setStatus(ProjectStatus.IN_PROGRESS);
        UserEntity user = new UserEntity();
        projectEntity.setOwner(user);


        when(projectDao.findProjectById(anyLong())).thenReturn(projectEntity);
        when(taskDao.findTasksByProjectId(anyLong())).thenReturn(Collections.emptyList());

        // Test
        ProjectDto result = projectBean.getProjectById(1L);
        assertNotNull(result);
        assertEquals("Test Project", result.getTitle());
    }

    @Test
    void testUpdateProject() {
        // Mocking dependencies
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(1L);
        projectDto.setTitle("Updated Project");
        projectDto.setStatus(200);
        projectDto.setStartingDate(LocalDate.of(2023, 7, 1).atStartOfDay());
        projectDto.setPlannedEndDate(LocalDate.of(2023, 12, 31).atTime(LocalTime.MAX));

        WorkplaceDto workplaceDto = new WorkplaceDto();
        workplaceDto.setName("Mocked Workplace");
        projectDto.setWorkplace(workplaceDto);

        String token = "mockedToken";
        when(serviceBean.getUserIdFromToken(token)).thenReturn(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        when(userDao.findUserById(anyLong())).thenReturn(userEntity);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(1L);
        projectEntity.setTitle("Original Project");

        when(projectDao.findProjectById(anyLong())).thenReturn(projectEntity);

        WorkplaceEntity workplaceEntity = new WorkplaceEntity();
        workplaceEntity.setName("Mocked Workplace");
        when(workplaceDao.findWorkplaceByName(anyString())).thenReturn(workplaceEntity);

        TaskEntity finalTaskEntity = new TaskEntity();
        when(taskBean.getFinalTaskOfProject(anyLong())).thenReturn(finalTaskEntity);

        // Test
        assertDoesNotThrow(() -> projectBean.updateProject(1L, projectDto, token));

        // Verify updates
        assertEquals("Updated Project", projectEntity.getTitle());
        assertEquals(ProjectStatus.READY, projectEntity.getStatus());
        assertEquals(LocalDateTime.of(2023, 7, 1, 0, 0), projectEntity.getStartingDate());
        assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999), projectEntity.getPlannedEndDate());
        assertEquals(workplaceEntity, projectEntity.getWorkplace());

        // Verify final task updates
        assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999), finalTaskEntity.getPlannedStartingDate());
        assertEquals(LocalDateTime.of(2023, 12, 31, 23, 59, 59, 999999999), finalTaskEntity.getPlannedEndingDate());

        // Verify notification was sent
        verify(notificationBean, times(1)).sendProjectAproval(projectEntity);
    }

    @Test
    void testGetTasksByProjectId() {
        // Mocking dependencies
        List<TaskEntity> tasks = new ArrayList<>();
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle("Task 1");
        taskEntity.setStatus(TaskStatus.TODO);
        taskEntity.setPriority(TaskPriority.HIGH);
        UserEntity user = new UserEntity();
        taskEntity.setUser(user);
        ProjectEntity project = new ProjectEntity();
        taskEntity.setProject(project);
        tasks.add(taskEntity);


        when(taskDao.findTasksByProjectId(anyLong())).thenReturn(tasks);

        // Test
        List<TaskDto> result = projectBean.getTasksByProjectId(1L);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetUsersByProject() {
        // Mocking data
        Long projectId = 1L;
        UserProjectEntity user = new UserProjectEntity();
        UserEntity userEntity = new UserEntity();
        user.setUser(userEntity);
        ProjectEntity project = new ProjectEntity();
        user.setProject(project);

        List<UserProjectEntity> mockUserProjectEntities = Collections.singletonList(user);

        // Mocking projectDao
        when(projectDao.findUserProjectsByProjectId(projectId)).thenReturn(mockUserProjectEntities);

        // Method call
        List<UserProjectDto> result = projectBean.getUsersByProject(projectId);

        // Verification
        verify(projectDao, times(1)).findUserProjectsByProjectId(projectId);
        assertEquals(mockUserProjectEntities.size(), result.size());
        // Add more assertions as needed
    }

    @Test
    public void testGetPossibleDependentTasks() {
        // Mocking data
        Long projectId = 1L;
        TaskEndDateDto plannedStartingDate = new TaskEndDateDto(); // Initialize with necessary fields
        TaskEntity task = new TaskEntity();
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);
        UserEntity user = new UserEntity();
        ProjectEntity project = new ProjectEntity();
        task.setProject(project);
        task.setUser(user);
        List<TaskEntity> mockTaskEntities = Collections.singletonList(task);

        // Mocking projectDao
        when(projectDao.findTasksByProjectIdAndEndingDate(eq(projectId), any())).thenReturn(mockTaskEntities);

        // Method call
        List<TaskDto> result = projectBean.getPossibleDependentTasks(projectId, plannedStartingDate);

        assertEquals(mockTaskEntities.size(), result.size());
    }

    @Test
    public void testGetAverageUsersPerProject() {
        // Mocking data
        long totalProjects = 10L;
        long totalUsers = 30L;

        // Mocking projectDao
        when(projectDao.getTotalProjectCount()).thenReturn(totalProjects);
        when(projectDao.getTotalUserCount()).thenReturn(totalUsers);

        // Method call
        double result = projectBean.getAverageUsersPerProject();

        // Verification
        verify(projectDao, times(1)).getTotalProjectCount();
        verify(projectDao, times(1)).getTotalUserCount();
        assertEquals((double) totalUsers / totalProjects, result);
        // Add more assertions as needed
    }

    @Test
    public void testSearchProjects() {
        // Mocking data
        String searchTerm = "searchTerm";
        String skillString = "skill";
        String interestString = "interest";
        ProjectStatus status = ProjectStatus.READY;
        ProjectEntity project = new ProjectEntity();
        project.setStatus(ProjectStatus.IN_PROGRESS);
        UserEntity user = new UserEntity();
        project.setOwner(user);
        List<ProjectEntity> mockProjects = Collections.singletonList(project);

        // Mocking projectDao
        when(projectDao.searchProjects(eq(searchTerm), eq(skillString), eq(interestString), eq(status))).thenReturn(mockProjects);

        // Method call
        Set<ProjectDto> result = projectBean.searchProjects(searchTerm, skillString, interestString, status);

        // Verification
        verify(projectDao, times(1)).searchProjects(eq(searchTerm), eq(skillString), eq(interestString), eq(status));
        assertEquals(mockProjects.size(), result.size());
        // Add more assertions as needed
    }

    @Test
    public void testRemoveComponentsFromProject() {
        // Mocking data
        Long projectId = 1L;
        List<Long> componentsToRemove = Arrays.asList(1L, 2L);

        ProjectEntity project = new ProjectEntity();
        UserEntity user = new UserEntity();
        project.setOwner(user);
        project.setStatus(ProjectStatus.IN_PROGRESS);

        // Mocking projectDao
        when(projectDao.findProjectById(projectId)).thenReturn(project); // Mocking project entity retrieval

        // Mocking componentDao
        ComponentEntity mockComponent1 = new ComponentEntity();
        mockComponent1.setId(1L);
        ComponentEntity mockComponent2 = new ComponentEntity();
        mockComponent2.setId(2L);
        when(componentDao.findComponentById(1L)).thenReturn(mockComponent1);
        when(componentDao.findComponentById(2L)).thenReturn(mockComponent2);

        // Method call
        assertDoesNotThrow(() -> projectBean.removeComponentsFromProject(componentsToRemove, projectId));

        // Verification
        verify(projectDao, times(1)).findProjectById(projectId);
        verify(componentDao, times(1)).findComponentById(1L);
        verify(componentDao, times(1)).findComponentById(2L);
        // Add more verifications as needed
    }

    @Test
    public void testRemoveResourcesFromProject() {
        // Mocking data
        Long projectId = 1L;
        List<Long> resourcesToRemove = Arrays.asList(1L, 2L);

        ProjectEntity project = new ProjectEntity();
        UserEntity user = new UserEntity();
        project.setOwner(user);
        project.setStatus(ProjectStatus.IN_PROGRESS);

        // Mocking projectDao
        when(projectDao.findProjectById(projectId)).thenReturn(project); // Mocking project entity retrieval

        // Mocking resourceDao
        ResourceEntity mockResource1 = new ResourceEntity();
        mockResource1.setId(1L);
        ResourceEntity mockResource2 = new ResourceEntity();
        mockResource2.setId(2L);
        when(resourceDao.findById(1L)).thenReturn(mockResource1);
        when(resourceDao.findById(2L)).thenReturn(mockResource2);

        // Method call
        assertDoesNotThrow(() -> projectBean.removeResourcesFromProject(resourcesToRemove, projectId));

        // Verification
        verify(projectDao, times(1)).findProjectById(projectId);
        verify(resourceDao, times(1)).findById(1L);
        verify(resourceDao, times(1)).findById(2L);
        // Add more verifications as needed
    }
}

