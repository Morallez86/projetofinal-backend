package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.TagEntity;
import jakarta.ejb.Stateless;

import java.util.Collections;
import java.util.List;

/**
 * Abstract Data Access Object (DAO) for managing entities that extend TagEntity.
 * Provides generic CRUD operations and utility methods for entities related to tags.
 *
 * @param <T> The type of TagEntity managed by this DAO.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public abstract class TagDao<T extends TagEntity> extends AbstractDao<T> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the TagDao with the specified entity class.
     *
     * @param clazz The Class object representing the entity type managed by this DAO.
     */
    public TagDao(Class<T> clazz) {
        super(clazz);
    }

    /**
     * Retrieves all entities of type T using a named query.
     *
     * @param namedQuery The name of the named query to execute.
     * @return A list of entities of type T retrieved from the database.
     */
    public List<T> findAllAttributes(String namedQuery) {
        try {
            return em.createNamedQuery(namedQuery, clazz).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves an entity of type T by its name using a named query.
     *
     * @param namedQuery The name of the named query to execute.
     * @param name The name parameter to pass to the named query.
     * @return The entity of type T with the specified name, or null if not found.
     */
    public T findByName(String namedQuery, String name) {
        try {
            return em.createNamedQuery(namedQuery, clazz)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves an entity of type T by its ID using a named query.
     *
     * @param namedQuery The name of the named query to execute.
     * @param id The ID parameter to pass to the named query.
     * @return The entity of type T with the specified ID, or null if not found.
     */
    public T findById(String namedQuery, long id) {
        try {
            return em.createNamedQuery(namedQuery, clazz)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

