package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Stateless
public class ComponentDao extends AbstractDao<ComponentEntity> {

    private static final long serialVersionUID = 1L;

    public ComponentDao() {
        super(ComponentEntity.class);
    }

    public ComponentEntity findComponentById(Long id) {
        try {
            TypedQuery<ComponentEntity> query = em.createNamedQuery("Component.findComponentById", ComponentEntity.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum componente for encontrado com o ID especificado
        }
    }

}

