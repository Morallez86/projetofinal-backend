package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.TaskEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ProjectDao extends AbstractDao<ProjectEntity> {

    private static final long serialVersionUID = 1L;

    public ProjectDao() {
        super(ProjectEntity.class);
    }

    public ProjectEntity findProjectById(Long id) {
        try {
            return em.createNamedQuery("ProjectEntity.findProjectById", ProjectEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ProjectEntity> findAllProjects(int page, int limit) {
        return em.createNamedQuery("ProjectEntity.findAll", ProjectEntity.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<ProjectEntity> getAllProjectsNoQueries() {
        return em.createNamedQuery("ProjectEntity.findAll", ProjectEntity.class)
                .getResultList();
    }

    public long getTotalProjectCount() {
        try {
            return em.createNamedQuery("ProjectEntity.getTotalProjectCount", Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public List<UserProjectEntity> findUserProjectsByProjectId(Long projectId) {
        return em.createNamedQuery("ProjectEntity.findUserProjectsByProjectId", UserProjectEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public List<TaskEntity> findTasksByProjectIdAndEndingDate(Long projectId, LocalDateTime plannedStartingDate) {
        return em.createNamedQuery("ProjectEntity.findTasksByProjectIdAndEndingDate", TaskEntity.class)
                .setParameter("projectId", projectId)
                .setParameter("plannedStartingDate", plannedStartingDate)
                .getResultList();
    }

    public long getTotalUserCount() {
        try {
            return em.createNamedQuery("ProjectEntity.getTotalUserCount", Long.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public long getApprovedProjectCount() {
        return em.createNamedQuery("ProjectEntity.getApprovedProjectCount", Long.class).getSingleResult();
    }

    public long getFinishedProjectCount() {
        return em.createNamedQuery("ProjectEntity.getFinishedProjectCount", Long.class).getSingleResult();
    }

    public long getCanceledProjectCount() {
        return em.createNamedQuery("ProjectEntity.getCanceledProjectCount", Long.class).getSingleResult();
    }

    public double getAverageExecutionTime() {
        return em.createNamedQuery("ProjectEntity.getAverageExecutionTime", Double.class).getSingleResult();
    }

    public double getPercentage(long part, long total) {
        if (total == 0) {
            return 0;
        }
        return (double) part / total * 100;
    }


}
