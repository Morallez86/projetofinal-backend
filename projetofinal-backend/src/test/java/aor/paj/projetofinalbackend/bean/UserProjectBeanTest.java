package aor.paj.projetofinalbackend.bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserProjectDao;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserProjectBeanTest {

    private UserProjectBean userProjectBean;

    private UserProjectDao userProjectDao;
    private ProjectDao projectDao;

    @BeforeEach
    public void setUp() {
        userProjectDao = mock(UserProjectDao.class);
        projectDao = mock(ProjectDao.class);
        userProjectBean = new UserProjectBean();
        userProjectBean.userProjectDao = userProjectDao;
        userProjectBean.projectDao = projectDao;
    }

    //isUserInProject
    @Test
    public void testIsUserInProjectWhenUserIsInProject() {
        // Arrange
        Long userId = 1L;
        Long projectId = 1L;
        UserProjectEntity userProjectEntity = new UserProjectEntity();
        when(userProjectDao.findByUserAndProjectActive(userId, projectId)).thenReturn(userProjectEntity);

        // Act
        boolean result = userProjectBean.isUserInProject(userId, projectId);

        // Assert
        assertTrue(result);
        verify(userProjectDao, times(1)).findByUserAndProjectActive(userId, projectId);
    }

    @Test
    public void testIsUserInProjectWhenUserIsNotInProject() {
        // Arrange
        Long userId = 1L;
        Long projectId = 1L;
        when(userProjectDao.findByUserAndProjectActive(userId, projectId)).thenReturn(null);

        // Act
        boolean result = userProjectBean.isUserInProject(userId, projectId);

        // Assert
        assertFalse(result);
        verify(userProjectDao, times(1)).findByUserAndProjectActive(userId, projectId);
    }

    //isProjectAtMaxUsers

    @Test
    public void testIsProjectAtMaxUsersWhenNotAtMax() {
        // Arrange
        Long projectId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setMaxUsers(5);
        when(projectDao.findProjectById(projectId)).thenReturn(projectEntity);
        when(userProjectDao.countActiveUsersByProjectId(projectId)).thenReturn(3L);

        // Act
        boolean result = userProjectBean.isProjectAtMaxUsers(projectId);

        // Assert
        assertFalse(result);
        verify(projectDao, times(1)).findProjectById(projectId);
        verify(userProjectDao, times(1)).countActiveUsersByProjectId(projectId);
    }

    @Test
    public void testIsProjectAtMaxUsersWhenAtMax() {
        // Arrange
        Long projectId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setMaxUsers(5);
        when(projectDao.findProjectById(projectId)).thenReturn(projectEntity);
        when(userProjectDao.countActiveUsersByProjectId(projectId)).thenReturn(5L);

        // Act
        boolean result = userProjectBean.isProjectAtMaxUsers(projectId);

        // Assert
        assertTrue(result);
        verify(projectDao, times(1)).findProjectById(projectId);
        verify(userProjectDao, times(1)).countActiveUsersByProjectId(projectId);
    }

    //isUserAdminAndActiveInProject
    @Test
    public void testIsUserAdminAndActiveInProjectWhenUserIsAdmin() {
        // Arrange
        Long projectId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        List<UserEntity> activeAdmins = new ArrayList<>();
        activeAdmins.add(userEntity);
        when(userProjectDao.findAdminsByProjectIdActive(projectId)).thenReturn(activeAdmins);

        // Act
        boolean result = userProjectBean.isUserAdminAndActiveInProject(userEntity, projectId);

        // Assert
        assertTrue(result);
        verify(userProjectDao, times(1)).findAdminsByProjectIdActive(projectId);
    }

    @Test
    public void testIsUserAdminAndActiveInProjectWhenUserIsNotAdmin() {
        // Arrange
        Long projectId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        List<UserEntity> activeAdmins = new ArrayList<>();
        when(userProjectDao.findAdminsByProjectIdActive(projectId)).thenReturn(activeAdmins);

        // Act
        boolean result = userProjectBean.isUserAdminAndActiveInProject(userEntity, projectId);

        // Assert
        assertFalse(result);
        verify(userProjectDao, times(1)).findAdminsByProjectIdActive(projectId);
    }





}
