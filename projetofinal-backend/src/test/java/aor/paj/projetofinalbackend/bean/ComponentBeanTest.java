package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.bean.ComponentBean;
import aor.paj.projetofinalbackend.dao.ComponentDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.mapper.ComponentMapper;

import jakarta.ejb.EJB;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ComponentBeanTest {

    @InjectMocks
    private ComponentBean componentBean;

    @Mock
    private ProjectDao projectDao;

    @Mock
    private ComponentDao componentDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddComponentDefault() {
        // Mock data
        ComponentDto dto = new ComponentDto();
        dto.setId(1L);
        dto.setName("Component 1");

        // Test
        componentBean.addComponentDefault(dto);

        // Verification
        verify(componentDao, times(1)).persist(any(ComponentEntity.class));
    }

    @Test
    public void testAddComponentInProject() {
        // Mock data
        ComponentDto dto = new ComponentDto();
        dto.setId(1L);
        dto.setName("Component 1");

        Long projectId = 1L;
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);

        when(projectDao.findProjectById(projectId)).thenReturn(projectEntity);

        // Test
        componentBean.addComponentInProject(dto, projectId);

        // Verification
        verify(projectDao, times(1)).findProjectById(projectId);
        verify(projectDao, times(1)).merge(any(ProjectEntity.class));
        verify(componentDao, times(1)).merge(any(ComponentEntity.class));
    }

    @Test
    public void testUpdateComponent() {
        // Mock data
        ComponentDto dto = new ComponentDto();
        dto.setId(1L);
        dto.setName("Updated Component");
        dto.setProjectId(1L);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(dto.getProjectId());

        when(projectDao.findProjectById(dto.getProjectId())).thenReturn(projectEntity);

        // Test
        componentBean.updateComponent(dto);

        // Verification
        verify(projectDao, times(1)).findProjectById(dto.getProjectId());
        verify(componentDao, times(1)).merge(any(ComponentEntity.class));
    }

    
}
