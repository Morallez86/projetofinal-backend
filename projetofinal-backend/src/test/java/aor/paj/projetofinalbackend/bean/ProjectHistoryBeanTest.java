package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ProjectHistoryDto;
import aor.paj.projetofinalbackend.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class ProjectHistoryBeanTest {

    @Mock
    private ProjectHistoryDao projectHistoryDaoMock;

    @Mock
    private ProjectDao projectDaoMock;

    @Mock
    private ServiceBean serviceBeanMock;

    @Mock
    private UserDao userDaoMock;

    @Mock
    private TaskDao taskDaoMock;

    @InjectMocks
    private ProjectHistoryBean projectHistoryBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize all annotated mocks
    }

    @Test
    public void testLogUserStatusChange() {
        // Mock data
        UserProjectEntity userProjectEntity = new UserProjectEntity();// Mock or create as needed
        Boolean newStatus = true; // Assuming new status for testing
        UserEntity userSending = new UserEntity(); // Mock or create as needed
        ProjectEntity project = new ProjectEntity();
        userProjectEntity.setProject(project);

        userProjectEntity.setUser(userSending);

        // Call method
        projectHistoryBean.logUserStatusChange(userProjectEntity, newStatus, userSending);

        // Assertions and verifications
        verify(projectHistoryDaoMock, times(1)).persist(any(ProjectHistoryEntity.class));
        verify(projectDaoMock, times(1)).merge(any(ProjectEntity.class));
    }

    @Test
    public void testLogUserInactiveChange() {
        // Mock data
        UserProjectEntity userProjectEntity = new UserProjectEntity(); // Mock or create as needed
        UserEntity userSending = new UserEntity(); // Mock or create as needed
        userProjectEntity.setUser(userSending);
        ProjectEntity project = new ProjectEntity();
        userProjectEntity.setProject(project);

        // Call method
        projectHistoryBean.logUserInactiveChange(userProjectEntity, userSending);

        // Assertions and verifications
        verify(projectHistoryDaoMock, times(1)).persist(any(ProjectHistoryEntity.class));
        verify(projectDaoMock, times(1)).merge(any(ProjectEntity.class));
    }
}
