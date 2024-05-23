package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserDao extends AbstractDao<UserEntity> {

    @PersistenceContext
    private EntityManager em;

    private static final long serialVersionUID = 1L;

    public UserDao() {
        super(UserEntity.class);
    }

    public UserEntity findUserByEmail(String email) {
        try {
            return em.createNamedQuery("User.findUserByEmail", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public UserEntity findUserByUsername(String username) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByUsername").setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public UserEntity findUserById(Long userId) {
        try {
            return em.createNamedQuery("User.findUserById", UserEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public long countTotalUsers() {
        try {
            return (long) em.createNamedQuery("User.countTotalUsers")
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0; // Return 0 if no users found
        }
    }

    public UserEntity findByEmailValidationToken(String emailToken) {
        try {
            return em.createNamedQuery("User.findUserByEmailValidationToken", UserEntity.class)
                    .setParameter("emailToken", emailToken)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}