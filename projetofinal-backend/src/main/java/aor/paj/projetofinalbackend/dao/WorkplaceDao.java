package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.Collections;
import java.util.List;

/**
 * Data Access Object (DAO) for managing WorkplaceEntity entities.
 * Provides methods to perform CRUD operations and retrieve workplaces from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class WorkplaceDao extends AbstractDao<WorkplaceEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the WorkplaceDao initializing with WorkplaceEntity class.
     */
    public WorkplaceDao() {
        super(WorkplaceEntity.class);
    }

    /**
     * Retrieves a workplace entity based on the name of the workplace.
     *
     * @param name The name of the workplace to retrieve.
     * @return The WorkplaceEntity with the specified name. Returns null if no workplace with the specified name is found.
     */
    public WorkplaceEntity findWorkplaceByName(String name) {
        try {
            return em.createNamedQuery("Workplace.findWorkplaceByName", WorkplaceEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Retrieves a list of all workplaces stored in the database.
     *
     * @return A list of all WorkplaceEntity objects representing all workplaces. Returns an empty list if no workplaces are found.
     */
    public List<WorkplaceEntity> findAllWorkplaces() {
        try{
            return em.createNamedQuery("Workplace.findAllWorkplaces", WorkplaceEntity.class)
                    .getResultList();
        } catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves the count of projects associated with each workplace.
     *
     * @return A list of Object arrays where each array contains workplace and project count information.
     * Each array has two elements: WorkplaceEntity (as the first element) and Long (as the second element).
     * Returns an empty list if no results are found.
     */
    public List<Object[]> getProjectCountPerWorkplace() {
        try {
            return em.createNamedQuery("Workplace.findProjectCountPerWorkplace", Object[].class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
