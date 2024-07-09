package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.dto.ResourceDto;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import aor.paj.projetofinalbackend.mapper.ResourceMapper;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application scoped bean responsible for managing resources.
 * @see ProjectDao
 * @see ResourceDao
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@ApplicationScoped
public class ResourceBean {

    @EJB
    ProjectDao projectDao;

    @EJB
    ResourceDao resourceDao;

    /**
     * Retrieves a list of all resources with pagination.
     *
     * @param page Page number (starting from 0).
     * @param limit Maximum number of resources per page.
     * @return List of ResourceDto objects representing resources.
     */
    public List<ResourceDto> allResources (int page, int limit) {
        List<ResourceEntity> resourceEntities = resourceDao.findAllOrderedByName(page,limit);
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Updates an existing resource.
     *
     * @param dto ResourceDto object containing updated resource information.
     */
    public void updateResource (ResourceDto dto) {
        ResourceEntity resourcedData = resourceDao.findById(dto.getId());
        ResourceEntity resourceEntity = ResourceMapper.toEntity(dto);
        resourceEntity.setProjects(resourcedData.getProjects());
        resourceDao.merge(resourceEntity);
    }

    /**
     * Retrieves the total count of all resources.
     *
     * @return Total count of resources.
     */
    public long getTotalResourcesCount () {
        return resourceDao.getTotalResourcesCount();
    }

    /**
     * Retrieves a list of resources matching a search keyword with pagination.
     *
     * @param page     Page number (starting from 0).
     * @param limit    Maximum number of resources per page.
     * @param keyWord  Search keyword to filter resources.
     * @return List of ResourceDto objects representing filtered resources.
     */
    public List<ResourceDto> allResourcesSearch (int page, int limit, String keyWord) {
        List<ResourceEntity> resourceEntities = resourceDao.findByKeywordOrderedByName(page, limit, keyWord);
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Retrieves the total count of resources matching a search keyword.
     *
     * @param keyWord Search keyword to filter resources.
     * @return Total count of resources matching the keyword.
     */
    public long getTotalCountBySearch (String keyWord) {
        return resourceDao.countByKeyword(keyWord);
    }

    /**
     * Retrieves a list of all resources without applying any filters.
     *
     * @return List of ResourceDto objects representing all resources.
     */
    public List<ResourceDto> getAllWithoutFilters () {
        List<ResourceEntity> resourceEntities = resourceDao.findAll();
        return resourceEntities.stream().map(ResourceMapper::toDto).collect(Collectors.toList());

    }

    /**
     * Adds a new resource with default settings.
     *
     * @param dto ResourceDto object containing information of the resource to be added.
     */
    public void addResourceDefault( ResourceDto dto) {
        ResourceEntity componentEntity = ResourceMapper.toEntity(dto);
        resourceDao.persist(componentEntity);
    }

    /**
     * Associates a resource with multiple projects.
     *
     * @param dto        ResourceDto object containing information of the resource to be associated.
     * @param projectIds List of IDs of projects to associate the resource with.
     */
    @Transactional
    public void addResourceInProject(ResourceDto dto, List<Long> projectIds) {
        ResourceEntity resourceEntity = ResourceMapper.toEntity(dto);

        // Persist the resource first to get its ID
        resourceDao.persist(resourceEntity);

        for (Long projectId : projectIds) {
            ProjectEntity project = projectDao.findProjectById(projectId);
            if (project != null) {
                project.getResources().add(resourceEntity);
                projectDao.merge(project);
            } else {
                // Handle the case where the project ID does not exist
                throw new IllegalArgumentException("Project with ID " + projectId + " not found");
            }
        }
    }

    /**
     * Retrieves a list of resources that are expiring within the next week.
     *
     * @return List of ResourceEntity objects representing expiring resources.
     */
    public List<ResourceEntity> findResourcesExpiringWithinWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekFromNow = now.plusWeeks(1);
        return resourceDao.findResourcesExpiringWithinWeek(now, oneWeekFromNow);
    }

    /**
     * Retrieves names of resources that are currently unused.
     *
     * @return String containing names of unused resources separated by commas.
     */
    public String getUnusedResourcesNames() {
        List<ResourceEntity> unusedResources = resourceDao.findUnusedResources();
        return unusedResources.stream()
                .map(ResourceEntity::getName)
                .collect(Collectors.joining(", "));
    }
}
