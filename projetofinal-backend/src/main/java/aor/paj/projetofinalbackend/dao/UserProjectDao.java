package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserEntity;
import aor.paj.projetofinalbackend.entity.UserProjectEntity;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

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
}

