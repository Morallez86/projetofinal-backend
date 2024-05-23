package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.Collections;
import java.util.List;

@Stateless
public class WorkplaceDao extends AbstractDao<WorkplaceEntity> {

    private static final long serialVersionUID = 1L;

    public WorkplaceDao() {
        super(WorkplaceEntity.class);
    }

    public WorkplaceEntity findWorkplaceByName(String name) {
        try {
            return em.createNamedQuery("Workplace.findWorkplaceByName", WorkplaceEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<WorkplaceEntity> findAllWorkplaces() {
        try{
            return em.createNamedQuery("Workplace.findAllWorkplaces", WorkplaceEntity.class)
                    .getResultList();
        } catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
