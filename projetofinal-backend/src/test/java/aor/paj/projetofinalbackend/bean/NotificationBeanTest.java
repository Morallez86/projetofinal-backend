package aor.paj.projetofinalbackend.bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import aor.paj.projetofinalbackend.bean.NotificationBean;
import aor.paj.projetofinalbackend.dao.NotificationDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dao.UserNotificationDao;
import aor.paj.projetofinalbackend.dto.NotificationDto;
import aor.paj.projetofinalbackend.entity.NotificationEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Collections;
import java.util.List;


public class NotificationBeanTest {

    @Mock
    private ProjectDao projectDao;

    @Mock
    private UserDao userDao;

    @Mock
    private NotificationDao notificationDao;

    @Mock
    private UserNotificationDao userNotificationDao;

    @InjectMocks
    private NotificationBean notificationBean;

    @InjectMocks
    private UserProjectBean userProjectBean;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void sendRequestInvitationProject_InvalidProjectId_ExceptionThrown() {
        // Mock data
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setProjectId(999L); // Invalid project ID

        UserEntity sender = new UserEntity();

        // Mock behaviors
        when(projectDao.findProjectById(999L)).thenReturn(null);

        // Test method invocation and expectation of exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationBean.sendRequestInvitationProject(sender, notificationDto);
        });

        // Verify
        assertEquals("Invalid project ID", exception.getMessage());
        verify(notificationDao, never()).persist(any());
        verify(userNotificationDao, never()).persist(any());
    }

    @Test
    public void approveOrRejectNotificationParticipateProject_RejectRequest_Success() {
        // Mock data
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(1L);
        notificationDto.setProjectId(1L);
        notificationDto.setReceiverId(2L);
        notificationDto.setApproval(false);
        notificationDto.setType("300"); // Managing type

        UserEntity sender = new UserEntity();
        sender.setId(3L); // Assuming sender ID for testing

        UserEntity receiver = new UserEntity();
        receiver.setId(2L);

        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setOwner(sender);

        NotificationEntity notification = new NotificationEntity();
        notification.setId(1L);
        notification.setSender(sender);
        notification.setProject(project);

        // Mock behaviors
        when(notificationDao.find(1L)).thenReturn(notification);
        when(projectDao.findProjectById(1L)).thenReturn(project);
        when(userDao.findUserById(2L)).thenReturn(receiver);

        // Here, set up doNothing() for notificationDao.persist(any())
        doNothing().when(notificationDao).persist(any());

        // Test method invocation
        assertDoesNotThrow(() -> notificationBean.approveOrRejectNotificationParticipateProject(notificationDto, sender, false));

        // Verify
        verify(notificationDao, times(1)).persist(any()); // One for response notification, one for user notification
        verify(userNotificationDao, times(1)).persist(any());
    }


    @Test
    public void sendProjectApproval_Success() {
        // Mock data
        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setTitle("Test Project");

        UserEntity owner = new UserEntity();
        owner.setId(2L);
        owner.setUsername("owner");

        project.setOwner(owner);


        List<UserEntity> admins = Collections.singletonList(owner);

        // Mock behaviors
        when(userDao.findAdmins()).thenReturn(admins);

        // Here, set up doNothing() for notificationDao.persist(any())
        doNothing().when(notificationDao).persist(any());

        // Test method invocation
        assertDoesNotThrow(() -> notificationBean.sendProjectAproval(project));

        // Verify
        verify(notificationDao, times(1)).persist(any());
        verify(userDao, times(1)).findAdmins();
    }

    @Test
    public void approveOrRejectNotificationReadyProject_ApproveProject_Success() {
        // Mock data
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(1L);
        notificationDto.setProjectId(1L);
        notificationDto.setApproval(true);

        UserEntity sender = new UserEntity();
        sender.setId(2L); // Assuming sender ID for testing

        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setOwner(sender);

        NotificationEntity notification = new NotificationEntity();
        notification.setId(1L);
        notification.setSender(sender);
        notification.setProject(project);

        // Mock behaviors
        when(notificationDao.find(1L)).thenReturn(notification);
        when(projectDao.findProjectById(1L)).thenReturn(project);
        when(userDao.findAdmins()).thenReturn(Collections.singletonList(sender));

        // Here, set up doNothing() for notificationDao.persist(any())
        doNothing().when(notificationDao).persist(any());

        // Test method invocation
        assertDoesNotThrow(() -> notificationBean.approveOrRejectNotificationReadyProject(notificationDto, sender));

        // Verify
        verify(notificationDao, times(1)).persist(any()); // One for response notification, one for user notification
        verify(projectDao, times(1)).merge(any());
    }

    @Test
    public void approveOrRejectNotificationReadyProject_RejectProject_Success() {
        // Mock data
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(1L);
        notificationDto.setProjectId(1L);
        notificationDto.setApproval(false);

        UserEntity sender = new UserEntity();
        sender.setId(2L); // Assuming sender ID for testing

        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setOwner(sender);

        NotificationEntity notification = new NotificationEntity();
        notification.setId(1L);
        notification.setSender(sender);
        notification.setProject(project);

        // Mock behaviors
        when(notificationDao.find(1L)).thenReturn(notification);
        when(projectDao.findProjectById(1L)).thenReturn(project);
        when(userDao.findAdmins()).thenReturn(Collections.singletonList(sender));

        // Here, set up doNothing() for notificationDao.persist(any())
        doNothing().when(notificationDao).persist(any());

        // Test method invocation
        assertDoesNotThrow(() -> notificationBean.approveOrRejectNotificationReadyProject(notificationDto, sender));

        // Verify
        verify(notificationDao, times(1)).persist(any()); // One for response notification, one for user notification
        verify(projectDao, times(1)).merge(any());
    }

}
