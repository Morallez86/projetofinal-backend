package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.ComponentEntity;
import aor.paj.projetofinalbackend.entity.ProjectEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

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

    public long getTotalComponentsCount() {
        TypedQuery<Long> query = em.createNamedQuery("Component.getTotalComponentsCount", Long.class);
        return query.getSingleResult();
    }

    public List<ComponentEntity> findAllOrderedByName(int page, int limit) {
        return em.createNamedQuery("Component.findAllOrderedByName", ComponentEntity.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<ComponentEntity> findByKeywordOrderedByName(int page, int limit,String keyword) {
        return em.createNamedQuery("Component.findByKeywordOrderedByName", ComponentEntity.class)
                .setParameter("keyword", keyword)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public long countByKeyword(String keyword) {
        return em.createNamedQuery("Component.countByKeyword", Long.class)
                .setParameter("keyword", keyword)
                .getSingleResult();
    }
}

