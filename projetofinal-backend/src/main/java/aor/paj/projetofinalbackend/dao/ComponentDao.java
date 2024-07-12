package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ComponentEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Data Access Object (DAO) for managing ComponentEntity entities.
 * Extends AbstractDao providing basic CRUD operations and additional methods specific to ComponentEntity.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ComponentDao extends AbstractDao<ComponentEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the ComponentDao with the entity class ComponentEntity.
     */
    public ComponentDao() {
        super(ComponentEntity.class);
    }

    /**
     * Retrieves a ComponentEntity by its ID.
     *
     * @param id The ID of the component to retrieve.
     * @return The ComponentEntity with the specified ID, or null if not found.
     */
    public ComponentEntity findComponentById(Long id) {
        try {
            TypedQuery<ComponentEntity> query = em.createNamedQuery("Component.findComponentById", ComponentEntity.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum componente for encontrado com o ID especificado
        }
    }

    /**
     * Retrieves a ComponentEntity by its name.
     *
     * @param name The name of the component to retrieve.
     * @return The ComponentEntity with the specified name, or null if not found.
     */
    public ComponentEntity findByName(String name) {
        try {
            TypedQuery<ComponentEntity> query = em.createNamedQuery("Component.findByName", ComponentEntity.class);
            query.setParameter("name", name);
            return query.getResultList().stream().findFirst().orElse(null);
        } catch (NoResultException e) {
            return null; // Returns null if no component is found with the specified name
        }
    }

    /**
     * Retrieves the total count of components.
     *
     * @return The total count of components.
     */
    public long getTotalComponentsCount() {
        TypedQuery<Long> query = em.createNamedQuery("Component.getTotalComponentsCount", Long.class);
        return query.getSingleResult();
    }

    /**
     * Retrieves a list of components ordered by name.
     *
     * @param page  The page number (starting from 1).
     * @param limit The maximum number of components per page.
     * @return A list of ComponentEntity instances ordered by name.
     */
    public List<ComponentEntity> findAllOrderedByName(int page, int limit) {
        return em.createNamedQuery("Component.findAllOrderedByName", ComponentEntity.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Retrieves the count of components matching a keyword.
     *
     * @param keyword The keyword to count components by.
     * @return The count of components matching the keyword.
     */
    public List<ComponentEntity> findByKeywordOrderedByName(int page, int limit,String keyword) {
        return em.createNamedQuery("Component.findByKeywordOrderedByName", ComponentEntity.class)
                .setParameter("keyword", keyword)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Retrieves the count of components matching a keyword.
     *
     * @param keyword The keyword to count components by.
     * @return The count of components matching the keyword.
     */
    public long countByKeyword(String keyword) {
        return em.createNamedQuery("Component.countByKeyword", Long.class)
                .setParameter("keyword", keyword)
                .getSingleResult();
    }

    /**
     * Retrieves a list of components associated with a specific workplace.
     *
     * @param workplaceId The ID of the workplace to retrieve components for.
     * @return A list of ComponentEntity instances associated with the specified workplace.
     */
    public List<ComponentEntity> findByWorkplaceId(Long workplaceId) {
        return em.createNamedQuery("Component.findByWorkplaceId", ComponentEntity.class)
                .setParameter("workplaceId", workplaceId)
                .getResultList();
    }

    /**
     * Retrieves a list of available component names grouped by name for a specific workplace.
     *
     * @param workplaceId The ID of the workplace to retrieve available component names for.
     * @return A list of available component names grouped by name.
     */
    public List<String> findAvailableComponentsGroupedByName(Long workplaceId) {
        TypedQuery<String> query = em.createNamedQuery("Component.findAvailableComponentsGroupedByName", String.class);
        query.setParameter("workplaceId", workplaceId);
        return query.getResultList();
    }

    /**
     * Retrieves the first available component by name for a specific workplace.
     *
     * @param name        The name of the component to retrieve.
     * @param workplaceId The ID of the workplace to retrieve the component from.
     * @return The first available ComponentEntity with the specified name and workplace ID, or null if not found.
     */
    public ComponentEntity findFirstAvailableComponentByName(String name, Long workplaceId) {
        try {
            TypedQuery<ComponentEntity> query = em.createNamedQuery("Component.findFirstAvailableComponentByName", ComponentEntity.class);
            query.setParameter("name", name);
            query.setParameter("workplaceId", workplaceId);
            return query.getResultList().stream().findFirst().orElse(null);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Counts the total number of components associated with a specific workplace.
     *
     * @param workplaceName The name of the workplace to count components for.
     * @return The total number of components associated with the specified workplace.
     */
    public long countTotalComponentsByWorkplace(String workplaceName) {
        try{
            TypedQuery<Long> query = em.createNamedQuery("Component.countTotalComponentsByWorkplace", Long.class);
            query.setParameter("workplaceName", workplaceName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Counts the number of available components associated with a specific workplace.
     *
     * @param workplaceName The name of the workplace to count available components for.
     * @return The number of available components associated with the specified workplace.
     */
    public long countAvailableComponentsByWorkplace(String workplaceName) {
        try{
            TypedQuery<Long> query = em.createNamedQuery("Component.countAvailableComponentsByWorkplace", Long.class);
            query.setParameter("workplaceName", workplaceName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Retrieves a list of components associated with a specific project.
     *
     * @param projectId The ID of the project to retrieve components for.
     * @return A list of ComponentEntity instances associated with the specified project.
     */
    public List<ComponentEntity> findComponentsByProjectId(Long projectId) {
        return em.createNamedQuery("Component.findComponentsByProjectId", ComponentEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}

