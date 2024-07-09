package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Data Access Object (DAO) for managing ResourceEntity entities.
 * Provides methods to perform CRUD operations and retrieve resources from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ResourceDao extends AbstractDao<ResourceEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the ResourceDao initializing with ResourceEntity class.
     */
    public ResourceDao() {
        super(ResourceEntity.class);
    }

    /**
     * Retrieves a resource by its ID.
     *
     * @param id The ID of the resource to retrieve.
     * @return The ResourceEntity object with the specified ID, or null if not found.
     */
    public ResourceEntity findById(Long id) {
        try {
            TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findById", ResourceEntity.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves a resource by its name.
     *
     * @param name The name of the resource to retrieve.
     * @return The ResourceEntity object with the specified name, or null if not found.
     */
    public ResourceEntity findByName(String name) {
        try {
            TypedQuery<ResourceEntity> query = em.createNamedQuery("Resource.findByName", ResourceEntity.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Returns null if no component is found with the specified name
        }
    }

    /**
     * Retrieves projects associated with a resource ID.
     *
     * @param resourceId The ID of the resource to retrieve associated projects for.
     * @return A set of ProjectEntity objects associated with the specified resource ID.
     */
    public Set<ProjectEntity> findProjectsByResourceId(Long resourceId) {
        TypedQuery<ProjectEntity> query = em.createNamedQuery("ResourceEntity.findProjectsByResourceId", ProjectEntity.class);
        query.setParameter("id", resourceId);
        return new HashSet<>(query.getResultList());
    }

    /**
     * Retrieves all resources ordered by name with pagination.
     *
     * @param page  The page number for pagination (1-based).
     * @param limit The maximum number of resources per page.
     * @return A list of ResourceEntity objects within the specified page and limit, ordered by name.
     */
    public List<ResourceEntity> findAllOrderedByName(int page, int limit) {
        return em.createNamedQuery("Resource.findAllOrderedByName", ResourceEntity.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Retrieves the total count of resources.
     *
     * @return The total number of resources in the database.
     */
    public long getTotalResourcesCount() {
        TypedQuery<Long> query = em.createNamedQuery("Resource.getTotalResourcesCount", Long.class);
        return query.getSingleResult();
    }

    /**
     * Retrieves resources that match a keyword ordered by name with pagination.
     *
     * @param page The page number for pagination (1-based).
     * @param limit The maximum number of resources per page.
     * @param keyword The keyword to search for in resource names or descriptions.
     * @return A list of ResourceEntity objects that match the keyword within the specified page and limit, ordered by name.
     */
    public List<ResourceEntity> findByKeywordOrderedByName(int page, int limit,String keyword) {
        return em.createNamedQuery("Resource.findByKeywordOrderedByName", ResourceEntity.class)
                .setParameter("keyword", keyword)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Retrieves the count of resources that match a keyword.
     *
     * @param keyword The keyword to search for in resource names or descriptions.
     * @return The number of resources that match the keyword.
     */
    public long countByKeyword(String keyword) {
        return em.createNamedQuery("Resource.countByKeyword", Long.class)
                .setParameter("keyword", keyword)
                .getSingleResult();
    }

    /**
     * Retrieves resources that are expiring within a week from now.
     *
     * @param now The current date and time.
     * @param oneWeekFromNow The date and time one week from now.
     * @return A list of ResourceEntity objects that are expiring within the specified time frame.
     */
    public List<ResourceEntity> findResourcesExpiringWithinWeek(LocalDateTime now, LocalDateTime oneWeekFromNow) {
        try{
            TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findResourcesExpiringWithinWeek", ResourceEntity.class);
            query.setParameter("now", now);
            query.setParameter("oneWeekFromNow", oneWeekFromNow);

            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves resources that are currently unused (not associated with any project).
     *
     * @return A list of ResourceEntity objects that are currently unused.
     */
    public List<ResourceEntity> findUnusedResources() {
        try {
            TypedQuery<ResourceEntity> query = em.createNamedQuery("ResourceEntity.findUnusedResources", ResourceEntity.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}

