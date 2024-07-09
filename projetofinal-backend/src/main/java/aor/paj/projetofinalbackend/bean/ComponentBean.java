package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ComponentDao;
import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.dto.ComponentDto;
import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.mapper.ComponentMapper;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean responsible for managing components. It handles adding, updating,
 * retrieving, and searching for components in projects.
 *
 * @see ComponentDao
 * @see ProjectDao
 * @see UserDao
 * @see ComponentDto
 * @see ComponentEntity
 * @see ProjectEntity
 * @see ComponentMapper
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class ComponentBean {

    @EJB
    ComponentDao componentDao;

    @EJB
    ProjectDao projectDao;

    /**
     * Adds a default component.
     *
     * @param dto the DTO containing component details
     */
    public void addComponentDefault(ComponentDto dto) {
        ComponentEntity componentEntity = ComponentMapper.toEntity(dto);
        componentDao.persist(componentEntity);
    }

    /**
     * Adds a component to a project.
     *
     * @param dto the DTO containing component details
     * @param projectId the ID of the project
     */
    @Transactional
    public void addComponentInProject(ComponentDto dto, Long projectId) {
        ProjectEntity project = projectDao.findProjectById(projectId);
        ComponentEntity componentEntity = ComponentMapper.toEntity(dto);
        project.getComponents().add(componentEntity);
        projectDao.merge(project);
        componentEntity.setProject(project);
        componentDao.merge(componentEntity);
    }

    /**
     * Updates an existing component.
     *
     * @param dto the DTO containing updated component details
     */
    public void updateComponent(ComponentDto dto) {
        ComponentEntity componentEntity = ComponentMapper.toEntity(dto);
        ProjectEntity project = projectDao.findProjectById(dto.getProjectId());
        componentEntity.setProject(project);
        componentDao.merge(componentEntity);
    }

    /**
     * Retrieves a paginated list of all components.
     *
     * @param page the page number
     * @param limit the number of components per page
     * @return a list of component DTOs
     */
    public List<ComponentDto> allComponents(int page, int limit) {
        List<ComponentEntity> componentEntities = componentDao.findAllOrderedByName(page, limit);
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Gets the total count of components.
     *
     * @return the total number of components
     */
    public long getTotalComponentsCount() {
        return componentDao.getTotalComponentsCount();
    }

    /**
     * Searches for components based on a keyword.
     *
     * @param page the page number
     * @param limit the number of components per page
     * @param keyWord the keyword to search for
     * @return a list of component DTOs
     */
    public List<ComponentDto> allComponentsSearch(int page, int limit, String keyWord) {
        List<ComponentEntity> componentEntities = componentDao.findByKeywordOrderedByName(page, limit, keyWord);
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Gets the total count of components based on a search keyword.
     *
     * @param keyWord the keyword to search for
     * @return the total number of components matching the keyword
     */
    public long getTotalCountBySearch(String keyWord) {
        return componentDao.countByKeyword(keyWord);
    }

    /**
     * Retrieves all components without any filters.
     *
     * @return a list of component DTOs
     */
    public List<ComponentDto> getAllWithoutFilters() {
        List<ComponentEntity> componentEntities = componentDao.findAll();
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Retrieves all components associated with a specific workplace ID.
     *
     * @param workplaceId the ID of the workplace
     * @return a list of component DTOs
     */
    public List<ComponentDto> getAllByWorkplaceId(Long workplaceId) {
        List<ComponentEntity> componentEntities = componentDao.findByWorkplaceId(workplaceId);
        return componentEntities.stream().map(ComponentMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Finds available components grouped by name for a specific workplace.
     *
     * @param workplaceId the ID of the workplace
     * @return a list of component names
     */
    public List<String> findAvailableComponentsGroupedByName(Long workplaceId) {
        return componentDao.findAvailableComponentsGroupedByName(workplaceId);
    }

    /**
     * Calculates the total percentage of available components for a specific workplace.
     *
     * @param workplaceName the name of the workplace
     * @return the percentage of available components
     */
    public double getTotalPercentageOfComponentsByWorkplace(String workplaceName) {
        long totalComponents = componentDao.countTotalComponentsByWorkplace(workplaceName);
        long availableComponents = componentDao.countAvailableComponentsByWorkplace(workplaceName);

        if (totalComponents == 0) {
            return 0;
        }
        return (double) availableComponents / totalComponents * 100;
    }
}
