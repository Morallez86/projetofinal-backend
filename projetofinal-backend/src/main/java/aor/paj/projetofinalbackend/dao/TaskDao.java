package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.TaskEntity;

import jakarta.ejb.Stateless;

import java.util.Collections;
import java.util.List;

@Stateless
public class TaskDao extends AbstractDao<TaskEntity> {

    private static final long serialVersionUID = 1L;

    public TaskDao() {
        super(TaskEntity.class);
    }

    public List<TaskEntity> findAll() {
        try{
            return em.createNamedQuery("Task.findAllTasks", TaskEntity.class)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
