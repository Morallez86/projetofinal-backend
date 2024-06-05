package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

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

}
