package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.Collections;
import java.util.List;

@Stateless
public class UserProjectDao extends AbstractDao<UserProjectEntity> {

    private static final long serialVersionUID = 1L;

    public UserProjectDao() {
        super(UserProjectEntity.class);
    }

    public UserProjectEntity findByUserAndProjectActive(Long userId, Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findByUserAndProjectActive", UserProjectEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ProjectEntity> findProjectsByUserIdActive(Long userId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findProjectsByUserIdActive", ProjectEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long countProjectsByUserIdActive(Long userId) {
        try {
            return em.createNamedQuery("UserProjectEntity.countProjectsByUserIdActive", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L; // Return 0 if no results found
        }
    }

    public List<UserProjectEntity> findByUserIdActive(Long userId) {
        return em.createNamedQuery("UserProjectEntity.findByUserIdActive", UserProjectEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<UserEntity> findAdminsByProjectIdActive(Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findAdminsByProjectIdActive", UserEntity.class)
                    .setParameter("projectId", projectId)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public Long countActiveUsersByProjectId(Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.countActiveUsersByProjectId", Long.class)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L; // Return 0 if no results found
        }
    }
}

