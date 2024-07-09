package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.TaskEntity;

import jakarta.ejb.Stateless;

import java.util.Collections;
import java.util.List;

/**
 * Data Access Object (DAO) for managing TaskEntity entities.
 * Provides methods to perform CRUD operations and retrieve tasks from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class TaskDao extends AbstractDao<TaskEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the TaskDao initializing with TaskEntity class.
     */
    public TaskDao() {
        super(TaskEntity.class);
    }

    /**
     * Retrieves all tasks from the database.
     *
     * @return A list of all TaskEntity objects stored in the database. Returns an empty list if no tasks are found or an error occurs.
     */
    public List<TaskEntity> findAll() {
        try{
            return em.createNamedQuery("Task.findAllTasks", TaskEntity.class)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves tasks associated with a specific project ID.
     *
     * @param projectId The ID of the project to retrieve tasks for.
     * @return A list of TaskEntity objects associated with the specified project ID.
     */
    public List<TaskEntity> findTasksByProjectId(Long projectId) {
        return em.createNamedQuery("ProjectEntity.findTasksByProjectId", TaskEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }
}
