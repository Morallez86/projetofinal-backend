package aor.paj.projetofinalbackend.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Abstract base class for Data Access Objects (DAOs) providing basic CRUD operations and entity management.
 *
 * @param <T> The type of entity managed by this DAO.
 * @author João Morais
 * @author Ricardo Elias
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The class type of the entity managed by this DAO.
     */
    protected final Class<T> clazz;

    /**
     * The entity manager provided by the persistence context.
     */
    @PersistenceContext(unitName = "PersistenceUnit")
    protected EntityManager em;

    /**
     * Constructs the DAO with the specified entity class.
     *
     * @param clazz The class type of the entity managed by this DAO.
     */
    public AbstractDao(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id The primary key of the entity.
     * @return The found entity instance or null if the entity does not exist.
     */
    public T find(Object id)
    {
        return em.find(clazz, id);
    }

    /**
     * Persists a new entity in the database.
     *
     * @param entity The entity to persist.
     */
    public void persist(final T entity)
    {
        em.persist(entity);
    }

    /**
     * Merges the state of a detached entity into the current persistence context.
     *
     * @param entity The entity to merge.
     */
    public void merge(final T entity)
    {
        em.merge(entity);
    }

    /**
     * Removes an entity from the database.
     *
     * @param entity The entity to remove.
     */
    public void remove(final T entity)
    {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    /**
     * Retrieves all entities of the managed type from the database.
     *
     * @return A list of all entities.
     */
    public List<T> findAll()
    {
        final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));
        return em.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Deletes all entities of the managed type from the database.
     */
    public void deleteAll()
    {
        final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(clazz);
        criteriaDelete.from(clazz);
        em.createQuery(criteriaDelete).executeUpdate();
    }

    /**
     * Flushes any changes made to entities managed by the current persistence context to the database.
     */
    public void flush() {
        em.flush();
    }
}
