package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ProjectHistoryEntity;
import jakarta.ejb.Stateless;

/**
 * Data Access Object (DAO) for managing ProjectHistoryEntity entities.
 * Provides methods to perform CRUD operations and retrieve the history of projects from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class ProjectHistoryDao extends AbstractDao<ProjectHistoryEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the ProjectHistoryDao initializing with ProjectHistoryEntity class.
     */
    public ProjectHistoryDao() {
        super(ProjectHistoryEntity.class);
    }
}
