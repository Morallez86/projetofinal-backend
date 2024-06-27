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

    public UserProjectEntity findByUserAndProject(Long userId, Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findByUserAndProject", UserProjectEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("projectId", projectId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ProjectEntity> findProjectsByUserId(Long userId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findProjectsByUserId", ProjectEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long countProjectsByUserId(Long userId) {
        try {
            return em.createNamedQuery("UserProjectEntity.countProjectsByUserId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L; // Return 0 if no results found
        }
    }

    public List<UserProjectEntity> findByUserId(Long userId) {
        return em.createNamedQuery("UserProjectEntity.findByUserId", UserProjectEntity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<UserEntity> findAdminsByProjectId(Long projectId) {
        try {
            return em.createNamedQuery("UserProjectEntity.findAdminsByProjectId", UserEntity.class)
                    .setParameter("projectId", projectId)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}

