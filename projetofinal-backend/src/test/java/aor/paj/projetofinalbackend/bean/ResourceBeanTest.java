package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ResourceDao;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ResourceBeanTest {

    @Mock
    private ResourceDao resourceDaoMock;

    @Mock
    private ProjectDao projectDaoMock;

    @InjectMocks
    private ResourceBean resourceBean;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAllResources() {
        // Mock data
        List<ResourceEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new ResourceEntity());
        mockEntities.add(new ResourceEntity());
        when(resourceDaoMock.findAllOrderedByName(anyInt(), anyInt())).thenReturn(mockEntities);

        // Call method
        List<ResourceDto> result = resourceBean.allResources(0, 10);

        // Assertions
        assertEquals(mockEntities.size(), result.size());
        verify(resourceDaoMock, times(1)).findAllOrderedByName(anyInt(), anyInt());
    }

    @Test
    public void testUpdateResource() {
        // Mock data
        ResourceDto dto = new ResourceDto();
        dto.setId(1L);
        ResourceEntity mockEntity = new ResourceEntity();
        when(resourceDaoMock.findById(dto.getId())).thenReturn(mockEntity);

        // Call method
        resourceBean.updateResource(dto);

        // Assertions
        verify(resourceDaoMock, times(1)).findById(dto.getId());
        verify(resourceDaoMock, times(1)).merge(any(ResourceEntity.class));
    }

    @Test
    public void testAddResourceDefault() {
        // Mock data
        ResourceDto dto = new ResourceDto();
        ResourceEntity mockEntity = new ResourceEntity();
        doNothing().when(resourceDaoMock).persist(any(ResourceEntity.class));

        // Call method
        resourceBean.addResourceDefault(dto);

        // Assertions
        verify(resourceDaoMock, times(1)).persist(any(ResourceEntity.class));
    }

    @Test
    public void testAddResourceInProject() {
        // Mock data
        ResourceDto dto = new ResourceDto();
        List<Long> projectIds = List.of(1L, 2L);
        ProjectEntity mockProject = new ProjectEntity();
        ResourceEntity mockEntity = new ResourceEntity();

        // Configurando comportamento dos mocks
        doNothing().when(resourceDaoMock).persist(any(ResourceEntity.class));
        doNothing().when(resourceDaoMock).merge(any(ResourceEntity.class));
        when(projectDaoMock.findProjectById(anyLong())).thenReturn(mockProject);
        doNothing().when(projectDaoMock).merge(any(ProjectEntity.class));

        // Chamando o método a ser testado
        resourceBean.addResourceInProject(dto, projectIds);

        // Verificações
        verify(resourceDaoMock, times(1)).persist(any(ResourceEntity.class)); // Verifica se persist foi chamado uma vez
        verify(projectDaoMock, times(projectIds.size())).findProjectById(anyLong()); // Verifica se findProjectById foi chamado a quantidade correta de vezes
        verify(projectDaoMock, times(projectIds.size())).merge(any(ProjectEntity.class)); // Verifica se merge do projectDaoMock foi chamado a quantidade correta de vezes
    }





    @Test
    public void testFindResourcesExpiringWithinWeek() {
        // Mock data
        List<ResourceEntity> mockEntities = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekFromNow = now.plusWeeks(1);
        when(resourceDaoMock.findResourcesExpiringWithinWeek(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(mockEntities);

        // Call method
        List<ResourceEntity> result = resourceBean.findResourcesExpiringWithinWeek();

        // Assertions
        assertEquals(mockEntities.size(), result.size());
        verify(resourceDaoMock, times(1)).findResourcesExpiringWithinWeek(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void testGetUnusedResourcesNames() {
        // Mock data
        List<ResourceEntity> mockEntities = new ArrayList<>();
        ResourceEntity resource1 = new ResourceEntity();
        resource1.setName("Resource 1");
        ResourceEntity resource2 = new ResourceEntity();
        resource2.setName("Resource 2");
        mockEntities.add(resource1);
        mockEntities.add(resource2);
        when(resourceDaoMock.findUnusedResources()).thenReturn(mockEntities);

        // Call method
        String result = resourceBean.getUnusedResourcesNames();

        // Assertions
        assertEquals("Resource 1, Resource 2", result);
        verify(resourceDaoMock, times(1)).findUnusedResources();
    }


    @Test
    public void testAllResourcesSearch() {
        // Mock data
        List<ResourceEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new ResourceEntity());
        mockEntities.add(new ResourceEntity());
        when(resourceDaoMock.findByKeywordOrderedByName(anyInt(), anyInt(), anyString())).thenReturn(mockEntities);

        // Call method
        List<ResourceDto> result = resourceBean.allResourcesSearch(0, 10, "keyword");

        // Assertions
        assertEquals(mockEntities.size(), result.size());
        verify(resourceDaoMock, times(1)).findByKeywordOrderedByName(anyInt(), anyInt(), anyString());
    }

    @Test
    public void testGetTotalCountBySearch() {
        // Mock data
        when(resourceDaoMock.countByKeyword(anyString())).thenReturn(10L);

        // Call method
        long result = resourceBean.getTotalCountBySearch("keyword");

        // Assertions
        assertEquals(10L, result);
        verify(resourceDaoMock, times(1)).countByKeyword(anyString());
    }

    @Test
    public void testGetTotalResourcesCount() {
        // Mock data
        when(resourceDaoMock.getTotalResourcesCount()).thenReturn(100L);

        // Call method
        long result = resourceBean.getTotalResourcesCount();

        // Assertions
        assertEquals(100L, result);
        verify(resourceDaoMock, times(1)).getTotalResourcesCount();
    }

    @Test
    public void testGetAllWithoutFilters() {
        // Mock data
        List<ResourceEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new ResourceEntity());
        mockEntities.add(new ResourceEntity());
        when(resourceDaoMock.findAll()).thenReturn(mockEntities);

        // Call method
        List<ResourceDto> result = resourceBean.getAllWithoutFilters();

        // Assertions
        assertEquals(mockEntities.size(), result.size());
        verify(resourceDaoMock, times(1)).findAll();
    }
}
