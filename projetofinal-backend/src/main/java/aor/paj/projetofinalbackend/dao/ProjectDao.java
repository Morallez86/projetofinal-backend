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

}
