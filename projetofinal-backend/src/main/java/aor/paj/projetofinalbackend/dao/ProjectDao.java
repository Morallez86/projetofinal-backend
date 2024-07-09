package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.*;
import aor.paj.projetofinalbackend.utils.ProjectStatus;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Data Access Object (DAO) for managing ProjectEntity entities.
 * Provides methods to perform CRUD operations and retrieve projects from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ProjectDao extends AbstractDao<ProjectEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the ProjectDao initializing with ProjectEntity class.
     */
    public ProjectDao() {
        super(ProjectEntity.class);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param id The ID of the project to retrieve.
     * @return The ProjectEntity object with the specified ID, or null if not found.
     */
    public ProjectEntity findProjectById(Long id) {
        try {
            return em.createNamedQuery("ProjectEntity.findProjectById", ProjectEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves all projects with pagination.
     *
     * @param page  The page number for pagination (1-based).
     * @param limit The maximum number of projects per page.
     * @return A list of ProjectEntity objects within the specified page and limit.
     */
    public List<ProjectEntity> findAllProjects(int page, int limit) {
        return em.createNamedQuery("ProjectEntity.findAll", ProjectEntity.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Retrieves all projects without using queries.
     *
     * @return A list of all ProjectEntity objects.
     */
    public List<ProjectEntity> getAllProjectsNoQueries() {
        return em.createNamedQuery("ProjectEntity.findAll", ProjectEntity.class)
                .getResultList();
    }

    /**
     * Retrieves the total count of projects.
     *
     * @return The total number of projects in the database.
     */
    public long getTotalProjectCount() {
        try {
            return em.createNamedQuery("ProjectEntity.getTotalProjectCount", Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Retrieves user projects associated with a project ID.
     *
     * @param projectId The ID of the project to retrieve user projects for.
     * @return A list of UserProjectEntity objects associated with the specified project ID.
     */
    public List<UserProjectEntity> findUserProjectsByProjectId(Long projectId) {
        return em.createNamedQuery("ProjectEntity.findUserProjectsByProjectId", UserProjectEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    /**
     * Retrieves tasks of a project filtered by project ID and planned starting date.
     *
     * @param projectId           The ID of the project to retrieve tasks for.
     * @param plannedStartingDate The planned starting date of the tasks.
     * @return A list of TaskEntity objects that match the criteria.
     */

    public List<TaskEntity> findTasksByProjectIdAndEndingDate(Long projectId, LocalDateTime plannedStartingDate) {
        return em.createNamedQuery("ProjectEntity.findTasksByProjectIdAndEndingDate", TaskEntity.class)
                .setParameter("projectId", projectId)
                .setParameter("plannedStartingDate", plannedStartingDate)
                .getResultList();
    }

    /**
     * Retrieves the total count of users associated with projects.
     *
     * @return The total number of users associated with projects in the database.
     */
    public long getTotalUserCount() {
        try {
            return em.createNamedQuery("ProjectEntity.getTotalUserCount", Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    /**
     * Retrieves the count of approved projects.
     *
     * @return The number of projects that are approved (status APPROVED) in the database.
     */
    public long getApprovedProjectCount() {
        return em.createNamedQuery("ProjectEntity.getApprovedProjectCount", Long.class).getSingleResult();
    }

    /**
     * Retrieves the count of finished projects.
     *
     * @return The number of projects that are finished (status FINISHED) in the database.
     */
    public long getFinishedProjectCount() {
        return em.createNamedQuery("ProjectEntity.getFinishedProjectCount", Long.class).getSingleResult();
    }

    /**
     * Retrieves the count of canceled projects.
     *
     * @return The number of projects that are canceled (status CANCELED) in the database.
     */
    public long getCanceledProjectCount() {
        return em.createNamedQuery("ProjectEntity.getCanceledProjectCount", Long.class).getSingleResult();
    }

    /**
     * Retrieves the average execution time of projects.
     *
     * @return The average execution time of projects in days.
     */
    public double getAverageExecutionTime() {
        return em.createNamedQuery("ProjectEntity.getAverageExecutionTime", Double.class).getSingleResult();
    }

    /**
     * Searches for projects based on search term, skill string, interest string, and status.
     *
     * @param searchTerm The term to search for in project names or descriptions.
     * @param skillString The string representation of skills required for the project.
     * @param interestString The string representation of interests related to the project.
     * @param status The status of the projects to retrieve (e.g., APPROVED, IN_PROGRESS).
     * @return A list of ProjectEntity objects that match the search criteria.
     */
    public List<ProjectEntity> searchProjects(String searchTerm, String skillString, String interestString, ProjectStatus status) {
        try {
            TypedQuery<ProjectEntity> query = em.createNamedQuery("ProjectEntity.searchProjects", ProjectEntity.class)
                    .setParameter("searchTerm", searchTerm)
                    .setParameter("skillString", skillString)
                    .setParameter("interestString", interestString)
                    .setParameter("status", status);

            return query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            return Collections.emptyList(); // Return an empty list if no results found
        }
    }
}
