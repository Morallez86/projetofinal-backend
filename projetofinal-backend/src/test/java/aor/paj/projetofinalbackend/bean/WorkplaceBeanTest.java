package aor.paj.projetofinalbackend.bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import aor.paj.projetofinalbackend.dao.WorkplaceDao;
import aor.paj.projetofinalbackend.dto.WorkplaceDto;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import aor.paj.projetofinalbackend.mapper.WorkplaceMapper;
import aor.paj.projetofinalbackend.pojo.WorkplaceProjectCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class WorkplaceBeanTest {

    @Mock
    private WorkplaceDao workplaceDao;

    @InjectMocks
    private WorkplaceBean workplaceBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //check when workplace already exists
    @Test
    public void testCreateWorkplaceWhenWorkplaceAlreadyExists() {
        // Arrange
        String workplaceName = "Existing Workplace";
        WorkplaceEntity existingWorkplace = new WorkplaceEntity();
        existingWorkplace.setName(workplaceName);

        when(workplaceDao.findWorkplaceByName(workplaceName)).thenReturn(existingWorkplace);

        // Act
        WorkplaceEntity result = workplaceBean.createWorkplace(workplaceName);

        // Assert
        assertNull(result);
        verify(workplaceDao, times(1)).findWorkplaceByName(workplaceName);
        verify(workplaceDao, never()).persist(any(WorkplaceEntity.class));
    }

    //check when workplace doesn't exists
    @Test
    public void testCreateWorkplaceWhenWorkplaceDoesNotExist() {
        // Arrange
        String workplaceName = "New Workplace";
        when(workplaceDao.findWorkplaceByName(workplaceName)).thenReturn(null);

        // Act
        WorkplaceEntity result = workplaceBean.createWorkplace(workplaceName);

        // Assert
        assertNotNull(result);
        assertEquals(workplaceName, result.getName());
        verify(workplaceDao, times(1)).findWorkplaceByName(workplaceName);
        verify(workplaceDao, times(1)).persist(result);
    }

    //check get all Workplaces
    @Test
    public void testGetAllWorkplaces() {
        // Arrange
        List<WorkplaceEntity> mockEntities = new ArrayList<>();
        WorkplaceEntity entity1 = new WorkplaceEntity();
        entity1.setId(1L);
        entity1.setName("Workplace 1");
        WorkplaceEntity entity2 = new WorkplaceEntity();
        entity2.setId(2L);
        entity2.setName("Workplace 2");
        mockEntities.add(entity1);
        mockEntities.add(entity2);

        // Mockando o comportamento do DAO
        when(workplaceDao.findAllWorkplaces()).thenReturn(mockEntities);

        // Act
        List<WorkplaceDto> result = workplaceBean.getAllWorkplaces();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("Workplace 1", result.get(0).getName());

        assertEquals(2L, result.get(1).getId());
        assertEquals("Workplace 2", result.get(1).getName());

        // Verificações dos mocks
        verify(workplaceDao, times(1)).findAllWorkplaces();
    }

    @Test
    public void testGetProjectCountPerWorkplace() {

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"Workplace A", 5L});
        mockResults.add(new Object[]{"Workplace B", 3L});
        mockResults.add(new Object[]{"Workplace C", 2L});

        when(workplaceDao.getProjectCountPerWorkplace()).thenReturn(mockResults);


        List<WorkplaceProjectCount> result = workplaceBean.getProjectCountPerWorkplace();


        assertEquals(3, result.size());

        assertEquals("Workplace A", result.get(0).getWorkplaceName());
        assertEquals(5L, result.get(0).getProjectCount());
        assertEquals(50.0, result.get(0).getPercentage(), 0.01);

        assertEquals("Workplace B", result.get(1).getWorkplaceName());
        assertEquals(3L, result.get(1).getProjectCount());
        assertEquals(30.0, result.get(1).getPercentage(), 0.01);

        assertEquals("Workplace C", result.get(2).getWorkplaceName());
        assertEquals(2L, result.get(2).getProjectCount());
        assertEquals(20.0, result.get(2).getPercentage(), 0.01);


        verify(workplaceDao, times(1)).getProjectCountPerWorkplace();
    }



}
