package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.InterestEntity;
import jakarta.ejb.Stateless;
import java.util.List;

/**
 * Data Access Object (DAO) for managing InterestEntity entities.
 * Extends TagDao providing CRUD operations and additional methods specific to InterestEntity.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class InterestDao extends TagDao<InterestEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the InterestDao with the entity class InterestEntity.
     */
    public InterestDao() {
        super(InterestEntity.class);
    }

    /**
     * Retrieves all interests.
     *
     * @return A list of all InterestEntity instances.
     */
    public List<InterestEntity> findAllInterests() {
        return super.findAllAttributes("Interest.findAllInterests");
    }

    /**
     * Retrieves an interest through its name.
     *
     * @param name The name of the interest to retrieve.
     * @return The InterestEntity with the specified name, or null if not found.
     */
    public InterestEntity findByName(String name) {
        return super.findByName("Interest.findByName", name);
    }

    /**
     * Retrieves an interest through its ID.
     *
     * @param id The ID of the interest to retrieve.
     * @return The InterestEntity with the specified ID, or null if not found.
     */
    public InterestEntity findById(Long id) {
        return super.findById("Interest.findInterestById", id);
    }

    /**
     * Retrieves interests by a list of IDs.
     *
     * @param ids The list of IDs of interests to retrieve.
     * @return A list of InterestEntity instances corresponding to the provided IDs.
     */
    public List<InterestEntity> findAllById(List<Long> ids) {
        return em.createNamedQuery("Interest.findAllById", InterestEntity.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
