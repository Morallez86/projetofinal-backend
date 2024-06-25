package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Stateless
public class UserDao extends AbstractDao<UserEntity> {

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

    public UserEntity findUserByIdWithProjects(Long userId) {
        UserEntity user = em.find(UserEntity.class, userId);
        if (user != null) {
            // Initialize the collection
            Hibernate.initialize(user.getOwnedProjects());
        }
        return user;
    }

    public long getTotalProjectCount(Long userId) {
        try {
            return em.createNamedQuery("User.getTotalProjectCount", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0; // Return 0 if no projects found for the user
        }
    }

    public List<UserEntity> findUsersByQuery(String query) {
        return em.createNamedQuery("User.findUsersByQuery", UserEntity.class)
                .setParameter("query", "%" + query.toLowerCase() + "%")
                .getResultList();
    }

    public List<UserEntity> findAllUsers() {
        try{
            return em.createNamedQuery("User.findAllUsers", UserEntity.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserEntity> searchUsers(String searchTerm, String workplace, String skills, String interests) {
        try {
            return em.createNamedQuery("User.searchUsers", UserEntity.class)
                    .setParameter("searchTerm", searchTerm != null ? "%" + searchTerm.toLowerCase() + "%" : null)
                    .setParameter("workplace", workplace)
                    .setParameter("skills", skills != null && !skills.isEmpty() ? skills : null)
                    .setParameter("interests", interests != null && !interests.isEmpty() ? interests : null)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList(); // Return an empty list instead of null
        }
    }




}
